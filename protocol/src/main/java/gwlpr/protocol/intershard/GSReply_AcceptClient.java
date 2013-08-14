/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard;

import java.util.UUID;
import realityshard.container.events.Event;

/**
 * Answer of the AcceptSessionRequest from a MapShard to the LoginShard.
 * This determines whether the map shard wants to accept the client or not.
 *
 * @author miracle444, _rusty
 */
public final class GSReply_AcceptClient implements Event
{
    
    private final UUID serverUid;
    private final UUID clientUid;
    private final boolean accepted;
    
    
    /**
     * Constructor.
     * 
     * @param       serverUid 
     * @param       clientUid
     * @param       accepted  
     */
    public GSReply_AcceptClient(UUID serverUid, UUID clientUid, boolean accepted)
    {
        this.serverUid = serverUid;
        this.clientUid = clientUid;
        this.accepted = accepted;
    }

    
    public UUID getServerUid() 
    {
        return serverUid;
    }

    
    public UUID getClientUid() 
    {
        return clientUid;
    }
    

    public boolean isAccepted() 
    {
        return accepted;
    }
}