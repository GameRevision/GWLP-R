/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.events.NetworkClientDisconnectedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
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
        // get the entity and session
        Session session = disc.getSession();
        Entity entity = clientlookup.getBySession(session);
        
        // delete the entity
        entityManager.unregister(entity);
        
        // clean up the client lookup dict
        clientlookup.removeClient(session);
    }
}
