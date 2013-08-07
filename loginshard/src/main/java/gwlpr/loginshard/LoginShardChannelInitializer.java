/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import gwlpr.protocol.handshake.HandshakeHandler;
import gwlpr.protocol.loginserver.LoginServerCodec;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import realityshard.container.network.ConnectionStateHandler;
import realityshard.container.network.MessageDemuxDecoder;


/**
 * Initializes a login shard channel... duh
 * 
 * @author _rusty
 */
public class LoginShardChannelInitializer extends ChannelInitializer<Channel>
{

    @Override
    protected void initChannel(Channel ch)
    {
        // inbound handlers
        ch.pipeline().addLast(
                new ConnectionStateHandler(),
                HandshakeHandler.produceLoginHandshake(),
                new LoginServerCodec(),
                new MessageDemuxDecoder());
    }
    
}
