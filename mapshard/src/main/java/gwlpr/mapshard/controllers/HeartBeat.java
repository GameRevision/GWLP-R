/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.ContextAttachment;
import gwlpr.mapshard.SessionAttachment;
import gwlpr.mapshard.models.ClientLookupTable;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.views.TimeDeltaView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.events.HeartBeatEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet periodically publishes the server tick time interval
 * to all clients.
 * 
 * @author _rusty
 */
public class HeartBeat extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(HeartBeat.class);
    
    private ClientLookupTable clientlookup;

    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init HeartBeat controller");
    }
    
    
    /**
     * Executes startup features, like storing database references etc.
     * 
     * @param event 
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        ContextAttachment attach = ((ContextAttachment) getShardletContext().getAttachment());
        clientlookup = attach.getClientLookup();
    }
    
    
    /**
     * Event handler.
     * Pumps out the server tick time-interval to all connected clients.
     * 
     * @param event 
     */
    @EventHandler
    public void onHeartBeat(HeartBeatEvent event)
    {
        for (Session session : clientlookup.getAllSessions()) 
        {
            // check if this session should receive a heartbeat packet
            SessionAttachment attach = (SessionAttachment) session.getAttachment();
            if (attach.getPlayerState() != PlayerState.Playing) { continue; }
            
            TimeDeltaView.heartBeat(session, event.getPassedTimeInterval());
        }
    }
}
