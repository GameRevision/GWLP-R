/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.intershardcom;

import realityshard.shardlet.utils.GenericAction;


/**
 * This action is send by the mapshard, 
 * if there is no client connected to it anymore.
 * 
 * @author _rusty
 */
public class ISC_EmptyMapshardNotifyAction extends GenericAction
{
    
    private int mapId;
    
    
    /**
     * Constructor.
     * 
     * @param       mapId                   The db-related map ID of this mapshard. 
     */
    public ISC_EmptyMapshardNotifyAction(int mapId)
    {
        init(null); // no session needed here!
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
