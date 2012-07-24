/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.events.DatabaseConnectionProviderEvent;
import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the MapShard.
 * 
 * @author miracle444
 */
public class ShardInitializer extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ShardInitializer.class);
    
    
    @Override
    protected void init()
    {
        LOGGER.debug("shard initializer shardlet initialized!");
    }
    
    
    /**
     * This handler is invoked by the api when the gameapp is created.
     * 
     * @param   event  the event carrying the context information.
     */
    @EventHandler
    public void gameAppCreatedEventHandler(GameAppCreatedEvent event)
    {
        LoginShardView.SetLoginShardContext(event.getParent());
        
        DatabaseConnectionProvider connectionProvider = new DatabaseConnectionProvider(this.getInitParameter("dbip"),
                                                                            this.getInitParameter("dbport"),
                                                                            this.getInitParameter("dbdatabase"),
                                                                            this.getInitParameter("dbusername"),
                                                                            this.getInitParameter("dbpassword"));
                
        publishEvent(new DatabaseConnectionProviderEvent(connectionProvider));
    }
}