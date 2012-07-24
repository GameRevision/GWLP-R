/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P053_RequestResponseAction;
import com.gamerevision.gwlpr.loginshard.views.ComputerInfoReplyView;
import com.gamerevision.gwlpr.loginshard.views.SendResponseView;
import com.gamerevision.gwlpr.loginshard.views.StreamTerminatorView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A generic handler should handle all actions that have to be handled but
 * we don't really process the data.
 * 
 * @author _rusty
 */
public class GenericHandler extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(GenericHandler.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("generic handler shardlet initialized!");
    }
    
    
    @EventHandler
    public void computerUserHandler(P001_ComputerUserAction action)
    {
        LOGGER.debug("got the computer user packet");
        Session session = action.getSession();
        
        
        LOGGER.debug("sending computer info reply");
        sendAction(ComputerInfoReplyView.create(session));
    }
    
    
    @EventHandler
    public void requestResponseHandler(P053_RequestResponseAction action)
    {
        LOGGER.debug("got the request response packet");
        Session session = action.getSession();
        
        
        session.setAttribute("SyncCount", action.getLoginCount());
        
        
        LOGGER.debug("sending send response");
        sendAction(SendResponseView.create(session));
        
        LOGGER.debug("sending stream terminator");
        sendAction(StreamTerminatorView.create(session, 0));
    }
}
