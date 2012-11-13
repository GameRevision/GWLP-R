/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P053_RequestResponseAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.views.StaticReplyView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles all the actions of which we don't really process the data.
 * TODO: Check in case of strange errors, check if this shardlet is the cause.
 * 
 * @author miracle444, _rusty
 */
public class StaticReply extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StaticReply.class);
    private StaticReplyView genericHandlerView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        this.genericHandlerView = new StaticReplyView(getShardletContext());
        
        LOGGER.debug("LoginShard: init StaticReply controller");
    }
    
    
    /**
     * Event handler.
     * 
     * @param action 
     */
    @EventHandler
    public void onComputerUser(P001_ComputerUserAction action)
    {
        LOGGER.debug("LoginShard: got a computer user packet");
        
        Session session = action.getSession();

        genericHandlerView.computerInfoReply(session);
    }
    
    
    /**
     * Event handler.
     * 
     * @param action 
     */
    @EventHandler
    public void onRequestResponse(P053_RequestResponseAction action)
    {
        LOGGER.debug("LoginShard: got the request response packet");
        
        Session session = action.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        // note that this actually influences the login count of the session...
        attach.setLoginCount(action.getLoginCount());
        
        genericHandlerView.sendResponse(session, 0);
    }
}