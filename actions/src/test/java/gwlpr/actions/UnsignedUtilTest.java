/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.UnsignedUtil;
import org.junit.Test;


/**
 *
 * @author _rusty
 */
public class UnsignedUtilTest 
{
    @Test
    public void test()
    {
        assert UnsignedUtil.unsignByte((byte)-120) > 0;
        assert UnsignedUtil.unsignShort((short)-30000) > 0;
        assert UnsignedUtil.unsignInt(-120000) > 0;
        
        short[] sa = UnsignedUtil.unsignByteArray(new byte[] {(byte)-0xAA, (byte)0x36, (byte)-0x71, (byte)0x01});
        for (int i = 0; i < sa.length; i++) { assert sa[i] > 0; }
        
        int[] ia = UnsignedUtil.unsignShortArray(new short[] {(short)-193, (short)1674, (short)-73895, (short)1});
        for (int i = 0; i < ia.length; i++) { assert ia[i] > 0; }
        
        long[] la = UnsignedUtil.unsignIntArray(new int[] {-193345, 167465, -738951, 15675678});
        for (int i = 0; i < la.length; i++) { assert la[i] > 0; }
    }
}
