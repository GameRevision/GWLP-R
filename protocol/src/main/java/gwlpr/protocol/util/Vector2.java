/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.util;


/**
 * A 2 dimensional vector.
 * 
 * @author _rusty
 */
public class Vector2 
{
    
    private final float x,y;

    
    public Vector2(float x, float y) 
    {
        this.x = x;
        this.y = y;
    }

    
    public float getX() 
    {
        return x;
    }

    
    public float getY() 
    {
        return y;
    }
    

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        getShortName(sb).append("[");
        getVars(sb).append("]");
        
        return sb.toString();
    }
    
    
    protected StringBuilder getShortName(StringBuilder sb)
    {
        return sb.append("Vec2");
    }
    
    
    protected StringBuilder getVars(StringBuilder sb)
    {
        return sb.append("X=").append(x).append(",Y=").append(y);
    }
}
