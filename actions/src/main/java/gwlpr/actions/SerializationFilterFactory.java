/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;
import gwlpr.actions.utils.Vector2;
import gwlpr.actions.utils.Vector3;
import java.lang.reflect.Field;


/**
 * Creates a serializer chain for given generic action.
 * 
 * @author _rusty
 */
public final class SerializationFilterFactory 
{
    
    /**
     * Creates a new serializer chain. Should be used once per session.
     * 
     * @param       <T>                     Type of the action it serializes
     * @param       clazz                   The actual class.
     * @return      The serialization methods, chained.
     */
    public static SerializationFilter produceSerializer(Class<?> clazz)
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
    
    
    private static SerializationFilter createFilter(Field field)
    {        
        // note that we need to use the next bigger type,
        // because we cant have unsigned values.
        if (field.getType().equals(char.class))     { return new SerializationFilter.UTF16Char(field); }
        if (field.getType().equals(short.class))    { return new SerializationFilter.UByte(field); }
        if (field.getType().equals(int.class))      { return new SerializationFilter.UShort(field); }
        if (field.getType().equals(long.class))     { return new SerializationFilter.UInt(field); }
        if (field.getType().equals(float.class))    { return new SerializationFilter.Float(field); }
        if (field.getType().equals(Vector2.class))  { return new SerializationFilter.Vec2(field); }
        if (field.getType().equals(Vector3.class))  { return new SerializationFilter.Vec3(field); }
        if (field.getType().equals(String.class))   { return new SerializationFilter.UTF16String(field); }
        if (field.getType().equals(short[].class))  { return new SerializationFilter.UByteArray(field); }
        if (field.getType().equals(int[].class))    { return new SerializationFilter.UShortArray(field); }
        if (field.getType().equals(long[].class))   { return new SerializationFilter.UIntArray(field); }
        if (field.getType().equals(float[].class))  { return new SerializationFilter.FloatArray(field); }
        
        if (field.getType().isAssignableFrom(NestedMarker.class))
        {
            if (field.getAnnotation(IsArray.class) == null)
            {
                return new SerializationFilter.Nested(field, field.getType(), produceSerializer(field.getType()));
            }
            else
            {
                return new SerializationFilter.NestedArray(field, field.getType(), produceSerializer(field.getType()));
            }
        }
        
        return new SerializationFilter.Empty();
    }
}
