/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;


/**
 * A 4 dimensional Vector.
 * 
 * @author _rusty
 */
public class Vector4 extends Vector3 
{
    
    private final float a;

    
    public Vector4(float x, float y, float z, float a) 
    {
        super(x, y, z);
        this.a = a;
    }

    
    public float getA() 
    {
        return a;
    }
    
    
    @Override
    protected StringBuilder getShortName(StringBuilder sb)
    {
        return sb.append("Vec4");
    }
    
    
    @Override
    protected StringBuilder getVars(StringBuilder sb)
    {
        super.getVars(sb);
        return sb.append(",A=").append(a);
    }
}
