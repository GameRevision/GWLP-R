/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard;

import gwlpr.protocol.NettyGWLoggingHandler;
import static gwlpr.protocol.NettyGWLoggingHandler.OperationMethod.*;
import gwlpr.protocol.gameserver.GameServerCodec;
import gwlpr.protocol.handshake.HandshakeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Initializes a map shard channel... duh
 * 
 * @author _rusty
 */
public class MapShardChannelInitializer  extends ChannelInitializer<Channel>
{

    @Override
    protected void initChannel(Channel ch)
    {
        // do not log these outbound packets
        Collection<Integer> outBlackList = new ArrayList<>();
        outBlackList.add(0);
        
        // inbound handlers
        ch.pipeline().addLast(
                HandshakeHandler.produceGameHandshake(),
                new GameServerCodec(),
                new NettyGWLoggingHandler(BlackList, new ArrayList<Integer>(), outBlackList));
    }
    
}
