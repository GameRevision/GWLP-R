/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.ContextAttachment;
import gwlpr.mapshard.SessionAttachment;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.models.ClientLookupTable;
import gwlpr.mapshard.models.enums.PlayerState;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.Session;
import realityshard.shardlet.events.GameAppCreatedEvent;
import realityshard.shardlet.events.NetworkClientDisconnectedEvent;
import realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles client-disconnections
 * 
 * @author _rusty
 */
public class Disconnect extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Handshake.class);
    
    private EntityManager entityManager;
    private ClientLookupTable clientlookup;
     
    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init Disconnect controller");
        // note that this shardlet relies on content that is loaded by the
        // StartUp controller.
        // see the onStartUp event handler for more initialization
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
        
        entityManager = attach.getEntitySystem();
        clientlookup = attach.getClientLookup();
    }
    
    
    /**
     * Event handler: If a client disconnected, delete the entity and so on...
     * 
     * @param disc 
     */
    @EventHandler
    public void onClientDisconnect(NetworkClientDisconnectedEvent disc)
    {
        Session session = disc.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        
        // deactivate heart beat and ping and such
        attachment.setPlayerState(PlayerState.Playing);
        
        // remove the entity
        Entity entity = clientlookup.getBySession(session);
        entityManager.unregister(entity);
        
        // clean up the client lookup dict
        clientlookup.removeClient(session);
        
        // invalidate the session
        session.invalidate();
    }
}
