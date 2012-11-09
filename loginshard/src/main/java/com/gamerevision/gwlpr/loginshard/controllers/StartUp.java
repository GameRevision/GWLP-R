/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
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
 * This shardlet is used to initialize the LoginShard.
 * It loads the database connection provider and distributes it, after the
 * game app context has finished loading the shardlets.
 * This shardlet also registers the persistant client verifier, that is used
 * to accept any login clients.
 * 
 * @author miracle444, _rusty
 */
public class StartUp extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StartUp.class);
    private LoginShardStartupEvent startupEvent;
    
    
    /**
     * Initializes this shardlet.
     */
    @Override
    protected void init()
    {
        LOGGER.debug("LoginShard: loading initial data...");
        
        // we simply register a persistant client verifier here, because the login server
        // will accept any client that actually uses its protocol
        ClientVerifier verf = new ClientVerifier() {
            // use an anonymous class here...
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
        // can use the verifier to decide if it should accept or refuse clients.
        getShardletContext().addClientVerifier(verf, true);
        
        // next step is starting up the database connection
        // we can use the init parameter that this shardlet got from the game.xml
        DatabaseConnectionProvider connectionProvider = new DatabaseConnectionProvider(
                this.getInitParameter("dbip"),
                this.getInitParameter("dbport"),
                this.getInitParameter("dbdatabase"),
                this.getInitParameter("dbusername"),
                this.getInitParameter("dbpassword"));
        
        // create the event
        startupEvent = new LoginShardStartupEvent(connectionProvider);
        
        // we'r done for now. when the gameapp created event is triggered by the context, 
        // we can simply use that event we just created
        LOGGER.debug("LoginShard: finished loading initial data");
    }
    
    
    /**
     * This handler is invoked by the container after all shardlets have been
     * initialized.
     * 
     * @param       event                   The event carrying some context information.
     */
    @EventHandler
    public void gameAppCreatedEventHandler(GameAppCreatedEvent event)
    {
        // we just need to distribute the event that we created in the init process
        publishEvent(startupEvent);
    }
}