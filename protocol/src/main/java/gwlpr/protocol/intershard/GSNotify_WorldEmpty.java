/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard;

import java.util.UUID;
import realityshard.container.events.Event;


/**
 * This action is send by the mapshard, 
 * if there is no client connected to it anymore.
 * 
 * @author _rusty
 */
public class GSNotify_WorldEmpty implements Event
{
    
    private final UUID serverUid;
    
    
    /**
     * Constructor.
     * 
     * @param       serverUid 
     */
    public GSNotify_WorldEmpty(UUID serverUid)
    {
        this.serverUid = serverUid;
    }

    
    public UUID getServerUid() 
    {
        return serverUid;
    }
}
