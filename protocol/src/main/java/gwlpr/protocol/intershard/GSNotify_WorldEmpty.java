/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard;

import realityshard.container.events.Event;


/**
 * This action is send by the mapshard, 
 * if there is no client connected to it anymore.
 * 
 * @author _rusty
 */
public class GSNotify_WorldEmpty implements Event
{
    
    private int mapId;
    
    
    /**
     * Constructor.
     * 
     * @param       mapId                   The db-related map ID of this mapshard. 
     */
    public GSNotify_WorldEmpty(int mapId)
    {
        this.mapId = mapId;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The map id of this MapShard.
     */
    public int getMapId()
    {
        return this.mapId;
    }
}
