/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.shardlet.Action;
import realityshard.shardlet.SessionState;


/**
 * Base class for action factories
 * Register your actions with one of the subclasses - they will use these methods
 * to register their actions with the ActionRegisty
 * @author _rusty
 */
public abstract class AbstractGWActionFilter implements SessionState
{
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractGWActionFilter.class);
    
    private ByteBuffer lastbuffer = ByteBuffer.allocate(0);
    private boolean hasRemaining;
    
    
    /**
     * Filter a given byte buffer and return any action that could have been deserialized.
     * 
     * @param       buffer
     * @return      The deserialized actions
     */
    @Override
    public List<Action> doInFilter(ByteBuffer buffer) 
    {
        ByteBuffer buf = buffer;
        
        // if we have remaining data, combine the buffers
        if (hasRemaining)
        {
            ByteBuffer tmp = ByteBuffer.allocate(lastbuffer.remaining() + buf.remaining());
            tmp.put(lastbuffer);
            tmp.put(buf);
            
            buf = tmp;
        }
        
        List<Action> result = new ArrayList<>();
        
        while (buf.hasRemaining())
        {
            int pos = buf.position();
            
            // get the header failsafe
            int header = buf.remaining() >= 2 ? buf.getShort() : -1;
            
            // try get the action class
            Class<? extends GWAction> actionClazz = getByHeader(header);
            
            // try retrieve the serialization filter
            SerializationFilter filter = GWActionSerializationRegistry.getFilter(actionClazz);
            
            if ((header == -1) || actionClazz == null || filter == null)
            {
                buf.position(pos);
                lastbuffer = buf;
                hasRemaining = true;
                
                return result;
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
                
                buf.position(pos);
                lastbuffer = buf;
                hasRemaining = true;
                
                return result;
            }
            
            // try serialize the action
            if (!filter.deserialize(buf, action))
            {
                buf.position(pos);
                lastbuffer = buf;
                hasRemaining = true;
                
                return result;
            }
            
            // finally add the action
            result.add(action);
        }
        
        hasRemaining = false;
        
        return result;
    }

    
    @Override
    public ByteBuffer doOutFilter(Action action) 
    {
        ByteBuffer result = ByteBuffer.allocate(1024);
        result.order(ByteOrder.LITTLE_ENDIAN);
        
        GWAction gwact = (GWAction) action;
        int header = gwact.getHeader();

        // try retrieve the serialization filter
        SerializationFilter filter = GWActionSerializationRegistry.getFilter(gwact.getClass());
        
        if (filter == null) 
        { 
            LOGGER.error("Could not find a filter for given action.");
            return result; 
        }
        
        // write the header
        result.putShort((short)header);

        // serialize the action
        filter.serialize(result, action);

        return result;
    }
    
    
    /**
     * Template method.
     */
    protected abstract Class<? extends GWAction> getByHeader(int header);
}
