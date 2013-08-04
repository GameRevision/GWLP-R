/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import gwlpr.protocol.serialization.GWAction;
import gwlpr.protocol.serialization.GWActionSerializationRegistry;
import gwlpr.protocol.serialization.NettySerializationFilter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.nio.ByteOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.shardlet.Action;


/**
 * Base class for action factories
 * Register your actions with one of the subclasses - they will use these methods
 * to register their actions with the ActionRegisty
 * 
 * @author _rusty
 */
public abstract class NettyGWCodec extends ByteToMessageCodec<GWAction>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyGWCodec.class);

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> result) 
    {
        buf.order(ByteOrder.LITTLE_ENDIAN);
        
        while (buf.isReadable())
        {
            buf.markReaderIndex();
            
            // get the header failsafe
            int header = buf.readableBytes() >= 2 ? buf.readShort() : -1;
            
            // try get the action class
            Class<? extends GWAction> actionClazz = getByHeader(header);
            
            // try retrieve the serialization filter
            NettySerializationFilter filter = GWActionSerializationRegistry.getFilter(actionClazz);
            
            if ((header == -1) || actionClazz == null || filter == null)
            {
                buf.resetReaderIndex();
                return;
            }
            
            // try create the action
            Action action;
            try 
            {
                action = actionClazz.newInstance();
            } 
            catch (InstantiationException | IllegalAccessException ex) 
            {
                LOGGER.error("Could not create an instance of an action.", ex);
                
                buf.resetReaderIndex();
                return;
            }
            
            // try serialize the action
            if (!filter.deserialize(buf, action))
            {
                buf.resetReaderIndex();
                return;
            }
            
            // finally add the action
            result.add(action);
        }
    }


    @Override
    public void encode(ChannelHandlerContext ctx, GWAction action, ByteBuf result) 
    {
        result.order(ByteOrder.LITTLE_ENDIAN);
        
        GWAction gwact = action;
        int header = gwact.getHeader();

        // try retrieve the serialization filter
        NettySerializationFilter filter = GWActionSerializationRegistry.getFilter(gwact.getClass());
        
        if (filter == null) 
        { 
            LOGGER.error("Could not find a filter for given action.");
            return; 
        }
        
        // write the header
        result.writeShort(header);

        // serialize the action
        filter.serialize(result, action);
    }
    
    
    /**
     * Template method.
     */
    protected abstract Class<? extends GWAction> getByHeader(int header);
}
