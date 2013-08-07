/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.protocol.intershard.GSNotify_ClientDisconnected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.NetworkClientDisconnectedEvent;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.util.Handle;


/**
 * This shardlet handles client-disconnections
 * 
 * @author _rusty
 */
public class ClientDisconnect
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDisconnect.class);
    
    private final Handle<GameAppContext> context;
    private final Handle<GameAppContext> loginShard;
    private final EntityManager entityManager;
    
    
    /**
     * Constructor.
     * 
     * @param       context 
     * @param       loginShard 
     * @param       entityManager
     */
    public ClientDisconnect(Handle<GameAppContext> context, Handle<GameAppContext> loginShard, EntityManager entityManager)
    {
        this.context = context;
        this.loginShard = loginShard;
        this.entityManager = entityManager;
    }
    
    
    /**
     * Event handler: If a client disconnected, delete the entity and so on...
     * 
     * @param       event 
     */
    @Event.Handler
    public void onClientDisconnect(NetworkClientDisconnectedEvent event)
    {
        Handle<ClientBean> clientHandle = ClientBean.getHandle(event.getChannel());
        
        // deactivate heart beat and ping and such
        clientHandle.get().setPlayerState(PlayerState.Disconnecting);
        
        // remove the entity
        entityManager.unregister(clientHandle.get().getEntity());
        
        // send a notification to the login shard
        loginShard.get().trigger(
                new GSNotify_ClientDisconnected(
                    context.getUid(),
                    clientHandle.getUid()));
        
        // invalidate the client handler
        clientHandle.invalidate();
        
        // TODO: do we need to close this manually?
        event.getChannel().close();
    }
}
