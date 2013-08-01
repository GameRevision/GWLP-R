/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.ASCIIString;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;
import gwlpr.actions.utils.VarInt;
import gwlpr.actions.utils.Vector2;
import gwlpr.actions.utils.Vector3;
import gwlpr.actions.utils.Vector4;
import gwlpr.actions.utils.WorldPosition;
import java.math.BigInteger;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * Code taken from iDemmel (with permission).
 * 
 * @author _rusty
 */
@Ignore
public class P008_TestPacket
    extends GWAction
{
    public short uByte;
    public int uShort;
    public long uInt;
    public BigInteger uLong;
    public float single;
    public Vector2 vec2;
    public Vector3 vec3;
    public Vector4 vec4;
    public WorldPosition dw3;
    public VarInt vint;
    public ASCIIString ascii;
    public String utf16;
    
    @IsArray(constant=true, size=2, prefixLength=-1)
    public byte[] constBuf;
    
    @IsArray(constant=false, size=-1, prefixLength=1)
    public byte[] varBufPre1;
    @IsArray(constant=false, size=-1, prefixLength=2)
    public byte[] varBufPre2;
    
    public Nested nestedNull;
    public Nested nestedNotNull;
    
    @IsArray(constant=true, size=2, prefixLength=-1)
    public Nested[] constNestAr;
    
    @IsArray(constant=false, size=-1, prefixLength=1)
    public Nested[] varNestArPre1;
    @IsArray(constant=false, size=-1, prefixLength=2)
    public Nested[] varNestArPre2;
    
    
    @Override
    public short getHeader()
    {
        return 8;
    }
    

    public final static class Nested implements NestedMarker
    {
        public short uByte;
        public NestedNested nested;

        
        public final static class NestedNested implements NestedMarker
        {
            public short uByte;
        }
    }
    
    
    public static P008_TestPacket getMockUp()
    {
        Nested.NestedNested nn = new Nested.NestedNested();
        nn.uByte = 200;
        
        Nested n = new Nested();
        n.uByte = 100;
        n.nested = nn;
        
        P008_TestPacket p = new P008_TestPacket();
        p.uByte = 1;
        p.uShort = 2;
        p.uInt = 3;
        p.uLong = new BigInteger("4");
        p.single = 5.0F;
        p.vec2 = new Vector2(6.0F, 7.0F);
        p.vec3 = new Vector3(8.0F, 9.0F, 10.0F);
        p.vec4 = new Vector4(11.0F, 12.0F, 13.0F, 14.0F);
        p.dw3 = new WorldPosition(15.0F, 16.0F, 17.0F, 18);
        p.vint = new VarInt(19);
        p.ascii = new ASCIIString("20");
        p.utf16 = "21";
        p.constBuf = new byte[] {(byte)22, (byte)23};
        p.varBufPre1 = new byte[] {(byte)24, (byte)25, (byte)26};
        p.varBufPre2 = new byte[] {(byte)27, (byte)28, (byte)29, (byte)30};
        p.nestedNull = null;
        p.nestedNotNull = n;
        p.constNestAr = new Nested[] {n, n};
        p.varNestArPre1 = new Nested[] {n, n, n};
        p.varNestArPre2 = new Nested[] {n, n, n, n};
        
        return p;
    }
    
    
    public static void assertCompare(P008_TestPacket p1, P008_TestPacket p2)
    {
        assertEquals(p1.uByte, p2.uByte);
        assertEquals(p1.uShort, p2.uShort);
        assertEquals(p1.uInt, p2.uInt);
        assertEquals(p1.uLong, p2.uLong);
        assert (p1.single == p2.single);
        assert (p1.vec2.getX()== p2.vec2.getX()) && (p1.vec2.getY() == p2.vec2.getY());
        assert (p1.vec3.getX() == p2.vec3.getX()) && (p1.vec3.getY()== p2.vec3.getY()) && (p1.vec3.getZ() == p2.vec3.getZ());
        assert (p1.vec4.getX() == p2.vec4.getX()) && (p1.vec4.getY()== p2.vec4.getY()) && (p1.vec4.getZ() == p2.vec4.getZ()) &&(p1.vec4.getA() == p2.vec4.getA());
        assert (p1.dw3.getX() == p2.dw3.getX()) && (p1.dw3.getY()== p2.dw3.getY()) && (p1.dw3.getZ() == p2.dw3.getZ()) &&(p1.dw3.getW() == p2.dw3.getW());
        assertEquals(p1.vint.get(), p2.vint.get());
        assertEquals(p1.ascii.toString(), p2.ascii.toString());
        assertEquals(p1.utf16, p2.utf16);
        assertThat(p1.constBuf, equalTo(p2.constBuf));
        assertThat(p1.varBufPre1, equalTo(p2.varBufPre1));
        assertThat(p1.varBufPre2, equalTo(p2.varBufPre2));
        assertEquals(p1.nestedNull, p2.nestedNull);
        
        assertEquals(p1.nestedNotNull.uByte, p2.nestedNotNull.uByte);
        assertEquals(p1.nestedNotNull.nested.uByte, p2.nestedNotNull.nested.uByte);
                
        for (int i = 0; i < 2; i++) 
        {
            assertEquals(p1.constNestAr[i].uByte, p2.constNestAr[i].uByte);
            assertEquals(p1.constNestAr[i].nested.uByte, p2.constNestAr[i].nested.uByte);
        }
        
        assertEquals(p1.varNestArPre1.length, p2.varNestArPre1.length);
        
        for (int i = 0; i < p1.varNestArPre1.length; i++) 
        {
            assertEquals(p1.varNestArPre1[i].uByte, p2.varNestArPre1[i].uByte);
            assertEquals(p1.varNestArPre1[i].nested.uByte, p2.varNestArPre1[i].nested.uByte);
        }
        
        assertEquals(p1.varNestArPre2.length, p2.varNestArPre2.length);
        
        for (int i = 0; i < p1.varNestArPre2.length; i++) 
        {
            assertEquals(p1.varNestArPre2[i].uByte, p2.varNestArPre2[i].uByte);
            assertEquals(p1.varNestArPre2[i].nested.uByte, p2.varNestArPre2[i].nested.uByte);
        }
    }
}
