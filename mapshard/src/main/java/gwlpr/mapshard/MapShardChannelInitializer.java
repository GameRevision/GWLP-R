/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard;

import gwlpr.protocol.NettyGWLoggingHandler;
import static gwlpr.protocol.NettyGWLoggingHandler.OperationMethod.*;
import gwlpr.protocol.gameserver.GameServerCodec;
import gwlpr.protocol.handshake.EncryptionOptions;
import gwlpr.protocol.handshake.HandshakeHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;
import java.util.ArrayList;
import java.util.Collection;
import realityshard.container.network.ConnectionStateHandler;
import realityshard.container.network.MessageDemuxDecoder;


/**
 * Initializes a map shard channel... duh
 * 
 * @author _rusty
 */
public class MapShardChannelInitializer  extends ChannelInitializer<Channel>
{
    
    private final EncryptionOptions encrypted;
    
    
    public MapShardChannelInitializer(EncryptionOptions encrypted)
    {
        this.encrypted = encrypted;
    }
    

    @Override
    protected void initChannel(Channel ch)
    {
        // do not log these outbound packets
        Collection<Integer> outBlackList = new ArrayList<>();
        outBlackList.add(0);
        
        // inbound handlers
        ch.pipeline().addLast(
                new LoggingHandler(),
                new ConnectionStateHandler(),
                HandshakeHandler.produceGameHandshake(encrypted),
                new GameServerCodec(),
                new MessageDemuxDecoder(),
                new NettyGWLoggingHandler(BlackList, new ArrayList<Integer>(), outBlackList));
    }
    
}
