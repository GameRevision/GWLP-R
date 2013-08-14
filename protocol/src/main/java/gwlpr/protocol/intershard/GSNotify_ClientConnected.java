/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard;

import java.util.UUID;
import realityshard.container.events.Event;


/**
 * Notify the login shard that a client has successfully established a connection.
 *
 * @author _rusty
 */
public final class GSNotify_ClientConnected implements Event
{
    
    private final UUID serverUid;
    private final UUID clientUid;
    
    
    /**
     * Constructor.
     * 
     * @param       serverUid 
     * @param       clientUid
     */
    public GSNotify_ClientConnected(UUID serverUid, UUID clientUid)
    {
        this.serverUid = serverUid;
        this.clientUid = clientUid;
    }

    
    public UUID getServerUid() 
    {
        return serverUid;
    }

    
    public UUID getClientUid() 
    {
        return clientUid;
    }
}