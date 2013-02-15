/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P093_UnknownAction;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.ChatCommandEvent;
import com.gamerevision.gwlpr.mapshard.events.ChatMessageEvent;
import com.gamerevision.gwlpr.mapshard.models.enums.ChatChannel;
import com.realityshard.shardlet.EventHandler;
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

        // extract the whole message
        String msg = String.copyValueOf(chatMsg.getUnknown1());

        // get the channel (it's the prefix of our message, as the first char)
        ChatChannel chan = ChatChannel.getFromPrefix(msg.charAt(0));

        // trim that channel now...
        msg = msg.substring(1);

        // failcheck
        if (chan == null) { return; }

        // if it's a special channel we might want to distinguish between
        // different actions.
        // there is 3 types: the usual ingame stuff; whisper; command

        if (chan == ChatChannel.Command)
        {
            publishEvent(new ChatCommandEvent(attach.getEntity(), msg));
            return;
        }

        if (chan == ChatChannel.Whisper)
        {
            // TODO
            // no internal event, communicate with the login server directly
            return;
        }

        // we have a standart message. trigger the event
        publishEvent(new ChatMessageEvent(attach.getEntity(), chan, msg));
    }
}
