/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P16896_ClientSeedAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.models.MapClientVerifier;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreationView;
import com.gamerevision.gwlpr.mapshard.views.HandshakeView;
import com.realityshard.entitysystem.EntitySystemFacade;
import com.realityshard.shardlet.ClientVerifier;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the handshake process for GW clients.
 * It establishes an encrypted session.
 * 
 * @author miracle444, _rusty
 */
public class Handshake extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Handshake.class);
    
    private RemoteShardletContext loginShard;
    private DatabaseConnectionProvider db;
    private EntitySystemFacade entitySystem;
    private ClientLookupTable clientlookup;
    private int mapId;
     
    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init Handshake controller");
        // note that this shardlet relies on content that is loaded by the
        // StartUp controller.
        // see the onStartUp event handler for more initialization
    }
    
    
    /**
     * Executes startup features, like storing database references etc.
     * 
     * @param event 
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        // this event indicates that all shardlets have been loaded (including
        // the startup shardlet) so we can safely use the context attachment now.
        
        // this is quite verbose, but luckily we only have to query for the 
        // necessary references once
        ContextAttachment attach = ((ContextAttachment) getShardletContext().getAttachment());
        
        loginShard = attach.getLoginShard();
        db = attach.getDatabaseProvider();
        entitySystem = attach.getEntitySystem();
        clientlookup = attach.getClientLookup();
        mapId = attach.getMapId();
    }
    
    
    /**
     * Event handler.
     * 
     * Handshake step:
     * We got a AcceptSession request. This means that there will be a new client
     * wanting to connect to our map shard soon.
     * We need to add a client verifier for that client, so we know when it tries
     * to establish the connection.
     * 
     * @param action 
     */
    @EventHandler
    public void onAcceptSession(ISC_AcceptClientRequestAction action)
    {
        LOGGER.debug("Got the request to accept a new client");
        
        // create the verifier with all necessary info
        ClientVerifier verf = new MapClientVerifier(
                action.getAccountId(),
                action.getCharacterId(),
                action.getKey1(),
                action.getKey2());
        
        // add it to the context
        getShardletContext().addClientVerifier(verf, false);
        
        // finally, reply to the action from the login shard
        loginShard.sendTriggerableAction(new ISC_AcceptClientReplyAction(
                action.getSession(),
                action.getAccountId(),
                mapId,
                true));
    }
    
    
    /**
     * Event handler.
     * 
     * Handshake step:
     * This is the actual interaction with the client.
     * 
     * @param action 
     */
    @EventHandler
    public void onClientSeed(P16896_ClientSeedAction action)
    {
        LOGGER.debug("Got the client seed packet");
        
        // get the session for this client
        Session session = action.getSession();
        SessionAttachment attach = ((SessionAttachment) session.getAttachment());
        
        // send the usual handshake stuff
        sendAction(HandshakeView.serverSeed(session));
        sendAction(HandshakeView.instanceHead(session));
        
        // check if this is a character creation mapshard
        if (mapId == 0)
        {
            LOGGER.debug("Starting character creation");
            sendAction(CharacterCreationView.charCreateHead(session));
            return;
        }
        
        // lets load that char name if it already exists...
        String charName = DBCharacter.getCharacter(db, attach.getAccountId()).getName();
        
        // as we are about to use that name for other stuff, lets 
        // update the session attachment prior to that
        attach.setCharacterName(charName);
        
        sendAction(HandshakeView.charName(session, charName));
        sendAction(HandshakeView.districtInfo(session, mapId));
        
    }
}
