/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard.utils;


/**
 * This enum holds all possible region types of a mapshard outpost
 * 
 * @author _rusty
 */
public enum DistrictRegion 
{
    International   (-2),
    Default         (-1),
    Amerika         (0),
    Korea           (1),
    Europe          (2),
    China           (3),
    Japan           (4);
        
    private final int id;

    private DistrictRegion(int id) 
    {
        this.id = id;
    }
    
    public short get()
    {
        return (short)id;
    }
}
