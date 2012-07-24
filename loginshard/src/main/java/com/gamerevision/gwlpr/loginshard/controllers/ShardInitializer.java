/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.events.DatabaseConnectionProviderEvent;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletActionVerifier;
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
        
        ShardletActionVerifier verf = new ShardletActionVerifier() {

            @Override
            public boolean check(ShardletAction action) 
            {
                if (!action.getSession().getProtocol().equals("GWLoginServerProtocol"))
                {
                    return false;
                }
                
                action.getSession().setAttribute("SyncCount", (int) 0);
                
                return true;
            }
        };
        
        getShardletContext().addClientVerifier(verf, true);
    }
    
    
    /**
     * This handler is invoked by the api when the gameapp is created.
     * 
     * @param   event  the event carrying the context information.
     */
    @EventHandler
    public void gameAppCreatedEventHandler(GameAppCreatedEvent event)
    {
        DatabaseConnectionProvider connectionProvider = new DatabaseConnectionProvider(this.getInitParameter("dbip"),
                                                                            this.getInitParameter("dbport"),
                                                                            this.getInitParameter("dbdatabase"),
                                                                            this.getInitParameter("dbusername"),
                                                                            this.getInitParameter("dbpassword"));
                
        publishEvent(new DatabaseConnectionProviderEvent(connectionProvider));
    }
}