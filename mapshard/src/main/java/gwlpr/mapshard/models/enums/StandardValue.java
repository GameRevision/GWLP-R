/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models.enums;


/**
 * Some static GW size standards
 * 
 * @author _rusty
 */
public enum StandardValue 
{
    TerrainTile (96), // tt
    
    RangeAdjacent       (156), // or 144? tt*1.5
    RangeNearby         (240), // tt*2.5
    RangeArea           (312), // or 336? tt*3.5
    RangeHalf           (624), // or 672? tt*7
    RangeEarshot        (1000), // aggro bubble ... or 1008? tt*10.5
    RangeSpiritAttack   (1350), // or 1344? tt*14
    RangeSpiritPassive  (2500), // or 2304? tt*24 
    RangeCompass        (5000), // or 5040? tt*52.5
    
    PlayerSpeed (StandardValue.TerrainTile.getVal() * 3); // 3 terrain tiles per second
    
    private final float val;

    StandardValue(float val) 
    {    
        this.val = val;
    }

    public float getVal() { return val; }
}
