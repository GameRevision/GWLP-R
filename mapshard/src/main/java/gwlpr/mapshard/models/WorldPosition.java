/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import gwlpr.protocol.util.Vector2;


/**
 * Use this for the GW vector standart of:
 * (X, Y, Zplane)
 *
 * THIS CLASS DOES NOT USE THE GENERAL CODING STYLE
 *
 * @author _rusty
 */
public final class WorldPosition extends Vector2 implements Cloneable
{

    private int zPlane;


    /**
     * Constructor.
     *
     * @param       x                       Delta X
     * @param       y                       Delta Y
     * @param       zPlane                  Z-Plane (see pathing maps etc., this
     *                                      is the GW way of managing heights)
     */
    public WorldPosition(float x, float y, int zPlane)
    {
        super(x, y);
    }
    
    
    /**
     * Constructor.
     *
     * @param       vec                     The vector representing this position
     * @param       zPlane                  Z-Plane (see pathing maps etc., this
     *                                      is the GW way of managing heights)
     */
    public WorldPosition(Vector2 vec, int zPlane)
    {
        super(vec.getY(), vec.getX());
    }


    @Override
    public WorldPosition add(Vector2 vec)
    {
        return new WorldPosition(super.add(vec), zPlane);
    }


    @Override
    public WorldPosition sub(Vector2 vec)
    {
        return new WorldPosition(super.sub(vec), zPlane);
    }


    @Override
    public WorldPosition div(float div)
    {
        if (div == 0) { return null; }
        return new WorldPosition(super.div(div), zPlane);
    }


    @Override
    public WorldPosition mul(float mul)
    {
        return new WorldPosition(super.mul(mul), zPlane);
    }


    @Override
    public float getLength()
    {
        return super.getLength();
    }


    @Override
    public WorldPosition getUnit()
    {
        return this.div(getLength());
    }
    
    
    public Vector2 vecWithEndpoint(WorldPosition pos)
    {
        return new Vector2(
                pos.getX() - getX(),
                pos.getY() - getY());
    }


    public float getDistanceTo(WorldPosition pos)
    {
        return sub(pos).getLength();
    }


    public short getZPlane() 
    { 
        return (short) zPlane; 
    }
    
    
    public void setZPlane(int zPlane) 
    { 
        this.zPlane = zPlane; 
    }
    
    
    @Override
    public WorldPosition clone()
    {
        return new WorldPosition(getX(), getY(), zPlane);
    }
}
