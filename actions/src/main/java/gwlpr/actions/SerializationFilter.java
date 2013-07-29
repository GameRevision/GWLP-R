/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.shardlet.Action;


/**
 * Defines the serialization method. And standard classes.
 * 
 * @author _rusty
 */
public interface SerializationFilter 
{
    
    final static Logger LOGGER = LoggerFactory.getLogger(DeserializationFilter.class);
    
    
    /**
     * Serializes a certain amount of bytes from a certain field
     * of the action T and returns the buffer with the filled-in data.
     */
    <T extends Action> boolean deserialize(ByteBuffer buf, T action);

}
