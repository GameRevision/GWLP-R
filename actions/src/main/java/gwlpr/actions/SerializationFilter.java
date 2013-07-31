/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.UnsignedUtil;
import gwlpr.actions.utils.Vector2;
import gwlpr.actions.utils.Vector3;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
    
    
    public final static class UTF16Char extends Generic
    {
        public UTF16Char(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            buf.putChar(field.getChar(object)); 
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            return buf.remaining() >= 2 ? new String(new byte[] {buf.get(), buf.get()}, CHARSET).charAt(0): null;
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
    
    
    public final static class Nested extends Generic
    {
        private final Class<?> nestedClass;
        private final SerializationFilter innerFilter;
        
        public Nested(Field field, Class<?> nestedClass, SerializationFilter innerFilter) 
        { 
            super(field); 
            this.nestedClass = nestedClass;
            this.innerFilter = innerFilter;
        }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        { 
            innerFilter.serialize(buf, field.get(object));
        }
        
        @Override
        protected Object get(ByteBuffer buf)
                throws Exception
        {
            Object result = nestedClass.newInstance();
                
            if (!innerFilter.deserialize(buf, result)) 
            { 
                return null; 
            }
            
            return result;
        }
    }

    
    public final static class UTF16String extends Generic
    {
        public UTF16String(Field field) { super(field); }
        
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
    
    
    public final static class UByteArray extends Array
    {
        public UByteArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        {
            short[] sa = (short[])field.get(object);
            
            // put the byte-count
            setLength(buf, sa.length);
           
            // then put the array itself
            for (int i = 0; i < sa.length; i++) 
            {
                buf.put((byte)sa[i]);
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
            
            return UnsignedUtil.unsignByteArray(result);
        }
    }
    
    
    public final static class UShortArray extends Array
    {
        public UShortArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        {
            int[] ia = (int[])field.get(object);
            
            // put the ushort-count
            setLength(buf, ia.length);
           
            // then put the array itself
            for (int i = 0; i < ia.length; i++) 
            {
                buf.putShort((short)ia[i]);
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // do we have the trailing data? (length * bytes-of-a-short)
            if (buf.remaining() < length * 2) { return null; }
            
            short[] result = new short[length];
            
            for (int i = 0; i < result.length; i++) 
            {
                result[i] = buf.getShort();
            }
            
            return UnsignedUtil.unsignShortArray(result);
        }
    }
    
    
    public final static class UIntArray extends Array
    {
        public UIntArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        {
            long[] la = (long[])field.get(object);
            
            // put the ushort-count
            setLength(buf, la.length);
           
            // then put the array itself
            for (int i = 0; i < la.length; i++) 
            {
                buf.putInt((int)la[i]);
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // do we have the trailing data? (length * bytes-of-an-int)
            if (buf.remaining() < length * 4) { return null; }
            
            int[] result = new int[length];
            
            for (int i = 0; i < result.length; i++) 
            {
                result[i] = buf.getInt();
            }
            
            return UnsignedUtil.unsignIntArray(result);
        }
    }

    
    public final static class FloatArray extends Array
    {
        public FloatArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuffer buf, Object object)
                throws Exception
        {
            float[] fa = (float[])field.get(object);
            
            // put the ushort-count
            setLength(buf, fa.length);
           
            // then put the array itself
            for (int i = 0; i < fa.length; i++) 
            {
                buf.putFloat(fa[i]);
            }
        }
        
        @Override
        protected Object get(ByteBuffer buf)
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // do we have the trailing data? (length * bytes-of-a-float)
            if (buf.remaining() < length * 4) { return null; }
            
            float[] result = new float[length];
            
            for (int i = 0; i < result.length; i++) 
            {
                result[i] = buf.getFloat();
            }
            
            return result;
        }
    }
    
    
    public final static class NestedArray extends Array
    {
        private final Class<?> nestedClass;
        private final SerializationFilter innerFilter;
        
        public NestedArray(Field field, Class<?> nestedClass, SerializationFilter innerFilter) 
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
            
            Object[] result = new Object[length];
            
            for (int i = 0; i < length; i++) 
            {
                Object nested = nestedClass.newInstance();
                
                if (!innerFilter.deserialize(buf, nested)) 
                { 
                    return null; 
                }
            }
            
            return result;
        }
    }
}
