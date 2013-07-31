/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;


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
}
