/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.isc.ISC_AddClientVerifierAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P041_CharacterPlayInfoAction;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.Session;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet manages the dispatchment of clients to mapshards.
 * 
 * @author miracle444
 */
public class Dispatcher extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("dispatcher shardlet initialized!");
    }
    
    
    @EventHandler
    public void characterPlayInfoHandler(P041_CharacterPlayInfoAction action)
    {
        LOGGER.debug("got the character play info packet");
        Session session = action.getSession();
        
        
        session.setAttribute("SyncCount", action.getUnknown1());
        
        try
        {
            LOGGER.debug("trying to create a map shard");
            RemoteShardletContext mapShard = getShardletContext().tryCreateGameApp("MapShard", new HashMap<String, String>());
            mapShard.sendRemoteEventAction(new ISC_AddClientVerifierAction(session));
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
        }
    }
}
