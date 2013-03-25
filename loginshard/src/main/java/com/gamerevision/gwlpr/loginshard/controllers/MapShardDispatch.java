/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P041_CharacterPlayInfoAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.views.DispatcherView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericShardlet;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet dispatches our clients to the appropriate mapshards.
 * 
 * @author miracle444, _rusty
 */
public class MapShardDispatch extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(MapShardDispatch.class);
    
    private final Map<Integer, RemoteShardletContext> mapShards;
    private DispatcherView dispatcherView;
    
    
    /**
     * Constructor.
     */
    public MapShardDispatch()
    {
        mapShards = new HashMap<>();
    }
    
    
    /**
     * Initialize this shardlet.
     */
    @Override
    protected void init() 
    {
        this.dispatcherView = new DispatcherView(getShardletContext());
        
        LOGGER.info("LoginShard: init Dispatch controller");
    }
    
    
    /**
     * Event handler.
     * 
     * @param action 
     */
    @EventHandler
    public void onCharacterPlayInfo(P041_CharacterPlayInfoAction action)
    {
        LOGGER.debug("Got the character play info packet");
        
        Session session = action.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        // this process updates the login count
        attach.setLoginCount(action.getLoginCount());
        
        // lets set up the map shard now (if we do not already have one that
        // handles that instance
        // ... this is probably a TODO!
        
        // TODO: BUG THIS SHOULD NOT BE TAKEN FROM THIS PACKET DIRECTLY,
        // AS IT CAN BE ABUSED!!
        int mapId = action.getGameMapId();
        
        HashMap<String,String> params = new HashMap<>();
        params.put("MapId", String.valueOf(mapId));
        
        
        RemoteShardletContext mapShard = null;
        
        // lets see if we already got that map shard:
        if (mapShards.containsKey(mapId))
        {
            mapShard = mapShards.get(mapId);
        }
        else
        {
            // else create a new one
            try
            {
                LOGGER.debug("Trying to create a map shard");

                mapShard = getShardletContext().tryCreateGameApp("MapShard", params);
                mapShards.put(mapId, mapShard);
            }
            catch (Exception ex)
            {
                LOGGER.error("Could not create map shard.", ex);
            }
        }
        
        // lets check if we were successfull with the game app creation
        if (mapShard == null)
        {
            LOGGER.warn("Was unable to create a game app! Please check the log for other errors!");
            return;
        }
        
        // now lets start communication with that map shard
        int accId = attach.getAccountId();
        int charId = attach.getCharacterId();
        
        mapShard.sendTriggerableAction(new ISC_AcceptClientRequestAction(session, 1, 2, accId, charId));
        
        // note that our second method will be invoked when the map shard replies.
    }
    
    
    /**
     * Event handler.
     * This handler will manage session reply messages, used for inter-shard-communication
     * 
     * @param action 
     */
    @EventHandler
    public void onAcceptClientReply(ISC_AcceptClientReplyAction action)
    {
        LOGGER.debug("Got the accept session reply action");
        Session session = action.getSession();
        
        if (!action.acceptedSession())
        {
            LOGGER.warn("The MapShard did not accept the session!");
            // TODO: search for another mapshard or whatever here
            return;
        }
        
        LOGGER.debug("The MapShard accepted the session");

        dispatcherView.referToGameServer(
                session, 
                action.getIp(),
                action.getPort(), 
                1, 
                2, 
                action.getMapId());
    }
}
