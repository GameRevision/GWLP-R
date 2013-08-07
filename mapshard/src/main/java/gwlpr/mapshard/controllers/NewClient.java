/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.database.entities.Map;
import gwlpr.database.entities.Spawnpoint;
import gwlpr.mapshard.entitysystem.Components;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.entityfactories.CharacterFactory;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.views.CharacterCreationView;
import gwlpr.mapshard.views.HandshakeView;
import gwlpr.protocol.handshake.HandShakeDoneEvent;
import gwlpr.protocol.handshake.IN1_VerifyClient;
import gwlpr.protocol.handshake.IN2_ClientSeed;
import gwlpr.protocol.intershard.GSReply_AcceptClient;
import gwlpr.protocol.intershard.LSRequest_AcceptClient;
import io.netty.channel.Channel;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.NetworkClientConnectedEvent;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.network.GameAppContextKey;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This handles all incoming client connections.
 * We will get instructed by the parent login shard to actually accept certain
 * clients. (To link them with this mapshard)
 * 
 * @author _rusty
 */
public class NewClient
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(NewClient.class);
    
    private final java.util.Map<UUID, ClientBean> uninitializedClients = new ConcurrentHashMap<>();
    
    private final Handle<GameAppContext> context;
    private final Handle<GameAppContext> loginShard;
    private final HandleRegistry<ClientBean> clientRegistry;
    
    private final Map mapEntity;
    private final EntityManager entityManager;
    private final boolean isCharCreate;
    
    
    /**
     * Constructor.
     * 
     * @param       context                 This context
     * @param       loginShard              The parent context, i.e. the login shard.
     * @param       clientRegistry
     * @param       mapEntity               The map DAO
     * @param       entityManager 
     * @param       isCharCreate            True if this shard is used for char creation.
     */
    public NewClient(
            Handle<GameAppContext> context, 
            Handle<GameAppContext> loginShard, 
            HandleRegistry<ClientBean> clientRegistry,
            Map mapEntity,
            EntityManager entityManager, 
            boolean isCharCreate)
    {
        this.context = context;
        this.loginShard = loginShard;
        this.clientRegistry = clientRegistry;
        
        this.mapEntity = mapEntity;
        this.entityManager = entityManager;
        this.isCharCreate = isCharCreate;
    }
    
    
    /**
     * Intershard communication event handler.
     * 
     * TODO: check if we already got too much clients and such...
     * 
     * @param event 
     */
    @Event.Handler
    public void onAcceptClientRequest(LSRequest_AcceptClient event)
    {
        // create a new uninitialized client bean...
        ClientBean client = new ClientBean(event.getAccount(), event.getCharacter());
        
        // and add it to our controller internal mapping...
        uninitializedClients.put(event.getClientUid(), client);
        
        // and tell the LS that we're ready
        loginShard.get().trigger(
                new GSReply_AcceptClient(
                    context.getUid(),
                    event.getClientUid(),
                    true));
    }
    
    
    /**
     * Event handler.
     * 
     * @param       event 
     */
    @Event.Handler
    public void onHandshakeDone(HandShakeDoneEvent event)
    {
        IN1_VerifyClient action = event.getVerifyClient();
        
        // failcheck
        if (action == null) { return; }
        
        // check the server key
        if (((int)action.getKey1()) != context.getUid().hashCode()) { return; }
        
        // check if we got the client key
        UUID clientUid = null;
        for (UUID uuid : uninitializedClients.keySet()) 
        {
            if (((int)action.getKey2()) == uuid.hashCode())
            {
                // found the client!
                clientUid = uuid;
                break;
            }
        }
        
        // failcheck
        if (clientUid == null) { return; }

        // initialize the client bean first
        ClientBean client = uninitializedClients.remove(clientUid);
        client.init(action.getChannel());
        client.setPlayerState(PlayerState.LoadingInstance);
        
        // register it
        Handle<ClientBean> clientHandle = clientRegistry.registerExisting(client, clientUid);
        
        // sign the client
        Channel channel = action.getChannel();
        channel.attr(GameAppContextKey.KEY).set(context.get());
        channel.attr(GameAppContextKey.IS_SET).set(true);
        channel.attr(ClientBean.HANDLE_KEY).set(clientHandle);
        
        
        // check if this is a character creation mapshard
        if (isCharCreate)
        {
            LOGGER.debug("Starting character creation");
            CharacterCreationView.charCreateHead(channel);
            return;
        }
        
        // pick a random spawnpoint
        Iterator<Spawnpoint> it = mapEntity.getSpawnpointCollection().iterator();
        for (int i = 0; i < ((int)Math.random()*mapEntity.getSpawnpointCollection().size() -1); i++) {
            it.next();
        }
        Spawnpoint spawn = it.next();
        int radius = spawn.getRadius();
        
        // pick a random position in a certain radius
        float t = (float)(2 * Math.PI * Math.random());
        float r = (float)(radius * -1 + (Math.random() * radius));
        WorldPosition pos = new WorldPosition(
                (float)(spawn.getX() + (r * Math.cos(t))), 
                (float)(spawn.getY() + (r * Math.sin(t))), 
                spawn.getPlane());
        
        // create a new entity of this
        Entity player = CharacterFactory.createCharacter(clientUid, client.getCharacter(), pos, entityManager);

        client.setEntity(player);
        client.setAgentIDs(player.get(Components.AgentIdentifiers.class));
        
        // using the attachment now looks a bit ugly,
        // because we use the components here directly
        // (this should not happen with other components!)
        HandshakeView.charName(session, client.name);
        HandshakeView.districtInfo(session, attach.getAgentIDs().localID, mapData.getMapID());
    }
}