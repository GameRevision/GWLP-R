/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import gwlpr.protocol.serialization.GWMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO: Test this with real-life packets
 * 
 * @author _rusty
 */
public class NettyGWLoggingHandler extends LoggingHandler
{
 
    public static enum OperationMethod
    {
        WhiteList,
        BlackList
    }
    
    private static enum Dir
    {
        In,
        Out
    }
    
    
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyGWLoggingHandler.class);
    
    private final OperationMethod opMethod;
    private final Collection<Integer> inHeaders;
    private final Collection<Integer> outHeaders;

    
    /**
     * Constructor.
     */
    public NettyGWLoggingHandler(OperationMethod opMethod, Collection<Integer> inHeaders, Collection<Integer> outHeaders)
    {
        this.opMethod = opMethod;
        this.inHeaders = inHeaders;
        this.outHeaders = outHeaders;
    }

    
    @Override
    protected String formatMessage(String eventName, Object msg) 
    {
        if (!eventName.equals("read") && !eventName.equals("write")) { return null; }
        
        Dir dir = eventName.equals("read") ? Dir.In : Dir.Out;
        
        logIt(dir, (GWMessage) msg);
        
        return null;
    }

    
    @Override
    protected String format(ChannelHandlerContext ctx, String message) 
    {
        return null;
    }

    
    @Override
    protected String formatByteBuf(String eventName, ByteBuf buf) 
    {
        return null;
    }

    
    @Override
    protected String formatNonByteBuf(String eventName, Object msg) 
    {
        return null;
    }
    
    
    /**
     * Helper method.
     * Log a byte buffer, depending on our White/BlackList settings
     */
    public void logIt(Dir dir, GWMessage message)
    {
        int header = message.getHeader();
        
        if (opMethod == OperationMethod.BlackList)
        {
            // dont log it if the blacklist contains the header of this packet
            if (inHeaders.contains(header) && dir == Dir.In) { return; }
            if (outHeaders.contains(header) && dir == Dir.Out) { return; }
        }
        else if (opMethod == OperationMethod.WhiteList)
        {
            // dont log it if the whitelist doesnt contain the header of this packet
            if (!inHeaders.contains(header) && dir == Dir.In) { return; }
            if (!outHeaders.contains(header) && dir == Dir.Out) { return; }
        }
        
        String prefix = dir == Dir.In ? "C to S" : "S to C";
        
        LOGGER.debug(String.format("%s: %s", prefix, message.toString()));
    }
}


