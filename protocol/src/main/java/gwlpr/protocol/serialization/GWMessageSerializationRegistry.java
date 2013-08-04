/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.serialization;

import gwlpr.protocol.util.IsASCII;
import gwlpr.protocol.util.IsInt64;
import gwlpr.protocol.util.IsVarInt;
import gwlpr.protocol.util.NestedMarker;
import gwlpr.protocol.util.Vector2;
import gwlpr.protocol.util.Vector3;
import gwlpr.protocol.util.Vector4;
import gwlpr.protocol.util.WorldPosition;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Creates a serializer chain for given message.
 * 
 * @author _rusty
 */
public final class GWMessageSerializationRegistry
{

    private final static Map<Class<? extends GWMessage>, NettySerializationFilter> MESSAGES = new ConcurrentHashMap<>();
    
    
    /**
     * Register an message with this message factory.
     * Should be used once per message, ideally static.
     * 
     * @param       <T>                     Type of the message it serializes
     * @param       clazz                   The actual class.
     */
    public static <T extends GWMessage> void register(Class<T> clazz)
    {
        MESSAGES.put(clazz, produceSerializer(clazz));
    }
    
    
    /**
     * Returns the serialization filter for a given message type.
     * 
     * @param       message                  The message class
     * @return      The filter that can be used to de/serialize that class, or null
     *              if none was found in the registry
     */
    public static NettySerializationFilter getFilter(Class<? extends GWMessage> message)
    {
        return MESSAGES.get(message);
    }
    
    
    /**
     * Creates a new serializer chain. Should be used once per message, ideally static.
     */
    private static NettySerializationFilter produceSerializer(Class<?> clazz)
    {
        NettySerializationFilter.Chain result = new NettySerializationFilter.Chain();
        
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
    private static NettySerializationFilter createFilter(Field field)
    {        
        // note that we need to use the next bigger type,
        // because we cant have unsigned values.
        if (field.getType().equals(short.class))        { return new NettySerializationFilter.UByte(field); }
        if (field.getType().equals(float.class))        { return new NettySerializationFilter.Float(field); }
        if (field.getType().equals(Vector2.class))      { return new NettySerializationFilter.Vec2(field); }
        if (field.getType().equals(Vector3.class))      { return new NettySerializationFilter.Vec3(field); }
        if (field.getType().equals(Vector4.class))      { return new NettySerializationFilter.Vec4(field); }
        if (field.getType().equals(WorldPosition.class)){ return new NettySerializationFilter.DW3(field); }
        
        if (field.getType().equals(byte[].class))       { return new NettySerializationFilter.ByteArray(field); }
        // TODO: uid16
        // TODO: guid18
        
        // special case: uint and long
        if (field.getType().equals(long.class))         
        { 
            return field.getAnnotation(IsInt64.class) != null ?
                new NettySerializationFilter.Long(field) :
                new NettySerializationFilter.UInt(field);
                
        }
        
        // special case: ushort and varint        
        if (field.getType().equals(int.class))          
        { 
            return field.getAnnotation(IsVarInt.class) != null ?
                new NettySerializationFilter.VInt(field) :
                new NettySerializationFilter.UShort(field);
        }
        
        // special case: ascii and utf16
        if (field.getType().equals(String.class))          
        { 
            return field.getAnnotation(IsASCII.class) != null ?
                new NettySerializationFilter.ASCII(field) :
                new NettySerializationFilter.UTF16(field);
        }
        
        // special case: nested types
        if (NestedMarker.class.isAssignableFrom(field.getType()))
        {
            return new NettySerializationFilter.Nested(field, field.getType(), produceSerializer(field.getType()));
        }
        
        if (NestedMarker[].class.isAssignableFrom(field.getType()))
        {
            return new NettySerializationFilter.NestedArray(field, field.getType().getComponentType(), produceSerializer(field.getType().getComponentType()));
        }
        
        return new NettySerializationFilter.Empty();
    }
}
