/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.WorldBean;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.protocol.handshake.HandShakeDoneEvent;
import gwlpr.protocol.handshake.IN1_VerifyClient;
import gwlpr.protocol.intershard.GSNotify_ClientConnected;
import gwlpr.protocol.intershard.GSReply_AcceptClient;
import gwlpr.protocol.intershard.LSRequest_AcceptClient;
import io.netty.channel.Channel;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
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
public class ClientConnect
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ClientConnect.class);
    
    private final java.util.Map<UUID, ClientBean> uninitializedClients = new ConcurrentHashMap<>();
    
    private final Handle<GameAppContext> context;
    private final Handle<GameAppContext> loginShard;
    private final HandleRegistry<ClientBean> clientRegistry;
    
    private final WorldBean world;
    
    
    /**
     * Constructor.
     * 
     * @param       context                 This context
     * @param       loginShard              The parent context, i.e. the login shard.
     * @param       clientRegistry
     * @param       world 
     */
    public ClientConnect(
            Handle<GameAppContext> context, 
            Handle<GameAppContext> loginShard, 
            HandleRegistry<ClientBean> clientRegistry,
            WorldBean world)
    {
        this.context = context;
        this.loginShard = loginShard;
        this.clientRegistry = clientRegistry;
        
        this.world = world;
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
        LOGGER.debug("Got a new client to verify.");
        
        IN1_VerifyClient action = event.getVerifyClient();
        
        // failcheck
        if (action == null) { return; }
        
        LOGGER.debug("{} - {}", (int)action.getKey1(), context.getUid().hashCode());
        
        // check the server key
        if (((int)action.getKey1()) != context.getUid().hashCode()) { return; }
      
        // check if we got the client key
        UUID clientUid = null;
        for (UUID uuid : uninitializedClients.keySet()) 
        {
            LOGGER.debug("{} - {}", (int)action.getKey2(), uuid.hashCode());
            
            if (((int)action.getKey2()) == uuid.hashCode())
            {
                // found the client!
                clientUid = uuid;
                break;
            }
        }
        
        // failcheck
        if (clientUid == null) { return; }
        
        LOGGER.debug("Client accepted.");

        // initialize the client bean first
        ClientBean client = uninitializedClients.remove(clientUid);
        client.init(event.getChannel());
        
        PlayerState state = world.isCharCreate() ? PlayerState.CreatingCharacter : PlayerState.LoadingInstance;
        client.setPlayerState(state);
        
        // sign the client
        Channel channel = client.getChannel();
        channel.attr(GameAppContextKey.KEY).set(context.get());
        channel.attr(GameAppContextKey.IS_SET).set(true);
        
        // register it
        // this will trigger an event from the client registry notification decorator,
        // instructing other controllers to deal with this newly registered client
        Handle<ClientBean> clientHandle = clientRegistry.registerExisting(client, clientUid);
        channel.attr(ClientBean.HANDLE_KEY).set(clientHandle);
        
        // also inform the login shard
        loginShard.get().trigger(
                new GSNotify_ClientConnected(
                    context.getUid(), 
                    clientUid));       
            
        LOGGER.debug("Client connected to the server successfully.");
    }
}