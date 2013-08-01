/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;


/**
 * Helps by unsigning given values.
 * 
 * @author _rusty
 */
public final class UnsignedUtil 
{
    
    /**
     * Unsign a given byte.
     * 
     * @param       b
     * @return      The short value, but unsigned.
     */
    public static short unsignByte(byte b)
    {
        return (short) (b & 0xFF);
    }
    
    
    /**
     * Unsign a given short.
     * 
     * @param       i
     * @return      The int value, but unsigned.
     */
    public static int unsignShort(short s)
    {
        return (int) (s & 0xFFFF);
    }
    
    
    /**
     * Unsign a given int.
     * 
     * @param       i
     * @return      The long value, but unsigned.
     */
    public static long unsignInt(int i)
    {
        return (int) (i & 0xFFFF);
    }
    
    
    /**
     * Unsign a given long.
     * 
     * @param       l
     * @return      The big-integer value, but unsigned.
     */
    public static BigInteger unsignLong(long l)
    {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(l);
        return new BigInteger(1, buffer.array());
    }
}
