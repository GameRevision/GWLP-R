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
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet manages the dispatchment of clients to mapshards.
 * 
 * @author miracle444
 */
public class Dispatcher extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);
    private DispatcherView dispatcherView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        this.dispatcherView = new DispatcherView(getShardletContext());
        
        LOGGER.debug("Dispatcher shardlet initialized!");
    }
    
    
    /**
     * Event handler.
     * This handler will manage play info packets.
     * 
     * @param action 
     */
    @EventHandler
    public void characterPlayInfoHandler(P041_CharacterPlayInfoAction action)
    {
        LOGGER.debug("Got the character play info packet");
        
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        attachment.setLoginCount(action.getLoginCount());
        
        
        int mapId = action.getGameMapId();
        mapId = (mapId == 0) ? mapId : 248;
        HashMap<String,String> params = new HashMap<>();
        params.put("MapId", String.valueOf(mapId));
        
        int accountId = attachment.getAccountId();
        String characterName = attachment.getCharacterName();
        
        try
        {
            LOGGER.debug("Trying to create a map shard");
            ShardletContext mapShard = getShardletContext().tryCreateGameApp("MapShard", params);
            
            // TODO: check for null value of mapShard (meaning there was no MapShard generated).
            
            mapShard.sendRemoteEventAction(new ISC_AcceptClientRequestAction(session, 1, 2, accountId, characterName));
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
        }
    }
    
    
    /**
     * Event handler.
     * This handler will manage session reply messages, used for inter-shard-communication
     * 
     * @param action 
     */
    @EventHandler
    public void acceptClientReplyHandler(ISC_AcceptClientReplyAction action)
    {
        LOGGER.debug("Got the accept session reply action");
        Session session = action.getSession();
        
        if (action.getAccepted())
        {
            LOGGER.debug("The MapShard accepted the session");
            
            dispatcherView.referToGameServer(session, 1, 2, action.getMapId());
        }
        else
        {
            LOGGER.debug("The MapShard did not accept the session");
            // TODO: Implement what has to be done if it did not accept the session.
        }
    }
}
