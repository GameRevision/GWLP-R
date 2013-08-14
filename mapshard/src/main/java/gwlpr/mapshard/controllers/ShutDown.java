/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.models.HandleRegistryNotificationDecorator;
import gwlpr.mapshard.models.Pacemaker;
import gwlpr.protocol.intershard.GSNotify_WorldEmpty;
import realityshard.container.events.Event;
import realityshard.container.events.GameAppUnloadedEvent;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.util.Handle;


/**
 * This controller handles the mapshard shutdown.
 * We have to listen for different events that might force us to shut down:
 * 
 * - no more clients:           notify the login server of our emptiness :P
 * - unload:                    the game app is being forced to shutdown. we may need
 *                              to save data or something....
 * 
 * @author _rusty
 */
public class ShutDown
{

    private final Handle<GameAppContext> context;
    private final Handle<GameAppContext> loginShard;
    private final Pacemaker update;
    private final Pacemaker heartbeat;
    
    
    /**
     * Constructor.
     *  
     * @param       context
     * @param       loginShard
     * @param       update
     * @param       heartbeat  
     */
    public ShutDown(Handle<GameAppContext> context, Handle<GameAppContext> loginShard, Pacemaker update, Pacemaker heartbeat)
    {
        this.context = context;
        this.loginShard = loginShard;
        this.update = update;
        this.heartbeat = heartbeat;
    }
    
    
    /**
     * Event handler. Gets invoked when the client registry reports of being empty
     * 
     * @param       event 
     */
    @Event.Handler
    public void onClientRegistryEmpty(HandleRegistryNotificationDecorator.Empty event)
    {       
        // inform the parent login shard
        loginShard.get().trigger(
                new GSNotify_WorldEmpty(context.getUid()));
    }
    
    
    /**
     * Event handler.
     * 
     * @param       event 
     */
    @Event.Handler
    public void onUnload(GameAppUnloadedEvent event)
    {
        // TODO: do we need to do anything? saving the data or what?
        update.stop();
        heartbeat.stop();
    }
}
