/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.framework.entitysystem.EntitySystemFacade;
import com.gamerevision.gwlpr.mapshard.events.MapShardStartupEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the MapShard.
 * 
 * @author miracle444, _rusty
 */
public class ShardInitializer extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ShardInitializer.class);
    
    
    /**
     * Initialize this shardlet.
     * Because we will be running the whole startup process after all shardlets finished loading,
     * we wont be doing much in here.
     */
    @Override
    protected void init()
    {
        LOGGER.debug("Shard initializer shardlet initialized! [mapid = {}]", 
                this.getShardletContext().getInitParameter("MapId"));
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
        EntitySystemFacade entitySystem = new EntitySystemFacade(getShardletContext().getExecutor());
        
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