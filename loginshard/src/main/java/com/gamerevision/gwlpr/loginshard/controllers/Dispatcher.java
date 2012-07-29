/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientReplyAction;
import com.gamerevision.gwlpr.actions.intershardcom.ISC_AcceptClientRequestAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P041_CharacterPlayInfoAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.views.ReferToGameServerView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import java.util.HashMap;
import java.util.Map;
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
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("dispatcher shardlet initialized!");
    }
    
    
    @EventHandler
    public void characterPlayInfoHandler(P041_CharacterPlayInfoAction action)
    {
        LOGGER.debug("got the character play info packet");
        Session session = action.getSession();
        
        
        ((SessionAttachment) session.getAttachment()).setLoginCount(action.getLoginCount());
        
        
        int mapId = action.getGameMapId();
        HashMap<String,String> params = new HashMap<>();
        params.put("MapId", String.valueOf(mapId));
        
        try
        {
            LOGGER.debug("trying to create a map shard");
            ShardletContext mapShard = getShardletContext().tryCreateGameApp("MapShard", params);
            
            // TODO: check for null value of mapShard (meaning there was no MapShard generated).
            
            mapShard.sendRemoteEventAction(new ISC_AcceptClientRequestAction(session, 1, 2));
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.toString());
        }
    }
    
    
    @EventHandler
    public void acceptSessionReplyActionHandler(ISC_AcceptClientReplyAction action)
    {
        LOGGER.debug("got the accept session reply action");
        Session session = action.getSession();
        
        if (action.getAccepted())
        {
            LOGGER.debug("the MapShard accepted the session");
            sendAction(ReferToGameServerView.create(session));
        }
        else
        {
            LOGGER.debug("the MapShard did not accept the session");
            // TODO: Implement what has to be done if it did not accept the session.
        }
    }
}
