/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.protocol.gameserver.inbound.P093_ChatMessage;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.entitysystem.events.ChatCommandEvent;
import gwlpr.mapshard.entitysystem.events.ChatMessageEvent;
import gwlpr.mapshard.models.enums.ChatChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;


/**
 * This shardlet controls incoming chat messages (it distributes them, or
 * forwards server commands)
 *
 * @author _rusty
 */
public class Chat
{

    private final static Logger LOGGER = LoggerFactory.getLogger(Chat.class);
    private final EventAggregator aggregator;
    
    
    /**
     * Constructor
     * 
     * @param       aggregator 
     */
    public Chat(EventAggregator aggregator)
    {
        this.aggregator = aggregator;
    }
    
    
    /**
     * Event handler for incoming chat messages
     * 
     * TODO: what is the additional localid attached to this message?
     *
     * @param chatMsg
     */
    @Event.Handler
    public void onChatMessage(P093_ChatMessage chatMsg)
    {
        ClientBean client = ClientBean.get(chatMsg.getChannel());

        // extract the whole prefix message, get the channel prefix and the actual message
        String prfMsg = chatMsg.getPrefixedMessage();
        ChatChannel chan = ChatChannel.getFromPrefix(prfMsg.charAt(0));
        String msg = prfMsg.substring(1);

        // failcheck
        if (chan == null || msg == null || msg.equals("")) { return; }
        
        LOGGER.debug("Got new chat message for channel {}: {}", chan.name(), msg);

        // if it's a special channel we might want to distinguish between
        // different actions.
        // there is 3 types: the usual ingame stuff; whisper; command

        if (chan == ChatChannel.Command)
        {
            aggregator.triggerEvent(new ChatCommandEvent(client.getEntity(), msg));
            return;
        }

        if (chan == ChatChannel.Whisper)
        {
            // TODO
            // no internal event, communicate with the login server directly
            return;
        }

        // we have a standart message. trigger the event
        aggregator.triggerEvent(new ChatMessageEvent(client.getEntity(), chan, msg));
    }
}
