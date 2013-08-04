/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.loginshard.ISC_EmptyMapshardNotifyAction;
import gwlpr.loginshard.ISC_ShutdownMapshardRequestAction;
import gwlpr.mapshard.ContextAttachment;
import gwlpr.mapshard.events.InstantShutDownEvent;
import gwlpr.mapshard.models.ClientLookupTable;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.events.GameAppCreatedEvent;
import realityshard.shardlet.events.HeartBeatEvent;
import realityshard.shardlet.utils.GenericShardlet;


/**
 * This controller handles the mapshard shutdown.
 * We have to listen for different events that might force us to shut down:
 * 
 * - no more clients:           ask the login shard if we can shut down,
 *                              and do so when requested
 * - internal shutdown events:  some other shardlet wants this game app to terminate.
 * - container shutdown:        the whole container is being shut down (nothing to do)
 * 
 * @author _rusty
 */
public class ShutDown extends GenericShardlet
{
    
    private int mapId;
    private ClientLookupTable clientlookup;
    
    private boolean notifySend = false;
    

    /**
     * Nothing to do here.
     */
    @Override
    protected void init() {}

    
    /**
     * Executes startup features, like storing database references etc.
     * 
     * @param       event 
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        ContextAttachment attach = ((ContextAttachment) getShardletContext().getAttachment());
        
        clientlookup = attach.getClientLookup();
        mapId = attach.getMapData().getMapID();
    }
    
    
    /**
     * Test if we do have no connected clients anymore
     * (on every server tick - is this the best way to do it?)
     * 
     * @param       event 
     */
    @EventHandler
    public void onHearBeat(HeartBeatEvent event)
    {
        // failcheck (reset notify if necessary)
        if (!clientlookup.isEmpty()) { notifySend = false; return; }
        
        // did we already send a notify?
        if (notifySend) { return; }
        
        // inform the parent login shard
        getShardletContext().getParentContext().sendTriggerableAction(new ISC_EmptyMapshardNotifyAction(mapId));
        
        notifySend = true;
    }
    
    
    /**
     * Another game app wants us to shut down.
     * (This is a possible reply to the ISC_EmptyMapshardNotifyAction)
     * 
     * TODO: BUG: (security) can this be abused by a malicious client?
     * 
     * @param action 
     */
    @EventHandler
    public void onRequestShutDown(ISC_ShutdownMapshardRequestAction action)
    {
        // we will shut down, no matter what we did before
        // this is requested, so no time is wasted on triggering last-minute events
        getShardletContext().unload();
    }
    
    
    /**
     * Another module of the game app triggered this event,
     * we will do a simple shut down with no data saving.
     * (just as above)
     * 
     * @param event 
     */
    @EventHandler
    public void onInstantShutDown(InstantShutDownEvent event)
    {
        getShardletContext().unload();
    }
}
