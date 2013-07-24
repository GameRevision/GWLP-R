/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.actions.gameserver.ctos.P003_UnknownAction;
import gwlpr.actions.gameserver.ctos.P005_UnknownAction;
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
 * Handles the PING actions
 * 
 * @author _rusty
 */
public class Ping extends GenericShardlet
{
    private final static Logger LOGGER = LoggerFactory.getLogger(Ping.class);
    private final static int TIMEINTERVAL = 5000; // execute each 5 seconds
    
    private int timePassed = 0;
    private long lastPingTimeStamp = System.currentTimeMillis();  // possible BUG
    private ClientLookupTable clientlookup;

    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init Ping controller");
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
        // update the time that has passed
        timePassed += event.getPassedTimeInterval();
        if (timePassed < TIMEINTERVAL) { return; }

        // if enough time has passed...
        timePassed = 0;
        lastPingTimeStamp = System.currentTimeMillis();

        for (Session session : clientlookup.getAllSessions()) 
        {
            // check if this session should receive a ping packet
            SessionAttachment attach = (SessionAttachment) session.getAttachment();
            if (attach.getPlayerState() != PlayerState.Playing) { continue; }
            
            TimeDeltaView.pingRequest(session);
        }
    }
    
    
    /**
     * We got a reply to our ping request!
     * 
     * @param pingReply 
     */
    @EventHandler
    public void onPingReply(P003_UnknownAction pingReply)
    {
        // update the client's latency
        Session session = pingReply.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        int latency = (int) (System.currentTimeMillis() - lastPingTimeStamp);
        attach.setLatency(latency);
        
        TimeDeltaView.pingReply(session, latency);
    }
    
    
    /**
     * The clients requests a ping reply from us.
     * 
     * This will send a ping reply filled with the last known
     * latency value we got.
     * 
     * @param pingRequest 
     */
    @EventHandler
    public void onPingRequest(P005_UnknownAction pingRequest)
    {
        Session session = pingRequest.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        TimeDeltaView.pingReply(session, attach.getLatency());
    }
}
