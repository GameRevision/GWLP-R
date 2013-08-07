/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.models.enums.ChatChannel;
import realityshard.container.events.Event;


/**
 * Triggered when we got a chat message (can come from NPCs too ;)
 *
 * Server commands are handled with a separate event!
 *
 * @author _rusty
 */
public class ChatMessageEvent implements Event
{

    private final Entity sender;
    private final ChatChannel channel;
    private final String message;


    /**
     * Constructor.
     *
     * @param       sender                  The entity that sent
     * @param       message                 The message
     */
    public ChatMessageEvent(Entity sender, ChatChannel channel, String message)
    {
        this.sender = sender;
        this.channel = channel;
        this.message = message;
    }


    /**
     * Getter.
     *
     * @return
     */
    public Entity getSender()
    {
        return sender;
    }


    /**
     * Getter.
     *
     * @return
     */
    public ChatChannel getChannel()
    {
        return channel;
    }


    /**
     * Getter.
     *
     * @return
     */
    public String getMessage()
    {
        return message;
    }
}
