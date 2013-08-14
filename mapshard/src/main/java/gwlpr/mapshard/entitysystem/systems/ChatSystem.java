/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.systems;

import gwlpr.mapshard.entitysystem.GenericSystem;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.events.ChatMessageEvent;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.enums.ChatColor;
import gwlpr.mapshard.views.ChatMessageView;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This system is used to manage any ingame chat.
 *
 * @author _rusty
 */
public final class ChatSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = 0; // update disabled!
    private final HandleRegistry<ClientBean> clientRegistry;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       clientRegistry  
     */
    public ChatSystem(EventAggregator aggregator, HandleRegistry<ClientBean> clientRegistry)
    {
        super(aggregator, UPDATEINTERVAL);
        this.clientRegistry = clientRegistry;
    }


    /**
     * Update Loop
     * TODO: Is there anything we need to do regularily in the chat system?
     *
     * @param       timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
    }


    /**
     * Event handler.
     * This is our main business. Distribute the message depending on its
     * sender and the target group (or account)
     *
     * Note that this event does not necessarly have to be triggered by a player,
     * because NPCs can use the chat system as well!
     *
     * @param msg
     */
    @Event.Handler
    public void onChatMessage(ChatMessageEvent msg)
    {
        // TODO implement me properly!

        // find out the localID of the sender
        AgentIdentifiers aIDs = msg.getSender().get(AgentIdentifiers.class);
        int ownerLID = 0;
        if (aIDs != null) { ownerLID = aIDs.localID; }

        // for now, we'll be sending the message to all people on the server
        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles())
        {
            ChatMessageView.sendMessage(
                    clientHandle.get().getChannel(),
                    ownerLID,
                    ChatColor.Yellow_White,
                    msg.getMessage());
        }
    }
}
