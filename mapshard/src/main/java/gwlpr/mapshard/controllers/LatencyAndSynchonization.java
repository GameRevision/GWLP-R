/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.events.HeartBeatEvent;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.views.TimeDeltaView;
import gwlpr.protocol.gameserver.inbound.P003_PingReply;
import gwlpr.protocol.gameserver.inbound.P005_PingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This shardlet periodically publishes the server tick time interval
 * to all clients.
 * 
 * @author _rusty
 */
public class LatencyAndSynchonization
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LatencyAndSynchonization.class);
    private final static int TIMEINTERVAL = 5000; // execute each 5 seconds
    
    private int timePassed = 0;
    private long lastPingTimeStamp = System.currentTimeMillis();  // possible BUG
    
    private final HandleRegistry<ClientBean> clientRegistry;
    
    
    /**
     * Constructor.
     * 
     * @param       clientRegistry 
     */
    public LatencyAndSynchonization(HandleRegistry<ClientBean> clientRegistry)
    {
        this.clientRegistry = clientRegistry;
    }
    
    
    /**
     * Event handler.
     * Pumps out the server tick time-interval to all connected clients.
     * 
     * @param event 
     */
    @Event.Handler
    public void onHeartBeat(HeartBeatEvent event)
    {
        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles()) 
        {
            // check if this session should receive a heartbeat packet
            if (clientHandle.get().getPlayerState() != PlayerState.Playing) { continue; }
            
            TimeDeltaView.heartBeat(clientHandle.get().getChannel(), event.getPassedTimeInterval());
        }
    }
    
    
    /**
     * Event handler.
     * Ping each client once in a while.
     * 
     * TODO: Maybe we should add an extra event and pacemaker for that reason!
     * 
     * @param event 
     */
    @Event.Handler
    public void onPingTimeout(HeartBeatEvent event)
    {
        // update the time that has passed
        timePassed += event.getPassedTimeInterval();
        if (timePassed < TIMEINTERVAL) { return; }

        // if enough time has passed...
        timePassed = 0;
        lastPingTimeStamp = System.currentTimeMillis();

        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles()) 
        {
            // check if this session should receive a heartbeat packet
            if (clientHandle.get().getPlayerState() != PlayerState.Playing) { continue; }
            
            TimeDeltaView.pingRequest(clientHandle.get().getChannel());
        }
    }
    
    
    /**
     * We got a reply to our ping request!
     * 
     * @param pingReply 
     */
    @Event.Handler
    public void onPingReply(P003_PingReply pingReply)
    {
        // update the client's latency
        ClientBean client = ClientBean.get(pingReply.getChannel());
        
        int latency = (int) (System.currentTimeMillis() - lastPingTimeStamp);
        client.setLatency(latency);
        
        TimeDeltaView.pingReply(client.getChannel(), latency);
    }
    
    
    /**
     * The clients requests a ping reply from us.
     * 
     * This will send a ping reply filled with the last known
     * latency value we got.
     * 
     * @param pingRequest 
     */
    @Event.Handler
    public void onPingRequest(P005_PingRequest pingRequest)
    {
        ClientBean client = ClientBean.get(pingRequest.getChannel());
        
        TimeDeltaView.pingReply(client.getChannel(), client.getLatency());
    }
}
