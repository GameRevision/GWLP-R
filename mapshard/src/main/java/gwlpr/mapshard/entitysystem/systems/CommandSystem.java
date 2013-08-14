/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.systems;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.GenericSystem;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.entityfactories.NPCFactory;
import gwlpr.mapshard.entitysystem.events.ChatCommandEvent;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.Strings;
import gwlpr.mapshard.models.enums.ChatColor;
import gwlpr.mapshard.views.ChatMessageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;
import realityshard.container.util.HandleRegistry;


/**
 * This system handles any chat-commands.
 *
 * @author _rusty
 */
public final class CommandSystem extends GenericSystem
{

    private final static Logger LOGGER = LoggerFactory.getLogger(CommandSystem.class);
    
    private final static int UPDATEINTERVAL = 0;
    private final EntityManager entityManager;
    private final HandleRegistry<ClientBean> clientRegistry;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       entityManager 
     * @param       clientRegistry
     */
    public CommandSystem(EventAggregator aggregator, EntityManager entityManager, HandleRegistry<ClientBean> clientRegistry)
    {
        super(aggregator, UPDATEINTERVAL);
        this.entityManager = entityManager;
        this.clientRegistry = clientRegistry;
    }


    /**
     * This is invoked periodically. (Or never, depending on the interval)
     *
     * @param timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
    }


    /**
     * Event handler.
     * This is the main business of this class. Handle server commands
     * and check if the people issuing them have sufficient rights to do so.
     *
     * @param chatCmd
     */
    @Event.Handler
    public void onChatCommand(ChatCommandEvent chatCmd)
    {
        String command = getCommand(chatCmd.getMessage());
        List<String> parameters = getParameters(chatCmd.getMessage());

        // fail check (command execution fails automatically if the sender
        // has no chat option compontent)
        ChatOptions senderChatOptions = chatCmd.getSender().get(ChatOptions.class);
        if (senderChatOptions == null) { return; }
        
        LOGGER.debug("Got command '{}' with parameters '{}'", command, parameters.toString());

        if ("fun".equals(command))
        {
            // TODO remove me when not needed anyore ;)
            Entity ply = chatCmd.getSender();
            
            NPCFactory.mockNpc(ply.get(Position.class).position, entityManager);
        }
        else if (!senderChatOptions.availableCommands.contains(command))
        {
            // command is not available for the sender
            
            // try to get the client
            ClientBean client = clientRegistry.getObj(chatCmd.getSender().getUuid());
            
            if (client == null) { return; }

            // if the sender was a network client (as opposed to NPCs etc.)
            // we'll tell her that the execution failed:
            String cmdFailed = "The command does not exist or you dont have sufficient permissions to execute it.";
            
            ChatMessageView.sendMessage(
                    client.getChannel(),
                    0,
                    ChatColor.DarkOrange_DarkOrange,
                    Strings.CmdNotAvail.text());
            
            return;
        }

        // TODO implment me!
        // what happens with the command after passing all the checks?
    }


    /**
     * Helper.
     * Extracts the first word of the message.
     *
     * @param       message
     * @return      The first word (the command), or null if there is none
     */
    private String getCommand(String message)
    {
        String[] words = message.split(" ");

        // failcheck
        if (words.length == 0) { return null; }

        return words[0];
    }


    /**
     * Helper.
     * Extracts the parameters of the message.
     *
     * @param       message
     * @return      The first word (the command), or null if there is none
     */
    private List<String> getParameters(String message)
    {
        List<String> words = new ArrayList<>(Arrays.asList(message.split(" ")));

        // failcheck
        if (words.size() <= 1) { return new ArrayList<>(); }

        // remove the command word
        words.remove(0);

        return words;
    }
}
