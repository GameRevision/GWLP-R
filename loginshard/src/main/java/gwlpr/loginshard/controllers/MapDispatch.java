/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.protocol.intershard.LSRequest_AcceptClient;
import gwlpr.protocol.loginserver.inbound.P041_CharacterPlayInfo;
import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.models.MapDispatchModel;
import gwlpr.loginshard.models.MapShardBean;
import gwlpr.loginshard.models.enums.ErrorCode;
import gwlpr.loginshard.views.MapDispatchView;
import gwlpr.loginshard.views.StreamTerminatorView;
import gwlpr.protocol.intershard.GSNotify_ClientConnected;
import gwlpr.protocol.intershard.GSNotify_WorldEmpty;
import gwlpr.protocol.intershard.GSReply_AcceptClient;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This dispatches our clients to the appropriate mapshards, based on the models
 * decisions.
 * 
 * @author miracle444, _rusty
 */
public class MapDispatch
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(MapDispatch.class);
    
    private final GameAppContext context;
    private final MapDispatchModel model;
    private final HandleRegistry<ClientBean> clientHandleRegistry;
    
    
    /**
     * Constructor.
     * 
     * @param       context
     * @param       clientHandleRegistry  
     */
    public MapDispatch(GameAppContext context, HandleRegistry<ClientBean> clientHandleRegistry)
    {
        this.context = context;
        model = new MapDispatchModel(context);
        this.clientHandleRegistry = clientHandleRegistry;
    }
    
    
    /**
     * Event handler.
     * 
     * @param action 
     */
    @Event.Handler
    public void onCharacterPlayInfo(P041_CharacterPlayInfo action)
    {
        LOGGER.debug("Got the character play info packet");
        
        // get the channel attachment for that channel
        Channel channel = action.getChannel();
        ClientBean client = ClientBean.get(channel);
        Handle<ClientBean> clientHandle = ClientBean.getHandle(channel);
        
        // failcheck
        if (client == null) { channel.close(); return; }
        
        // update the login count
        client.setLoginCount(action.getLoginCount());
        
        // let the model do the work...
        Handle<MapShardBean> mapShardHandle = model.getOrCreate((int)action.getGameMapId());
        
        // lets check if we were successfull with the game app creation
        if (mapShardHandle == null)
        {
            LOGGER.warn("Was unable to create a game app! Please check the log for other errors!");
            StreamTerminatorView.send(channel, ErrorCode.InternalServerError);
            return;
        }
        
        // inform the model that we'r waiting for a mapshard to accept a client
        model.clientWaitingFor(clientHandle, mapShardHandle);
        
        // dont forget to update the client bean (it now has a mapshard, yay :)
        client.setMapShardHandle(mapShardHandle);
        
        LOGGER.debug("Asking a map shard to accept a client.");
        
        mapShardHandle.get().getMapShardContext().sendRemoteEvent(
                new LSRequest_AcceptClient(
                    clientHandle.getUid(),
                    client.getAccount(), 
                    client.getCharacter()));

        // we will notify the client when the mapshard replies.
    }
    
    
    /**
     * Event handler.
     * Triggered when the map shard accepts a client.
     * 
     * @param event 
     */
    @Event.Handler
    public void onAcceptClientReply(GSReply_AcceptClient event)
    {
        Handle<ClientBean> clientHandle = clientHandleRegistry.getHandle(event.getClientUid());
            
        // failcheck
        if (clientHandle == null) { return; }

        Handle<MapShardBean> mapShardHandle = clientHandle.get().getMapShardHandle();

        // failcheck
        if (mapShardHandle == null || !mapShardHandle.getUid().equals(event.getServerUid())) { return; }
        
        // check if the session has been accepted
        if (event.isAccepted())
        {
            model.clientGotAcceptedBy(clientHandle, mapShardHandle, true);
            
            // retrieve socket address for that game server
            InetSocketAddress address = context.getManager().localAddressFor(mapShardHandle.get().getMapShardContext());
            
            // failcheck
            if (address == null) { return; }
            
            LOGGER.debug("The map shard accepted the client.");

            MapDispatchView.referToGameServer(
                    clientHandle.get().getChannel(), 
                    address,
                    event.getServerUid(), 
                    event.getClientUid(), 
                    mapShardHandle.get().getMap().getGameID());
            
            return;            
        }
        
        // this is called when the mapshard didnt accept the client
        model.clientGotAcceptedBy(clientHandle, mapShardHandle, false);

        LOGGER.warn("The map shard did not accept the session!");
        
        StreamTerminatorView.send(clientHandle.get().getChannel(), ErrorCode.InternalServerError);
    }
    
    
    /**
     * Event handler.
     * Triggered when a map shard established the actual connection to the client
     * 
     * @param event 
     */
    @Event.Handler
    public void onNotifyClientConnected(GSNotify_ClientConnected event)
    {
        Handle<ClientBean> clientHandle = clientHandleRegistry.getHandle(event.getClientUid());
            
        // failcheck
        if (clientHandle == null) { return; }

        Handle<MapShardBean> mapShardHandle = clientHandle.get().getMapShardHandle();

        // failcheck
        if (mapShardHandle == null) { return; }
        
        model.dispatchDone(clientHandle, mapShardHandle);
    }
    
    
    /**
     * Event handler.
     * Triggered when a map shard runs out of clients. We need to decide whether
     * it should terminate in that case.
     * 
     * @param       event 
     */
    @Event.Handler
    public void onEmptyMapShard(GSNotify_WorldEmpty event)
    {
        // default behaviour: do nothing
        
//        RemoteShardletContext mapShard = null;
//        
//        // lets see if we got that map shard:
//        if (mapShards.containsKey(action.getMapId()))
//        {
//            mapShard = mapShards.get(action.getMapId());
//            
//            mapShard.sendTriggerableAction(new ISC_ShutdownMapshardRequestAction());
//            
//            mapShards.remove(action.getMapId());
//        }
    }
}
