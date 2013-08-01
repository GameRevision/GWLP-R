/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.ASCIIString;
import gwlpr.actions.utils.NestedMarker;
import gwlpr.actions.utils.VarInt;
import gwlpr.actions.utils.Vector2;
import gwlpr.actions.utils.Vector3;
import gwlpr.actions.utils.Vector4;
import gwlpr.actions.utils.WorldPosition;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creates a serializer chain for given generic action.
 * 
 * @author _rusty
 */
public final class GWActionSerializationRegistry
{

    private final static Map<Class<? extends GWAction>, SerializationFilter> ACTIONS = new ConcurrentHashMap<>();
    
    
    /**
     * Register an action with this action factory.
     * Should be used once per action, ideally static.
     * 
     * @param       <T>                     Type of the action it serializes
     * @param       clazz                   The actual class.
     */
    public static <T extends GWAction> void register(Class<T> clazz)
    {
        ACTIONS.put(clazz, produceSerializer(clazz));
    }
    
    
    /**
     * Returns the serialization filter for a given action type.
     * 
     * @param       action                  The action class
     * @return      The filter that can be used to de/serialize that class, or null
     *              if none was found in the registry
     */
    public static SerializationFilter getFilter(Class<? extends GWAction> action)
    {
        return ACTIONS.get(action);
    }
    
    
    /**
     * Creates a new serializer chain. Should be used once per action, ideally static.
     */
    private static SerializationFilter produceSerializer(Class<?> clazz)
    {
        SerializationFilter.Chain result = new SerializationFilter.Chain();
        
        for (Field field : clazz.getDeclaredFields()) 
        {
            // TODO: needs to be registered IN ORDER!
            // we cannot be sure that getDeclaredFields returns the correct order...
            result.register(createFilter(field));
        }
        
        return result;
    }
    
    
    /**
     * Creates a single filter for a field. (Except when its nested)
     */
    private static SerializationFilter createFilter(Field field)
    {        
        // note that we need to use the next bigger type,
        // because we cant have unsigned values.
        if (field.getType().equals(short.class))        { return new SerializationFilter.UByte(field); }
        if (field.getType().equals(int.class))          { return new SerializationFilter.UShort(field); }
        if (field.getType().equals(long.class))         { return new SerializationFilter.UInt(field); }
        if (field.getType().equals(BigInteger.class))   { return new SerializationFilter.ULong(field); }
        if (field.getType().equals(float.class))        { return new SerializationFilter.Float(field); }
        if (field.getType().equals(Vector2.class))      { return new SerializationFilter.Vec2(field); }
        if (field.getType().equals(Vector3.class))      { return new SerializationFilter.Vec3(field); }
        if (field.getType().equals(Vector4.class))      { return new SerializationFilter.Vec4(field); }
        if (field.getType().equals(WorldPosition.class)){ return new SerializationFilter.DW3(field); }
        if (field.getType().equals(VarInt.class))       { return new SerializationFilter.VInt(field); }
        if (field.getType().equals(ASCIIString.class))  { return new SerializationFilter.ASCII(field); }
        if (field.getType().equals(String.class))       { return new SerializationFilter.UTF16(field); }
        if (field.getType().equals(byte[].class))       { return new SerializationFilter.ByteArray(field); }
        // TODO: uid16
        // TODO: guid18
        
        if (NestedMarker.class.isAssignableFrom(field.getType()))
        {
            return new SerializationFilter.Nested(field, field.getType(), produceSerializer(field.getType()));
        }
        
        if (NestedMarker[].class.isAssignableFrom(field.getType()))
        {
            return new SerializationFilter.NestedArray(field, field.getType().getComponentType(), produceSerializer(field.getType().getComponentType()));
        }
        
        return new SerializationFilter.Empty();
    }
}
