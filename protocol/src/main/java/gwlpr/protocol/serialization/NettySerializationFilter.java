/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.serialization;

import gwlpr.protocol.util.IsArray;
import gwlpr.protocol.util.NestedMarker;
import gwlpr.protocol.util.Vector2;
import gwlpr.protocol.util.Vector3;
import gwlpr.protocol.util.Vector4;
import gwlpr.protocol.util.WorldPosition;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
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
public interface NettySerializationFilter
{
    
    static final Logger LOGGER = LoggerFactory.getLogger(NettySerializationFilter.class);
    static final Charset CHARSET_UTF16 = Charset.forName("utf-16le");
    static final Charset CHARSET_ASCII = Charset.forName("us-ascii");
    
    
    /**
     * Serializes a certain field of an object and puts the data
     * into the given buffer. 
     * NO LENGTH CHECKS HERE! MAKE SURE THE BUFFER IS LONG ENOUGH!
     */
    void serialize(ByteBuf buf, Object object);
    
    
    /**
     * Deserializes a certain amount of bytes of the given buffer into
     * a certain field of the object.
     */
    boolean deserialize(ByteBuf buf, Object object);
    
    
    /**
     * Null-object.
     */
    public final static class Empty implements NettySerializationFilter
    {
        
        @Override
        public boolean deserialize(ByteBuf buf, Object object) 
        {
            return true;
        }

        
        @Override
        public void serialize(ByteBuf buf, Object object) 
        {
        }
    }
    
    
    /**
     * Chain of responsibility in a composite pattern.
     */
    public final static class Chain implements NettySerializationFilter
    {
        
        List<NettySerializationFilter> filters = new ArrayList<>();
        
        
        public void register(NettySerializationFilter filter) 
        { 
            filters.add(filter); 
        }
        
        
        @Override
        public void serialize(ByteBuf buf, Object object) 
        {
            for (NettySerializationFilter filter : filters) 
            {
                filter.serialize(buf, object);
            }
        }
        
        
        @Override
        public boolean deserialize(ByteBuf buf, Object object) 
        {
            for (NettySerializationFilter filter : filters) 
            {
                if (!filter.deserialize(buf, object))
                {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    
    /**
     * Superclass of all serialization filters
     */
    public abstract static class Generic implements NettySerializationFilter
    {
        
        protected final Field field;
        
        
        public Generic(Field field) 
        { 
            field.setAccessible(true); 
            this.field = field; 
        }
        
        
        @Override
        public void serialize(ByteBuf buf, Object object) 
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
        
        protected abstract void put(ByteBuf buf, Object object)
                throws Exception;
        
        
        @Override
        public boolean deserialize(ByteBuf buf, Object object) 
        {
            try 
            { 
                Object value = get(buf);
                
                if (value == null) 
                { 
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
        
        protected abstract Object get(ByteBuf buf)
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
        
        
        protected void setLength(ByteBuf buf, int length)
        {
            if (constant) { return; }
            
            if (prefixSize == 1)
            {
                buf.writeByte(length);
            }
            if (prefixSize == 2)
            {
                buf.writeShort(length);
            }
        }
        
        
        protected int getLength(ByteBuf buf)
        {
            if (constant) { return arraySize; }
            
            if (prefixSize == 1)
            {
                return buf.readableBytes() >= 1 ? buf.readUnsignedByte() : -1;
            }
            if (prefixSize == 2)
            {
                return buf.readableBytes() >= 2 ? buf.readUnsignedShort() : -1;
            }
            
            // unknown prefix length
            return -1;
        }
    }
    
    
    public final static class UByte extends Generic
    {
        public UByte(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            buf.writeByte(field.getShort(object)); 
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 1 ? buf.readUnsignedByte() : null;
        }
    }
    
    
    public final static class UShort extends Generic
    {
        public UShort(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            buf.writeShort(field.getInt(object)); 
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 2 ? buf.readUnsignedShort() : null;
        }
    }
    
    
    public final static class UInt extends Generic
    {
        public UInt(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            buf.writeInt((int)field.getLong(object)); 
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 4 ? buf.readUnsignedInt() : null;
        }
    }
    
    
    public final static class Long extends Generic
    {
        public Long(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            buf.writeLong(field.getLong(object)); 
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 8 ? buf.readLong() : null;
        }
    }
    
    
    public final static class Float extends Generic
    {
        public Float(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            buf.writeFloat(field.getFloat(object)); 
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 4 ? buf.readFloat() : null;
        }
    }
    
    
    public final static class Vec2 extends Generic
    {
        public Vec2(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            Vector2 vec = (Vector2)field.get(object);
            buf.writeFloat(vec.getX());
            buf.writeFloat(vec.getY());
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 8 ? new Vector2(buf.readFloat(), buf.readFloat()) : null;
        }
    }
    
    
    public final static class Vec3 extends Generic
    {
        public Vec3(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            Vector3 vec = (Vector3)field.get(object);
            buf.writeFloat(vec.getX());
            buf.writeFloat(vec.getY());
            buf.writeFloat(vec.getZ());
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 12 ? new Vector3(buf.readFloat(), buf.readFloat(), buf.readFloat()) : null;
        }
    }
    
    
    public final static class Vec4 extends Generic
    {
        public Vec4(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            Vector4 vec = (Vector4)field.get(object);
            buf.writeFloat(vec.getX());
            buf.writeFloat(vec.getY());
            buf.writeFloat(vec.getZ());
            buf.writeFloat(vec.getA());
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 16 ? new Vector4(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat()) : null;
        }
    }
    
    
    public final static class DW3 extends Generic
    {
        public DW3(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            WorldPosition wp = (WorldPosition)field.get(object);
            buf.writeFloat(wp.getX());
            buf.writeFloat(wp.getY());
            buf.writeFloat(wp.getZ());
            buf.writeInt((int)wp.getW());
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            return buf.readableBytes() >= 16 ? new WorldPosition(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readUnsignedInt()) : null;
        }
    }
    
    
    public final static class VInt extends Generic
    {
        public VInt(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            int value = field.getInt(object);
            
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

                buf.writeByte(lower7bits);
            }
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            // Taken from GW2Emu.
            boolean more = true;
            int result = 0;
            int shift = 0;

            while (more)
            {
                // failcheck
                if (buf.readableBytes() < 1) { return null; }
                
                byte lower7bits = buf.readByte();
                more = (lower7bits & 128) != 0;
                result |= (lower7bits & 0x7f) << shift;
                shift += 7;
            }

            return result;
        }
    }
    
    
    public final static class ASCII extends Generic
    {
        public ASCII(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            String txt = (String)field.get(object);
            byte[] txtBytes = txt.getBytes(CHARSET_ASCII);
            
            // put the size of the string (UTF16 char count)
            buf.writeShort(txt.length());
            
            // then put the string itself
            buf.writeBytes(txtBytes);
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            // do we have a string length indicator?
            if (buf.readableBytes() < 2) { return null; }
            
            int length = buf.readUnsignedShort();
            
            // do we have the trailing data? (length)
            if (buf.readableBytes() < length) { return null; }
            
            byte[] result = new byte[length];
            buf.readBytes(result);

            return new String(result, CHARSET_ASCII);
        }
    }

    
    public final static class UTF16 extends Generic
    {
        public UTF16(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        { 
            String txt = (String)field.get(object);
            byte[] txtBytes = txt.getBytes(CHARSET_UTF16);
            
            // put the size of the string (UTF16 char count)
            buf.writeShort(txt.length());
            
            // then put the string itself
            buf.writeBytes(txtBytes);
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            // do we have a string length indicator?
            if (buf.readableBytes() < 2) { return null; }
            
            int length = buf.readUnsignedShort();
            
            // do we have the trailing data? (length * byte-per-char)
            if (buf.readableBytes() < length * 2) { return null; }
            
            byte[] result = new byte[length * 2];
            buf.readBytes(result);

            return new String(result, CHARSET_UTF16);
        }
    }
    
    
    public final static class ByteArray extends Array
    {
        public ByteArray(Field field) { super(field); }
        
        @Override
        protected void put(ByteBuf buf, Object object)
                throws Exception
        {
            byte[] sa = (byte[])field.get(object);
            
            // put the byte-count
            setLength(buf, sa.length);
           
            // then put the array itself
            buf.writeBytes(sa);
        }
        
        @Override
        protected Object get(ByteBuf buf)
        {
            int length = getLength(buf);
            
            // failcheck (length could not be determined)
            if (length == -1) { return null; }
            
            // do we have the trailing data?
            if (buf.readableBytes() < length) { return null; }
            
            byte[] result = new byte[length];
            buf.readBytes(result);
            
            return result;
        }
    }
    
    
    public final static class Nested implements NettySerializationFilter
    {
        private final Field field;
        private final Class<?> nestedClass;
        private final NettySerializationFilter innerFilter;
        
        
        public Nested(Field field, Class<?> nestedClass, NettySerializationFilter innerFilter) 
        { 
            field.setAccessible(true); 
            this.field = field;
            this.nestedClass = nestedClass;
            this.innerFilter = innerFilter;
        }
        
        
        @Override
        public void serialize(ByteBuf buf, Object object) 
        {
            try 
            { 
                Object nested = field.get(object);
            
                // put the byte indicating whether this is null or not
                buf.writeBoolean(nested != null);

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
        public boolean deserialize(ByteBuf buf, Object object) 
        {
            try 
            { 
                Object value = nestedClass.newInstance();
            
                if (buf.readableBytes() < 1) { return false; }

                // read the byte indicating whether this is null or not
                boolean isPresent = buf.readByte() == 1;
                
                // failcheck
                if (!isPresent)
                {
                    field.set(object, null);
                    return true;
                }

                if (!innerFilter.deserialize(buf, value)) 
                { 
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
        private final NettySerializationFilter innerFilter;
        
        public NestedArray(Field field, Class<T> nestedClass, NettySerializationFilter innerFilter) 
        { 
            super(field); 
            this.nestedClass = nestedClass;
            this.innerFilter = innerFilter;
        }
        
        @Override
        protected void put(ByteBuf buf, Object object)
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
        protected Object get(ByteBuf buf)
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
