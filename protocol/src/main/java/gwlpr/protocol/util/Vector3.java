/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;


/**
 * A 3 dimensional vector.
 * 
 * @author _rusty
 */
public class Vector3 extends Vector2 
{
    
    private final float z;

    
    public Vector3(float x, float y, float z) 
    {
        super(x, y);
        this.z = z;
    }

    
    public float getZ() 
    {
        return z;
    }
    
    
    @Override
    protected StringBuilder getShortName(StringBuilder sb)
    {
        return sb.append("Vec3");
    }
    
    
    @Override
    protected StringBuilder getVars(StringBuilder sb)
    {
        super.getVars(sb);
        return sb.append(",Z=").append(z);
    }
}
