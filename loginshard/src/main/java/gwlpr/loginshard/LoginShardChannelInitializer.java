/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import gwlpr.protocol.NettyGWLoggingHandler.*;
import gwlpr.protocol.handshake.EncryptionOptions;
import gwlpr.protocol.handshake.HandshakeHandler;
import gwlpr.protocol.loginserver.LoginServerCodec;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;
import realityshard.container.network.ConnectionStateHandler;
import realityshard.container.network.MessageDemuxDecoder;


/**
 * Initializes a login shard channel... duh
 * 
 * @author _rusty
 */
public class LoginShardChannelInitializer extends ChannelInitializer<Channel>
{
    
    private final EncryptionOptions encrypted;
    
    
    public LoginShardChannelInitializer(EncryptionOptions encrypted)
    {
        this.encrypted = encrypted;
    }
    
    
    @Override
    protected void initChannel(Channel ch)
    {
        // inbound handlers
        ch.pipeline().addLast(
                //new AutoFlushHandler(),
                new LoggingHandler(),
                new ConnectionStateHandler(),
                HandshakeHandler.produceLoginHandshake(encrypted),
                new LoginServerCodec(),
                new MessageDemuxDecoder());
    }
    
}
