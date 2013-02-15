/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P093_UnknownAction;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.ChatMessageEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet controls incoming chat messages (it distributes them, or
 * forwards server commands)
 * 
 * @author _rusty
 */
public class Chat extends GenericShardlet
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Chat.class);

    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init Chat controller");
    }
    
    
    /**
     * Event handler for incoming chat messages
     * 
     * @param chatMsg 
     */
    @EventHandler
    public void onChatMessage(P093_UnknownAction chatMsg)
    {
        SessionAttachment attach = (SessionAttachment) chatMsg.getSession().getAttachment();
        
        publishEvent(new ChatMessageEvent(attach.getEntity(), String.copyValueOf(chatMsg.getUnknown1())));
    }
}
