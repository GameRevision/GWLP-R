/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.GenericTriggerableAction;


/**
 * This action is send by the mapshard, 
 * if there is no client connected to it anymore.
 * 
 * @author _rusty
 */
public class ISC_EmptyMapshardNotifyAction extends GenericTriggerableAction
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
    
    
    /**
     * This method is used by the context of a game app, when this action is 
     * transmitted to another game app.
     * The purpose is to let the action trigger the event by itself instead of
     * having to know the exact type.
     */
    @Override
    public void triggerEvent(EventAggregator aggregator)
    {
        aggregator.triggerEvent(this);
    }
}
