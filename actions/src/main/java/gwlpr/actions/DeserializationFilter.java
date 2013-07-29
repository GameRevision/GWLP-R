/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.shardlet.Action;


/**
 * Defines the deserialization method. And standard classes.
 * 
 * @author _rusty
 */
public interface DeserializationFilter
{
    
    final static Logger LOGGER = LoggerFactory.getLogger(DeserializationFilter.class);
    
    
    /**
     * Deserializes a certain amount of bytes of a certain field
     * of the action T and returns the action with the filled field.
     */
    <T extends Action> boolean deserialize(ByteBuffer buf, T action);
    
    
    /**
     * Chain of responsibility in a composite pattern.
     */
    public final class Chain implements DeserializationFilter
    {
        
        List<DeserializationFilter> filters = new ArrayList<>();
        ListIterator<DeserializationFilter> iterator = filters.listIterator();
        
        
        public void register(DeserializationFilter filter) 
        { 
            filters.add(filter); 
            iterator = filters.listIterator(); 
        }
        

        @Override
        public <T extends Action> boolean deserialize(ByteBuffer buf, T action) 
        {
            if (iterator.hasPrevious()) 
            { 
                // retry the filter that didnt work last time
                iterator.previous(); 
            }
            
            for (Iterator<DeserializationFilter> it = iterator; it.hasNext();) 
            {
                DeserializationFilter filter = it.next();
                
                // if a deserialization failed,
                // we will return false and wait for the next invocation of this
                if (!filter.deserialize(buf, action))
                {
                    return false;
                }
            }
            
            // all done, return true and reset the iterator
            iterator = filters.listIterator();
            return true;
        }
    }
    
    
    /**
     * Superclass of all deserialization filters
     */
    public abstract class Generic implements DeserializationFilter
    {
        protected final Field field;
        
        public Generic(Field field) 
        { 
            field.setAccessible(true); 
            this.field = field; 
        }
        
        @Override
        public <T extends Action> boolean deserialize(ByteBuffer buf, T action) 
        {
            try 
            { 
                Object value = get(buf);
                if (value == null) { return false; }
                field.set(action, get(buf)); 
            } 
            catch (Exception ex) 
            {
                LOGGER.error("Could not deserialize.", ex); return false; 
            }
            
            return true;
        }
        
        protected abstract Object get(ByteBuffer buf);
    }
    
    
    /**
     * Null-object.
     */
    public final class Empty implements DeserializationFilter
    {
        @Override
        public <T extends Action> boolean deserialize(ByteBuffer buf, T action) 
        {
            return true;
        }
    }
    
    
    public class Byte extends Generic
    {
        public Byte(Field field) { super(field); }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 1 ? buf.get() : null;
        }
    }
    
    
    public class Short extends Generic
    {
        public Short(Field field) { super(field); }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 2 ? buf.getShort() : null;
        }
    }
    
    
    public class Int extends Generic
    {
        public Int(Field field) { super(field); }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 4 ? buf.getInt() : null;
        }
    }
    
    
    public class Float extends Generic
    {
        public Float(Field field) { super(field); }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 4 ? buf.getFloat() : null;
        }
    }
}
