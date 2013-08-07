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
    
    
    public Vector2 add(Vector2 vec)
    {
        return new Vector2(
                getX() + vec.getX(),
                getY() + vec.getY());
    }


    public Vector2 sub(Vector2 vec)
    {
        return new Vector2(
                x - vec.getX(),
                y - vec.getY());
    }


    public Vector2 div(float div)
    {
        if (div == 0) { return null; }
        return new Vector2(
                x / div,
                y / div);
    }


    public Vector2 mul(float mul)
    {
        return new Vector2(
                x * mul,
                y * mul);
    }
    
    
    public float angleTo(Vector2 vec)
    {
        return (float)Math.atan2(vec.getY() - y, vec.getX() - x);
    }


    public float getLength()
    {
        return (float) Math.sqrt((x * x) + (y * y));
    }


    public Vector2 getUnit()
    {
        return this.div(getLength());
    }
    
    
    public Vector2 applyRotation(float cos, float sin)
    {
        return new Vector2(
                x*cos - y*sin, 
                x*sin + y*cos);
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
