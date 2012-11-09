/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.events.MapShardStartupEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.entitysystem.EntitySystemFacade;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the MapShard.
 * 
 * @author miracle444, _rusty
 */
public class StartUp extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StartUp.class);
    
    
    /**
     * Initialize this shardlet.
     * We will be running the whole startup process right now, right here.
     */
    @Override
    protected void init()
    {
        // we will need the mapId to determine which map to load later on.
        int mapId = this.getShardletContext().getInitParameter("MapId");

        
        LoginShardView.SetLoginShardContext(event.getParent());

        LOGGER.debug("MapShard: init Startup controller [mapid = {}]", mapId);

        // load the database stuff
    }
    
    
    /**
     * This handler will be triggered by the R:S server when the game-app has been
     * initialized completely.
     * 
     * @param       event                   The event carrying the context information.
     */
    @EventHandler
    public void gameAppCreatedEventHandler(GameAppCreatedEvent event)
    {
        // do the necessary data handling first
        
        // check out who actually created this map shard,
        // because it won't be auto-loaded
        LoginShardView.SetLoginShardContext(event.getParent());
        
        // then lets assemble the global startup message
        
        // create the database stuff
        DatabaseConnectionProvider connectionProvider = new DatabaseConnectionProvider(
                this.getInitParameter("dbip"),
                this.getInitParameter("dbport"),
                this.getInitParameter("dbdatabase"),
                this.getInitParameter("dbusername"),
                this.getInitParameter("dbpassword"));

        // create the entity system
        EntitySystemFacade entitySystem = new EntitySystemFacade();
        
        // create the client lookup table
        ClientLookupTable lookupTable = new ClientLookupTable();
        
        MapShardStartupEvent ev = new MapShardStartupEvent(
                connectionProvider,
                entitySystem,
                lookupTable);
        
        // finally distribute the message!
        publishEvent(ev);
    }
}