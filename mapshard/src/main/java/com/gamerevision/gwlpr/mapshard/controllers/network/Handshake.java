/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers.network;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P16896_ClientSeedAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.models.MapClientVerifier;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreationView;
import com.gamerevision.gwlpr.mapshard.views.HandshakeView;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.builders.PlayerBuilder;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.LoadCharacter;
import com.gamerevision.gwlpr.mapshard.models.MapData;
import com.realityshard.shardlet.ClientVerifier;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    private EntityManager entityManager;
    private ClientLookupTable clientlookup;
    private MapData mapData;
     
    
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
        entityManager = attach.getEntitySystem();
        clientlookup = attach.getClientLookup();
        mapData = attach.getMapData();
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
                getShardletContext().getHostAddress(),
                9112, // TODO BUG where do i get the port from?
                action.getAccountId(),
                mapData.getMapID(),
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
        HandshakeView.serverSeed(session);
        HandshakeView.instanceHead(session);
        
        // check if this is a character creation mapshard
        if (mapData.getMapID() == 0)
        {
            LOGGER.debug("Starting character creation");
            CharacterCreationView.charCreateHead(session);
            return;
        }
        
        // this is where we query for all the data of this char that we have
        // to create a new entity.
        
        // remember: better have no business logic in the controllers!
        LoadCharacter loader = new LoadCharacter(db, attach.getCharacterId(), mapData.getSpawn());
        
        Entity player = loader.createPlayerEntityFor(entityManager);
        
        // as we are about to use the name for other stuff as well,
        // we will add that to the session attachment too
        attach.setEntity(player);
        // these are the only concrete component refernces we keep!
        attach.setCharacterName(player.get(Name.class));
        attach.setAgentID(player.get(AgentID.class));
        attach.setLocalID(player.get(LocalID.class));
        
        // using the attachment now looks a bit ugly,
        // because we use the components here directly
        // (this should not happen with other components!)
        HandshakeView.charName(session, attach.getCharacterName().name);
        HandshakeView.districtInfo(session, attach.getLocalID().localID, mapData.getMapID());
    }
}
