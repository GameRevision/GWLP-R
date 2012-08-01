/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P053_RequestResponseAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.views.GenericHandlerView;
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
    private GenericHandlerView genericHandlerView;
    
    
    @Override
    protected void init() 
    {
        this.genericHandlerView = new GenericHandlerView(getShardletContext());
        
        LOGGER.debug("generic handler shardlet initialized!");
    }
    
    
    @EventHandler
    public void computerUserHandler(P001_ComputerUserAction action)
    {
        LOGGER.debug("got the computer user packet");
        Session session = action.getSession();

        genericHandlerView.computerInfoReply(session);
    }
    
    
    @EventHandler
    public void requestResponseHandler(P053_RequestResponseAction action)
    {
        LOGGER.debug("got the request response packet");
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        attachment.setLoginCount(action.getLoginCount());
        
        genericHandlerView.sendResponse(session, 0);
    }
}