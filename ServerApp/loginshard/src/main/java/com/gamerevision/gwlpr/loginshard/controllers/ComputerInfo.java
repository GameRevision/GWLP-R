/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import com.gamerevision.gwlpr.loginshard.views.ComputerInfoReplyView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles computer information packets.
 * 
 * @author miracle444
 */
public class ComputerInfo extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ComputerInfo.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("computerinfo shardlet initialized!");
    }
    
    
    @EventHandler
    public void computerUserHandler(P001_ComputerUserAction action)
    {
        LOGGER.debug("got the computer user packet");
        Session session = action.getSession();
        
        
        LOGGER.debug("sending computer info reply");
        sendAction(ComputerInfoReplyView.create(session));
    }
}
