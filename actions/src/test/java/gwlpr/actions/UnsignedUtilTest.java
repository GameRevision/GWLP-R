/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.UnsignedUtil;
import java.math.BigInteger;
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
        assert UnsignedUtil.unsignLong(-564567454).compareTo(BigInteger.ZERO) >= 0;
    }
}
