/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.ContextAttachment;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.ClientVerifier;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the LoginShard.
 * It loads the database connection provider and creates the context's attachment.
 * This shardlet also registers the persistant client verifier, that is used
 * to accept any login clients.
 * 
 * @author miracle444, _rusty
 */
public class StartUp extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StartUp.class);
    
    
    /**
     * Initializes this shardlet.
     * We will be running the whole startup process right now, right here.
     * (There is no need for event handlers and the like)
     */
    @Override
    protected void init()
    {
        LOGGER.info("LoginShard: init StartUp controller.");
        
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
        DatabaseConnectionProvider db = new DatabaseConnectionProvider(
                this.getInitParameter("dbip"),
                this.getInitParameter("dbport"),
                this.getInitParameter("dbdatabase"),
                this.getInitParameter("dbusername"),
                this.getInitParameter("dbpassword"));
        
        // finally, create the context attachment
        getShardletContext().setAttachment(new ContextAttachment(db));
        
        // we'r done for now
        LOGGER.debug("Finished loading initial data");
    }
}