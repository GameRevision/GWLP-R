/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;


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
     * Unsign a given byte array.
     * 
     * @param       ba
     * @return      The short-array, but unsigned.
     */
    public static short[] unsignByteArray(byte[] ba)
    {
        short[] result = new short[ba.length];
        
        for (int i = 0; i < ba.length; i++) 
        {
            result[i] = unsignByte(ba[i]);  
        }
        
        return result;
    }
    
    
    /**
     * Unsign a given short array.
     * 
     * @param       sa
     * @return      The int-array, but unsigned.
     */
    public static int[] unsignShortArray(short[] sa)
    {
        int[] result = new int[sa.length];
        
        for (int i = 0; i < sa.length; i++) 
        {
            result[i] = unsignShort(sa[i]);  
        }
        
        return result;
    }
    
    
    /**
     * Unsign a given int array.
     * 
     * @param       ia
     * @return      The long-array, but unsigned.
     */
    public static long[] unsignIntArray(int[] ia)
    {
        long[] result = new long[ia.length];
        
        for (int i = 0; i < ia.length; i++) 
        {
            result[i] = unsignInt(ia[i]);  
        }
        
        return result;
    }
}
