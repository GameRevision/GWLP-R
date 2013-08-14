/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import gwlpr.database.entities.Map;
import gwlpr.protocol.intershard.utils.DistrictLanguage;
import gwlpr.protocol.intershard.utils.DistrictRegion;


/**
 * Storage class for data about this shard.
 * 
 * @author _rusty
 */
public class WorldBean 
{
    private final Map map;
    private final boolean isOutpost;
    private final boolean isPvP;
    private final boolean isCharCreate;
    
    // outpost only values
    private final int instanceNumber;
    private final DistrictRegion region;
    private final DistrictLanguage language;

    
    public WorldBean(Map map, int instanceNumber, DistrictRegion region, DistrictLanguage language, boolean isPvP, boolean isOutpost)
    {
        this.map = map;
        this.isOutpost = isOutpost;
        this.isPvP = isPvP;
        this.isCharCreate = map.getGameID() == 0;
        
        this.instanceNumber = instanceNumber;
        this.region = region;
        this.language = language;
    }

    
    public Map getMap() 
    {
        return map;
    }

    
    public boolean isOutpost() 
    {
        return isOutpost;
    }

    
    public boolean isPvP() 
    {
        return isPvP;
    }

    
    public boolean isCharCreate() 
    {
        return isCharCreate;
    }

    
    public int getInstanceNumber() 
    {
        return instanceNumber;
    }

    
    public DistrictRegion getRegion() 
    {
        return region;
    }

    
    public DistrictLanguage getLanguage() 
    {
        return language;
    }    
}
