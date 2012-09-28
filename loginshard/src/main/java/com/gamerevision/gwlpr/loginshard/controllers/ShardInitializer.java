/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.events.LoginShardStartupEvent;
import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.ClientVerifier;
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
public class ShardInitializer extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ShardInitializer.class);
    
    
    /**
     * Initializes this shardlet
     */
    @Override
    protected void init()
    {
        LOGGER.debug("Shard initializer shardlet initialized!");
        
        // we simply register a persistant client verifier here, because the login server
        // will accept any client that actually uses its protocol
        ClientVerifier verf = new ClientVerifier() {

            @Override
            public boolean check(Action action) 
            {
                if (!action.getSession().getProtocol().equals("GWLoginServerProtocol"))
                {
                    return false;
                }
                
                action.getSession().setAttachment(new SessionAttachment());

                return true;
            }
        };
        
        // after creation we will have to add it to the context, so the context
        // can decide if it should accept or refuse clients.
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
        // lets assemble the global startup message
        
        // create the database stuff
        DatabaseConnectionProvider connectionProvider = new DatabaseConnectionProvider(
                this.getInitParameter("dbip"),
                this.getInitParameter("dbport"),
                this.getInitParameter("dbdatabase"),
                this.getInitParameter("dbusername"),
                this.getInitParameter("dbpassword"));
        
        LoginShardStartupEvent ev = new LoginShardStartupEvent(connectionProvider);
        
        // finally distribute the message!
        publishEvent(ev);
    }
}