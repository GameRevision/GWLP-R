/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;


/**
 * Use this for the GW vector standart of:
 * (X, Y, Zplane)
 *
 * THIS CLASS DOES NOT USE THE GENERAL CODING STYLE
 *
 * @author _rusty
 */
public final class GWVector
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

    public short getZPlane() { return (short) zPlane; }
    public void setZPlane(int zPlane) { this.zPlane = zPlane; }


    public GWVector add(GWVector vec)
    {
        return new GWVector(
                x + vec.getX(),
                y + vec.getY(),
                zPlane);
    }


    public GWVector sub(GWVector vec)
    {
        return new GWVector(
                x - vec.getX(),
                y - vec.getY(),
                zPlane);
    }


    public GWVector div(float div)
    {
        if (div == 0) return null;
        return new GWVector(
                x / div,
                y / div,
                zPlane);
    }


    public GWVector mul(float mul)
    {
        return new GWVector(
                x * mul,
                y * mul,
                zPlane);
    }


    public float getLength()
    {
        return (float) Math.sqrt((x * x) + (y * y));
    }


    public GWVector getUnit()
    {
        return this.div(getLength());
    }


    /**
     * This method regards this class as absolute (points in the coord)
     * @param pt
     * @return
     */
    public float getDistanceTo(GWVector pt)
    {
        return sub(pt).getLength();
    }

    /**
     * Turn this gw vector into the float array used by GW.
     * This will not include the z-plane!
     *
     * @return      The x/y float array.
     */
    public float[] toFloatArray()
    {
        return new float[] {x, y};
    }


    /**
     * Turn this gw vector into a rotation value (radian measure?)
     *
     * @return
     */
    public float toRotation()
    {
        return (float) Math.atan2(y, x);
    }


    /**
     * Convert a float array plus zplane to a GWVector.
     *
     * @param       ar                      The float array.
     * @param       zPlane                  The actual z-plane
     * @return      A new GWVector, or null if the float array could not be read.
     */
    public static GWVector fromFloatArray(float[] ar, int zPlane)
    {
        // failcheck
        if (ar.length != 2) { return null; }

        return new GWVector(ar[0], ar[1], zPlane);
    }


    /**
     * Convert a rotation and r (polar coord) to a GWVector.
     * No Z plane given, cause rotation usually doesnt have a plane.
     *
     * @param       rotation                The rotation in radian measure.
     * @param       r                       Distance from coord zero.
     * @return      A new GWVector.
     */
    public static GWVector fromRotation(float rotation, float r)
    {
        float x = (float) (r * Math.cos(rotation));
        float y = (float) (r * Math.sin(rotation));

        return new GWVector(x, y, 0);
    }
}
