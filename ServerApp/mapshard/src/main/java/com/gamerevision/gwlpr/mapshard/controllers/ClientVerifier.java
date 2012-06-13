/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.isc.ISC_AddClientVerifierAction;
import com.realityshard.shardlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles client verifier requests.
 * 
 * @author _rusty
 */
public class ClientVerifier extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ClientVerifier.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("client verifier shardlet initialized!");
    }
    
    
    @EventHandler
    public void addClientVerifierHandler(ISC_AddClientVerifierAction action)
    {
        LOGGER.debug("got the add client verifier event");
        final Session session = action.getSession();
        
        LOGGER.debug("adding a client verifier");
        ShardletActionVerifier verf = new ShardletActionVerifier() {
            @Override
            public boolean check(ShardletAction action) 
            {
                if (!action.getSession().getProtocol().equals("GameServer"))
                {
                    return false;
                }
                                
                if (action.getSession().getId() == session.getId())
                {
                    return true;
                }
                
                return false;
            }
        };
        
        getShardletContext().addClientVerifier(verf, false);
    }
}