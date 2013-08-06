/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.actions.gameserver.ctos.P093_UnknownAction;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.events.ChatCommandEvent;
import gwlpr.mapshard.events.ChatMessageEvent;
import gwlpr.mapshard.models.enums.ChatChannel;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.utils.GenericShardlet;
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
        ClientBean attach = (ClientBean) chatMsg.getSession().getAttachment();

        // extract the whole message
        String msg = String.copyValueOf(chatMsg.getUnknown1());

        // get the channel (it's the prefix of our message, as the first char)
        ChatChannel chan = ChatChannel.getFromPrefix(msg.charAt(0));

        // trim that channel now...
        msg = msg.substring(1);

        // failcheck
        if (chan == null) { return; }
        
        LOGGER.debug("Got new chat message for channel {}: {}", chan.name(), msg);

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
