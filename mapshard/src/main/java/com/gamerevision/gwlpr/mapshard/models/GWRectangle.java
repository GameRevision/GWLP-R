/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;


/**
 * Use this class for bounding rects etc.
 * 
 * THIS CLASS DOES NOT USE THE GENERAL CODING STYLE
 * 
 * @author _rusty
 */
public class GWRectangle 
{
    private GWVector topLeftCorner;
    private float width;
    private float height;
    
    
    /**
     * Constructor.
     * 
     * @param       topLeftCorner           The absolute position of the top left
     *                                      corner of this rect.
     * @param       width                   Width/Height are always meant in alignment
     * @param       height                  with the general coord sys.
     */
    public GWRectangle(GWVector topLeftCorner, float width, float height)
    {
        this.topLeftCorner = topLeftCorner;
        this.width = width;
        this.height = height;
    }
    
    
    /**
     * Factory method.
     * 
     * @param       center                  The center of the rectangle
     * @param       width                   Width/Height are always meant in alignment
     * @param       height                  with the general coord sys.
     * @return 
     */
    public static GWRectangle createWithCenter(GWVector center, float width, float height)
    {
        // simply calculate the top left corner depending on the center of this rect
        GWVector newTopLeftCorner = new GWVector(
                center.getX() - width/2, 
                center.getY() + height/2, 
                center.getZPlane());
        
        return new GWRectangle(newTopLeftCorner, width, height);
    }

    
    public GWVector getTopLeftCorner() { return topLeftCorner; }
    public void setTopLeftCorner(GWVector topLeftCorner) { this.topLeftCorner = topLeftCorner; }

    public float getWidth() { return width; }
    public void setWidth(float width) { this.width = width; }

    public float getHeight() { return height; }
    public void setHeight(float height) { this.height = height; }
}
