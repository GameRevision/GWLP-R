/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletActionVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet initializes a new session.
 * It verifies the session and intializes the session's attributes.
 * 
 * @author miracle444
 */
public class SessionInitializer extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(SessionInitializer.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("session initializer shardlet initialized!");
        
        // fake always accept verifier
        ShardletActionVerifier verf = new ShardletActionVerifier() {

            @Override
            public boolean check(ShardletAction action) 
            {
                if (action.getSession().getProtocol().equals("LoginServer"))
                action.getSession().setAttribute("SyncCount", (int) 0);
                return true;
            }
        };
        
        getShardletContext().addClientVerifier(verf, true);
    }
}
