/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;


/**
 * Represents a world position (3 coords, 1 plane?)
 * 
 * @author _rusty
 */
public class WorldPosition extends Vector3
{
    
    private final long w;

    
    public WorldPosition(float x, float y, float z, long w) 
    {
        super(x, y, z);
        this.w = w;
    }

    
    public long getW() 
    {
        return w;
    }
}
