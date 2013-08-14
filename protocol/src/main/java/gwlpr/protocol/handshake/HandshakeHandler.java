/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.handshake;

import gwlpr.protocol.handshake.messages.P000_VerifyClient;
import gwlpr.protocol.handshake.messages.P000_ClientVersion;
import gwlpr.protocol.handshake.messages.P000_ClientSeed;
import gwlpr.protocol.handshake.messages.P001_ServerSeed;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.network.GameAppContextKey;
import realityshard.container.network.RC4Codec;


/**
 * Handshake is basically the same for LS and GS,
 * just the first packets that are sent differ (client version and verify client)
 *
 * This is full of magic numbers, but the actual classes cant be stuffed with the
 * shit, as they need to be serializable!
 *
 * TODO: refactor me ;)
 *
 * @author _rusty
 */
public class HandshakeHandler extends ByteToMessageDecoder
{

    private static final Logger LOGGER = LoggerFactory.getLogger(HandshakeHandler.class);

    private final EncryptionOptions encryption;
    private final ServerOptions server;

    // this will be given to the game app later on, in the HandShakeDone event
    private P000_VerifyClient.Payload verifyClient = null;


    /**
     * Constructor.
     *
     * @param       expectedLength          Length of first expected packet
     */
    private HandshakeHandler(EncryptionOptions encryption, ServerOptions server)
    {
        this.encryption = encryption;
        this.server = server;
    }


    /**
     * Factory method.
     */
    public static HandshakeHandler produceLoginHandshake(EncryptionOptions encrypted)
    {
        return new HandshakeHandler(encrypted, ServerOptions.LoginServer);
    }


    /**
     * Factory method.
     */
    public static HandshakeHandler produceGameHandshake(EncryptionOptions encrypted)
    {
        return new HandshakeHandler(encrypted, ServerOptions.GameServer);
    }


    @Override
    protected void decode(final ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
    {

        ByteBuf buf = in.order(ByteOrder.LITTLE_ENDIAN);

        switch (server)
        {
            case LoginServer: // client version:
                {
                    // lengthcheck
                    if (buf.readableBytes() < (P000_ClientVersion.getLength() + P000_ClientSeed.getLength())) { return; }

                    // get the header
                    P000_ClientVersion.Header header = P000_ClientVersion.serializeHeader(buf);

                    // check the header
                    if (header == null || !P000_ClientVersion.check(header)) { return; }

                    // get the data
                    P000_ClientVersion.Payload payload = P000_ClientVersion.serializePayload(buf);

                    LOGGER.debug(String.format("Got the client version: %d", payload.ClientVersion));
                }
                break;

            case GameServer: // verify client:
                {
                    // lengthcheck
                    if (buf.readableBytes() < (P000_VerifyClient.getLength() + P000_ClientSeed.getLength())) { return; }

                    // get the header
                    P000_VerifyClient.Header header = P000_VerifyClient.serializeHeader(buf);

                    // check the header
                    if (header == null || !P000_VerifyClient.check(header)) { return; }

                    // get the data
                    verifyClient = P000_VerifyClient.serializePayload(buf);

                    LOGGER.debug(String.format("Got the verify client packet."));
                }
                break;
        }

        // client seed:
        // get the header
        P000_ClientSeed.Header header = P000_ClientSeed.serializeHeader(buf);

        // check the header
        if (header == null || !P000_ClientSeed.check(header)) { return; }

        // get the data
        P000_ClientSeed.Payload payload = P000_ClientSeed.serializePayload(buf);

        LOGGER.debug("Got the client seed packet.");

        // INITIALIZE ENCRYPTION WITH THE CLIENT SEED PAYLOAD

        // generate the shared key
        byte[] sharedKeyBytes = EncryptionUtils.generateSharedKey(payload.ClientPublicKey);

        // start RC4 key generation
        byte[] randomBytes = new byte[20];
        new SecureRandom().nextBytes(randomBytes);
        final byte[] rc4Key = EncryptionUtils.hash(randomBytes);

        // encrypt the RC4 key before sending it to the client
        byte[] xoredRandomBytes = EncryptionUtils.XOR(randomBytes, sharedKeyBytes);

        // INITIALIZATION OF ENCRYPTION DONE

        // we can set the RC4 decoder now... if encryption is enabled
        if (encryption == EncryptionOptions.Enable)
        {
            RC4Codec.Decoder decoder = new RC4Codec.Decoder(rc4Key);
            ctx.pipeline().addFirst(decoder);
            LOGGER.debug("RC4Decoder added to pipeline.");
        }

        // now send the server seed packet
        P001_ServerSeed serverSeed = new P001_ServerSeed();
        serverSeed.setEncryptedRC4Key(xoredRandomBytes);

        buf = ctx.alloc().buffer(70).order(ByteOrder.LITTLE_ENDIAN);
        serverSeed.serializeInto(buf);

        ChannelFuture cf = ctx.writeAndFlush(buf);

        // also remove this handler
        ctx.pipeline().remove(this);

        // set the RC4 encoder only after the serverseed packet has been send!
        cf.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception
            {
                if (encryption == EncryptionOptions.Enable)
                {
                    // add the rc4 codec if encryption is enabled
                    RC4Codec.Encoder encoder = new RC4Codec.Encoder(rc4Key);
                    ctx.pipeline().addFirst(
                            encoder);
                    LOGGER.debug("RC4Encoder added to pipeline.");
                }

                // tell the channel's context that handshake has been done
                ctx.channel().attr(GameAppContextKey.KEY).get().trigger(
                        new HandShakeDoneEvent(ctx.channel(), verifyClient));
            }
        });
    }
}
