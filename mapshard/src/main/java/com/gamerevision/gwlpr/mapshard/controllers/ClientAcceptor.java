/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P1280_VerifyClientAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.MapShardStartupEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.entitysystem.Entity;
import com.realityshard.entitysystem.EntitySystemFacade;
import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.ClientVerifier;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TODO: Describe me!
 * 
 * @author miracle444
 */
public class ClientAcceptor extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ClientAcceptor.class);
    private int mapId;
    private EntitySystemFacade entitySystem;
    private ClientLookupTable clientEntityTable;
    
    
    @Override
    protected void init() 
    {
        this.mapId = Integer.parseInt(getShardletContext().getInitParameter("MapId"));
        
        LOGGER.debug("client verifier shardlet initialized!");
    }
    
    
    @EventHandler
    public void acceptSessionRequestActionHandler(ISC_AcceptClientRequestAction action)
    {
        LOGGER.debug("got the accept client request action");
        
        final int key1 = action.getKey1();
        final int key2 = action.getKey2();
        final int accountId = action.getAccountId();
        final String characterName = action.getCharacterName();
        
        
        LOGGER.debug("adding a client verifier");
        
        ClientVerifier verf = new ClientVerifier() {
            @Override
            public boolean check(Action action) 
            {
                // ensure the protocol used matches
                if (!action.getSession().getProtocol().equals("GWGameServerProtocol"))
                {
                    return false;
                }
                
                // check if the first action received is of type VerifyClientAction ...
                if (!(action instanceof P1280_VerifyClientAction))
                {
                    return false;
                }
                
                // ... if it is: cast it to get access to its properties
                P1280_VerifyClientAction thisAction = (P1280_VerifyClientAction)action;

                // compare the security keys
                if (thisAction.getKey1() == key1 && thisAction.getKey2() == key2)
                {
                    // create a new player entity
                    //Entity player = entitySystem.("Player");
                    
                    // and add it to the entity table
                    //clientEntityTable.addClient(action.getSession(), player);
                    
                    // generate this new client's session's attachment
                    action.getSession().setAttachment(new SessionAttachment(accountId, characterName));
                    
                    return true;
                }
                
                return false;
            }
        };
        
        getShardletContext().addClientVerifier(verf, false);
        
        LOGGER.debug("informing the LoginShard");
        
        LoginShardView.sendAction(new ISC_AcceptClientReplyAction(action.getSession(), true, mapId));
    }
    
    
    @EventHandler
    public void mapShardStartupEventHandler(MapShardStartupEvent event)
    {
        this.entitySystem = event.getEntitySystem();
        this.clientEntityTable = event.getClientTable();
    }
}