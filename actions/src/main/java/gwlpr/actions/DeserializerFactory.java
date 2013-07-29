/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.lang.reflect.Field;
import realityshard.shardlet.Action;


/**
 * Creates a deserializer chain for given generic action.
 * 
 * @author _rusty
 */
public final class DeserializerFactory 
{
    
    /**
     * Creates a new deserializer chain. Should be used once per session.
     * 
     * @param       <T>                     Type of the action it deserializes
     * @param       clazz                   The actual class.
     * @return      The deserialization methods, chained.
     */
    public <T extends Action> DeserializationFilter produceDeserializer(Class<T> clazz)
    {
        DeserializationFilter.Chain result = new DeserializationFilter.Chain();
        
        for (Field field : clazz.getDeclaredFields()) 
        {
            // TODO: needs to be registered IN ORDER!
            result.register(createFilter(field));
        }
        
        return result;
    }
    
    
    private DeserializationFilter createFilter(Field field)
    {        
        if (field.getType().equals(byte.class))     { return new DeserializationFilter.Byte(field); }
        if (field.getType().equals(short.class))    { return new DeserializationFilter.Short(field); }
        if (field.getType().equals(int.class))      { return new DeserializationFilter.Int(field); }
        if (field.getType().equals(float.class))    { return new DeserializationFilter.Float(field); }
        return new DeserializationFilter.Empty();
    }
}
