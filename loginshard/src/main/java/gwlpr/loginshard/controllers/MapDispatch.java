/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.database.entities.Map;
import gwlpr.protocol.intershard.LSRequest_AcceptClient;
import gwlpr.protocol.loginserver.inbound.P041_CharacterPlayInfo;
import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.models.MapDispatchModel;
import gwlpr.loginshard.models.enums.ErrorCode;
import gwlpr.loginshard.views.MapDispatchView;
import gwlpr.loginshard.views.StreamTerminatorView;
import gwlpr.protocol.intershard.GSNotify_ClientConnected;
import gwlpr.protocol.intershard.GSNotify_WorldEmpty;
import gwlpr.protocol.intershard.GSReply_AcceptClient;
import gwlpr.protocol.intershard.utils.DistrictLanguage;
import gwlpr.protocol.intershard.utils.DistrictRegion;
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
    public MapDispatch(Handle<GameAppContext> context, HandleRegistry<ClientBean> clientHandleRegistry)
    {
        this.context = context.get();
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

        // TODO: check mapId before directly creating a mapshard for it!
        // TODO: check the following values!
        // we ignore the instance number because the server needs to chose that...
        int mapId = (int)action.getGameMapId();
        int instanceNum = (int)action.getInstanceNumber();
        DistrictRegion region = DistrictRegion.values()[(int)action.getRegion()];
        DistrictLanguage language = DistrictLanguage.values()[(int)action.getLanguage()];
        
        // TODO: enable outposts
        Handle<GameAppContext> mapShardHandle = model.getOrCreateExplorable(mapId);
            // let the model do the work...
            //Handle<GameAppContext> mapShardHandle = model.getOrCreate(mapId, region, language, instanceNum, true);

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

        mapShardHandle.get().trigger(
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

        Handle<GameAppContext> mapShardHandle = clientHandle.get().getMapShardHandle();

        // failcheck
        if (mapShardHandle == null || !mapShardHandle.getUid().equals(event.getServerUid())) { return; }

        // this is called when the mapshard didnt accept the client
        model.clientGotAcceptedBy(clientHandle, mapShardHandle, event.isAccepted());

        // check if the session has been accepted
        if (!event.isAccepted())
        {
            LOGGER.warn("The map shard did not accept the session!");

            StreamTerminatorView.send(clientHandle.get().getChannel(), ErrorCode.InternalServerError);
        }

        // retrieve socket address for that game server
        InetSocketAddress address = context.getManager().localAddressFor(mapShardHandle);
        Map map = model.getBean(mapShardHandle).getMap();

        // failcheck
        if (address == null || map == null) { return; }

        LOGGER.debug("The map shard accepted the client.");

        MapDispatchView.referToGameServer(
                clientHandle.get().getChannel(),
                address,
                event.getServerUid(),
                event.getClientUid(),
                map.getGameID());
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

        Handle<GameAppContext> mapShardHandle = clientHandle.get().getMapShardHandle();

        // failcheck
        if (mapShardHandle == null || !mapShardHandle.getUid().equals(event.getServerUid())) { return; }

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
