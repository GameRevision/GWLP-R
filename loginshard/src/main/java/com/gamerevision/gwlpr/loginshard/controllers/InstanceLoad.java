/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P010_UnknownAction;
import com.gamerevision.gwlpr.loginshard.views.AccountPermissionsView;
import com.gamerevision.gwlpr.loginshard.views.FriendsListEndView;
import com.gamerevision.gwlpr.loginshard.views.StreamTerminatorView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet manages the dispatchment of clients to mapshards.
 * 
 * @author miracle444
 */
public class InstanceLoad extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(InstanceLoad.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("dispatcher shardlet initialized!");
    }
    
    
    @EventHandler
    public void characterPlayNameHandler(P010_UnknownAction action)
    {
        LOGGER.debug("got the character play info packet");
        Session session = action.getSession();
        
        session.setAttribute("SyncCount", action.getUnknown1());
        
       
        sendAction(FriendsListEndView.create(session));
        
        sendAction(AccountPermissionsView.create(session));
        
        sendAction(StreamTerminatorView.create(session, 0));
        
    }
}
