/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;


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
}
