/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P1280_VerifyClientAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletActionVerifier;
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
    public void acceptSessionRequestActionHandler(ISC_AcceptClientRequestAction action)
    {
        LOGGER.debug("got the accept client request action");
        final int key1 = action.getKey1();
        final int key2 = action.getKey2();
        
        LOGGER.debug("adding a client verifier");
        ShardletActionVerifier verf = new ShardletActionVerifier() {
            @Override
            public boolean check(ShardletAction action) 
            {
                if (!action.getSession().getProtocol().equals("GWGameServerProtocol"))
                {
                    return false;
                }
                
                if (!(action instanceof P1280_VerifyClientAction))
                {
                    return false;
                }
                
                P1280_VerifyClientAction thisAction = (P1280_VerifyClientAction)action;

                if (thisAction.getKey1() == key1 && thisAction.getKey2() == key2)
                {
                    return true;
                }
                
                return false;
            }
        };
        
        getShardletContext().addClientVerifier(verf, false);
        
        LOGGER.debug("informing the LoginShard");
        LoginShardView.sendAction(new ISC_AcceptClientReplyAction(action.getSession(), true));
    }
}