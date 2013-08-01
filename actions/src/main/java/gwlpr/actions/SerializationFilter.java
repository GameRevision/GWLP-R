/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.ASCIIString;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;
import gwlpr.actions.utils.UnsignedUtil;
import gwlpr.actions.utils.VarInt;
import gwlpr.actions.utils.Vector2;
import gwlpr.actions.utils.Vector3;
import gwlpr.actions.utils.Vector4;
import gwlpr.actions.utils.WorldPosition;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Defines the serialization methods. And standard classes.
 * 
 * All standard classes must make sure that they keep the buffer's original
 * state when they fail to deserialize something. When this happens, new 
 * incoming data must be attached to the buffer and its position must remain 
 * unchanged.
 * 
 * CAUTION! THE METHODS NEED TO BE CAREFUL WITH SIGNING!
 * 
 * @author _rusty
 */
public interface SerializationFilter
{
    
    static final Logger LOGGER = LoggerFactory.getLogger(SerializationFilter.class);
    static final Charset CHARSET = Charset.forName("utf-16le");
    
    
    /**
     * Serializes a certain field of an object and puts the data
     * into the given buffer. 
     * NO LENGTH CHECKS HERE! MAKE SURE THE BUFFER IS LONG ENOUGH!
     */
    void serialize(ByteBuffer buf, Object object);
    
    
    /**
     * Deserializes a certain amount of bytes of the given buffer into
     * a certain field of the object.
     */
    boolean deserialize(ByteBuffer buf, Object object);
    
    
    /**
     * Null-object.
     */
    public final static class Empty implements SerializationFilter
    {
        
        @Override
        public boolean deserialize(ByteBuffer buf, Object object) 
        {
            return true;
        }

        
        @Override
        public void serialize(ByteBuffer buf, Object object) 
        {
        }
    }
    
    
    /**
     * Chain of responsibility in a composite pattern.
     */
    public final static class Chain implements SerializationFilter
    {
        
        List<SerializationFilter> filters = new ArrayList<>();
        
        
        public void register(SerializationFilter filter) 
        { 
            filters.add(filter); 
        }
        
        
        @Override
        public void serialize(ByteBuffer buf, Object object) 
        {
            for (SerializationFilter filter : filters) 
            {
                filter.serialize(buf, object);
            }
        }
        
        
        @Override
        public boolean deserialize(ByteBuffer buf, Object object) 
        {
            int pos = buf.position();
            
            for (SerializationFilter filter : filters) 
            {
                if (!filter.deserialize(buf, object))
                {
                    buf.position(pos);
                    return false;
                }
            }
            
            return true;
        }
    }
    
    
    /**
     * Superclass of all serialization filters
     */
    public abstract static class Generic implements SerializationFilter
    {
        
        protected final Field field;
        
        
        public Generic(Field field) 
        { 
            field.setAccessible(true); 
            this.field = field; 
        }
        
        
        @Override
        public void serialize(ByteBuffer buf, Object object) 
        {
            try 
            { 
                put(buf, object);
            } 
            catch (Exception ex) 
            {
                LOGGER.error("Could not serialize.", ex);
            }
        }
        
        protected abstract void put(ByteBuffer buf, Object object)
                throws Exception;
        
        
        @Override
        public boolean deserialize(ByteBuffer buf, Object object) 
        {
            // save buf pos if we fail to deserialize something
            int pos = buf.position();
            
            try 
            { 
                Object value = get(buf);
                
                if (value == null) 
                { 
                    buf.position(pos);
                    return false; 
                }
                
                field.set(object, value); 
            } 
            catch (Exception ex) 
            {
                LOGGER.error("Could not deserialize.", ex); return false; 
            }
            
            return true;
        }
        
        protected abstract Object get(ByteBuffer buf)
                throws Exception;
    }
    
    
    /**
     * Arrays can use this class to automatically and safely
     * (no buffer changes when data not available) parse their length.
     */
    public static abstract class Array extends Generic
    {
        private final boolean constant;
        private final int arraySize;
        private final int prefixSize;
        
        public Array(Field field) 
        { 
            super(field); 
            
            IsArray packetArray = field.getAnnotation(IsArray.class);
            constant = packetArray.constant();
            arraySize = packetArray.size();
            prefixSize = packetArray.prefixLength();
        }
        
        
        protected void setLength(ByteBuffer buf, int length)
        {
            if (constant) { return; }
            
            if (prefixSize == 1)
            {
                buf.put((byte) length);
            }
            if (prefixSize == 2)
            {
                buf.putShort((short) length);
            }
        }
        
        
        protected int getLength(ByteBuffer buf)
        {
            if (constant) { return arraySize; }
            
            if (prefixSize == 1)
            {
                return buf.remaining() >= 1 ? UnsignedUtil.unsignByte(buf.get()) : -1;
            }
            if (prefixSize == 2)
            {
                return buf.remaining() >= 2 ? UnsignedUtil.unsignShort(buf.getShort()) : -1;
            }
            
            // unknown prefix length
            return -1;
        }
    }
    
    
    public final static class UByte extends Generic
    {
        public UByte(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            buf.put((byte)field.getShort(object)); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 1 ? UnsignedUtil.unsignByte(buf.get()) : null;
        }
    }
    
    
    public final static class UShort extends Generic
    {
        public UShort(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            buf.putShort((short)field.getInt(object)); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 2 ? UnsignedUtil.unsignShort(buf.getShort()) : null;
        }
    }
    
    
    public final static class UInt extends Generic
    {
        public UInt(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            buf.putInt((int)field.getLong(object)); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 4 ? UnsignedUtil.unsignInt(buf.getInt()) : null;
        }
    }
    
    
    public final static class ULong extends Generic
    {
        public ULong(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            BigInteger i = (BigInteger) field.get(object);
            buf.putLong(i.longValue()); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 8 ? UnsignedUtil.unsignLong(buf.getLong()) : null;
        }
    }
    
    
    public final static class Float extends Generic
    {
        public Float(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            buf.putFloat(field.getFloat(object)); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 4 ? buf.getFloat() : null;
        }
    }
    
    
    public final static class Vec2 extends Generic
    {
        public Vec2(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            Vector2 vec = (Vector2)field.get(object);
            buf.putFloat(vec.getX());
            buf.putFloat(vec.getY());
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 8 ? new Vector2(buf.getFloat(), buf.getFloat()) : null;
        }
    }
    
    
    public final static class Vec3 extends Generic
    {
        public Vec3(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            Vector3 vec = (Vector3)field.get(object);
            buf.putFloat(vec.getX());
            buf.putFloat(vec.getY());
            buf.putFloat(vec.getZ());
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 12 ? new Vector3(buf.getFloat(), buf.getFloat(), buf.getFloat()) : null;
        }
    }
    
    
    public final static class Vec4 extends Generic
    {
        public Vec4(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            Vector4 vec = (Vector4)field.get(object);
            buf.putFloat(vec.getX());
            buf.putFloat(vec.getY());
            buf.putFloat(vec.getZ());
            buf.putFloat(vec.getA());
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 16 ? new Vector4(buf.getFloat(), buf.getFloat(), buf.getFloat(), buf.getFloat()) : null;
        }
    }
    
    
    public final static class DW3 extends Generic
    {
        public DW3(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            WorldPosition wp = (WorldPosition)field.get(object);
            buf.putFloat(wp.getX());
            buf.putFloat(wp.getY());
            buf.putFloat(wp.getZ());
            buf.putInt((int)wp.getW());
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 16 ? new WorldPosition(buf.getFloat(), buf.getFloat(), buf.getFloat(), UnsignedUtil.unsignInt(buf.getInt())) : null;
        }
    }
    
    
    public final static class VInt extends Generic
    {
        public VInt(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            int value = ((VarInt)field.get(object)).get();
            
            // Taken from GW2Emu.
            boolean first = true;

            while (first || value > 0)
            {
                first = false;
                byte lower7bits = (byte)(value & 0x7f);
                value >>= 7;

                if (value > 0)
                {
                    lower7bits |= 128;
                }

                buf.put(lower7bits);
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            // Taken from GW2Emu.
            boolean more = true;
            int result = 0;
            int shift = 0;

            while (more)
            {
                // failcheck
                if (buf.remaining() < 1) { return null; }
                
                byte lower7bits = buf.get();
                more = (lower7bits & 128) != 0;
                result |= (lower7bits & 0x7f) << shift;
                shift += 7;
            }

            return new VarInt(result);
        }
    }
    
    
    public final static class ASCII extends Generic
    {
        public ASCII(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            ASCIIString txt = (ASCIIString)field.get(object);
            
            // put the size of the string (UTF16 char count)
            buf.putShort((short)txt.length());
            
            // then put the string itself
            buf.put(txt.getBytes());
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            // do we have a string length indicator?
            if (buf.remaining() < 2) { return null; }
            
            int length = UnsignedUtil.unsignShort(buf.getShort());
            
            // do we have the trailing data? (length)
            if (buf.remaining() < length) { return null; }
            
            byte[] result = new byte[length];
            buf.get(result);

            return new ASCIIString(result);
        }
    }

    
    public final static class UTF16 extends Generic
    {
        public UTF16(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            String txt = (String)field.get(object);
            
            // put the size of the string (UTF16 char count)
            buf.putShort((short)txt.length());
            
            // then put the string itself
            for (int i = 0; i < txt.length(); i++) 
            {
                buf.putChar(txt.charAt(i));
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            // do we have a string length indicator?
            if (buf.remaining() < 2) { return null; }
            
            int length = UnsignedUtil.unsignShort(buf.getShort());
            
            // do we have the trailing data? (length * byte-per-char)
            if (buf.remaining() < length * 2) { return null; }
            
            byte[] result = new byte[length * 2];
            buf.get(result);

            return new String(result, CHARSET);
        }
    }
    
    
    public final static class ByteArray extends Array
    {
        public ByteArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        {
            byte[] sa = (byte[])field.get(object);
            
            // put the byte-count
            setLength(buf, sa.length);
           
            // then put the array itself
            for (int i = 0; i < sa.length; i++) 
            {
                buf.put(sa[i]);
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // do we have the trailing data?
            if (buf.remaining() < length) { return null; }
            
            byte[] result = new byte[length];
            buf.get(result);
            
            return result;
        }
    }
    
    
    public final static class Nested implements SerializationFilter
    {
        private final Field field;
        private final Class<?> nestedClass;
        private final SerializationFilter innerFilter;
        
        
        public Nested(Field field, Class<?> nestedClass, SerializationFilter innerFilter) 
        { 
            field.setAccessible(true); 
            this.field = field;
            this.nestedClass = nestedClass;
            this.innerFilter = innerFilter;
        }
        
        
        @Override
        public void serialize(ByteBuffer buf, Object object) 
        {
            try 
            { 
                Object nested = field.get(object);
            
                // put the byte indicating whether this is null or not
                buf.put(nested != null ? (byte) 1 : (byte) 0);

                if (nested != null)
                {
                    innerFilter.serialize(buf, field.get(object));
                }
            } 
            catch (Exception ex) 
            {
                LOGGER.error("Could not serialize.", ex);
            }
        }
        
        
        @Override
        public boolean deserialize(ByteBuffer buf, Object object) 
        {
            // save buf pos if we fail to deserialize something
            int pos = buf.position();
            
            try 
            { 
                Object value = nestedClass.newInstance();
            
                if (buf.remaining() < 1) { return false; }

                // read the byte indicating whether this is null or not
                boolean isPresent = buf.get() == 1;
                
                // failcheck
                if (!isPresent)
                {
                    field.set(object, null);
                    return true;
                }

                if (!innerFilter.deserialize(buf, value)) 
                { 
                    buf.position(pos);
                    return false; 
                }
                
                field.set(object, value); 
            } 
            catch (Exception ex) 
            {
                LOGGER.error("Could not deserialize.", ex); return false; 
            }
            
            return true;
        }       
    }
    
    
    public final static class NestedArray<T extends NestedMarker> extends Array
    {
        private final Class<T> nestedClass;
        private final SerializationFilter innerFilter;
        
        public NestedArray(Field field, Class<T> nestedClass, SerializationFilter innerFilter) 
        { 
            super(field); 
            this.nestedClass = nestedClass;
            this.innerFilter = innerFilter;
        }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            Object[] oa = (Object[])field.get(object);
            
            // put the byte-count
            setLength(buf, oa.length);
           
            // then put the array itself
            for (Object o : oa) 
            {
                innerFilter.serialize(buf, o);
            }             
        }
        
        @Override
        protected Object get(ByteBuffer buf)
                throws Exception
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // TODO can this be avoided?
            T[] result = (T[]) java.lang.reflect.Array.newInstance(nestedClass, length);
            
            for (int i = 0; i < length; i++) 
            {
                T nested = nestedClass.newInstance();
                
                if (!innerFilter.deserialize(buf, nested)) 
                { 
                    return null; 
                }
                
                result[i] = nested;
            }
            
            return result;
        }
    }
}
