/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P1024_ClientVersionAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P16896_ClientSeedAction;
import com.gamerevision.gwlpr.loginshard.model.logic.HandshakeModel;
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
public class HandshakeController extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(HandshakeController.class);
    private HandshakeModel handshakeModel;
    
    @Override
    protected void init() 
    {
        handshakeModel = new HandshakeModel(this.getShardletContext());
        
        LOGGER.debug("handshake shardlet initialized!");
    }
    
    
    @EventHandler
    public void clientVersionHandler(P1024_ClientVersionAction action)
    {
        int clientVersion = action.getUnknown2();
        
        LOGGER.debug("got the client version packet");
        
        Session session = action.getSession();
        
        handshakeModel.setClientVersion(session, clientVersion);
        
    }
    
    
    @EventHandler
    public void clientSeedHandler(P16896_ClientSeedAction action)
    {
        byte[] clientSeed = action.getClientSeed();
        
        LOGGER.debug("got the client seed packet");
        
        Session session = action.getSession();
        
        handshakeModel.setClientSeed(session, clientSeed);
    }
}
