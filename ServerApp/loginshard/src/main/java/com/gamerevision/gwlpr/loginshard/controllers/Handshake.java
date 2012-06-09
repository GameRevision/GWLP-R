/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P1024_ClientVersionAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P16896_ClientSeedAction;
import com.gamerevision.gwlpr.loginshard.views.ServerSeedView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the handshake process for GW clients.
 * It establishes an encrypted session.
 * 
 * @author _rusty
 */
public class Handshake extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Handshake.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("handshake shardlet initialized!");
    }
    
    
    @EventHandler
    public void clientVersionHandler(P1024_ClientVersionAction action)
    {
        LOGGER.debug("got the client version packet");
    }
    
    
    @EventHandler
    public void clientSeedHandler(P16896_ClientSeedAction action)
    {
        LOGGER.debug("got the client seed packet");
        Session session = action.getSession();
        
        
        LOGGER.debug("sending server seed");
        sendAction(ServerSeedView.create(session));
    }
}
