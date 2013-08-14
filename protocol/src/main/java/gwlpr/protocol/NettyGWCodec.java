/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import gwlpr.protocol.serialization.GWMessage;
import gwlpr.protocol.serialization.GWMessageSerializationRegistry;
import gwlpr.protocol.serialization.NettySerializationFilter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import java.nio.ByteOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.network.Message;


/**
 * Base class for message factories
 * Register your messages with one of the subclasses - they will use these methods
 * to register their messages with the MessageRegisty
 * 
 * @author _rusty
 */
public abstract class NettyGWCodec extends ByteToMessageCodec<GWMessage>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyGWCodec.class);

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> result) 
    {
        ByteBuf buf = in.order(ByteOrder.LITTLE_ENDIAN);
        
        while (buf.isReadable())
        {
            buf.markReaderIndex();
            
            // get the header failsafe
            int header = buf.readableBytes() >= 2 ? buf.readShort() : -1;
            
            // failcheck
            if (header == -1) { return; }
            
            // try get the message class
            Class<? extends GWMessage> messageClazz = getByHeader(header);
            
            // failcheck
            if (messageClazz == null) { buf.resetReaderIndex(); return; }
            
            // try retrieve the serialization filter
            NettySerializationFilter filter = GWMessageSerializationRegistry.getFilter(messageClazz);
            
            // failcheck
            if (filter == null) { buf.resetReaderIndex(); return; }
            
            // try create the message
            Message message;
            try 
            {
                message = messageClazz.newInstance();
            } 
            catch (InstantiationException | IllegalAccessException ex) 
            {
                LOGGER.error("Could not create an instance of an message.", ex);
                
                buf.resetReaderIndex();
                return;
            }
            
            // dont forget to initialize the message
            message.init(ctx.channel());
            
            // try serialize the message
            if (!filter.deserialize(buf, message))
            {
                buf.resetReaderIndex();
                return;
            }
            
            // finally add the message
            result.add(message);
            
            LOGGER.debug("Got: {}", message.toString());
        }
    }


    @Override
    public void encode(ChannelHandlerContext ctx, GWMessage message, ByteBuf out) 
    {
        ByteBuf result = out.order(ByteOrder.LITTLE_ENDIAN);
        
        LOGGER.debug("Put: {}", message.toString());
        
        GWMessage gwact = message;
        int header = gwact.getHeader();

        // try retrieve the serialization filter
        NettySerializationFilter filter = GWMessageSerializationRegistry.getFilter(gwact.getClass());
        
        if (filter == null) 
        { 
            LOGGER.error("Could not find a filter for given message.");
            return; 
        }
        
        // write the header
        result.writeShort(header);

        // serialize the message
        filter.serialize(result, message);
    }
    
    
    /**
     * Template method.
     */
    protected abstract Class<? extends GWMessage> getByHeader(int header);
}
