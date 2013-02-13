/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;


/**
 * Use this for the GW vector standart of:
 * (X, Y, Zplane)
 * 
 * THIS CLASS DOES NOT USE THE GENERAL CODING STYLE
 * 
 * @author _rusty
 */
public class GWVector 
{
    private float x;
    private float y;
    private int zPlane;
    
    
    /**
     * Constructor.
     * 
     * @param       x                       Delta X
     * @param       y                       Delta Y
     * @param       zPlane                  Z-Plane (see pathing maps etc., this
     *                                      is the GW way of managing heights)
     */
    public GWVector(float x, float y, int zPlane)
    {
        this.x = x;
        this.y = y;
        this.zPlane = zPlane;
    }

    
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public int getZPlane() { return zPlane; }
    public void setZPlane(int zPlane) { this.zPlane = zPlane; }    
}
