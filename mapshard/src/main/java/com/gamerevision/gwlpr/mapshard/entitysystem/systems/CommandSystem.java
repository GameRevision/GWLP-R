/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.systems;

import com.gamerevision.gwlpr.mapshard.entitysystem.GenericSystem;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.events.ChatCommandEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.models.Strings;
import com.gamerevision.gwlpr.mapshard.models.enums.ChatColor;
import com.gamerevision.gwlpr.mapshard.views.ChatMessageView;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This system handles any chat-commands.
 *
 * @author _rusty
 */
public final class CommandSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = 0;
    private final ClientLookupTable clientLookup;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       clientLookup
     */
    public CommandSystem(EventAggregator aggregator, ClientLookupTable clientLookup)
    {
        super(aggregator, UPDATEINTERVAL);
        this.clientLookup = clientLookup;
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
    @EventHandler
    public void onChatCommand(ChatCommandEvent chatCmd)
    {
        String command = getCommand(chatCmd.getMessage());
        List<String> parameters = getParameters(chatCmd.getMessage());

        // fail check (command execution fails automatically if the sender
        // has not chat option compontent)
        ChatOptions senderChatOptions = chatCmd.getSender().get(ChatOptions.class);
        if (senderChatOptions == null) { return; }

        if (!senderChatOptions.availableCommands.contains(command))
        {
            // command is not available for the sender
            Session client = clientLookup.getByEntity(chatCmd.getSender());

            // if the sender was a network client (as opposed to NPCs etc.)
            // we'll tell her that the execution failed:
            String cmdFailed = "The command does not exist or you dont have sufficient permissions to execute it.";
            ChatMessageView.sendMessage(
                    client,
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
        List<String> words = Arrays.asList(message.split(" "));

        // failcheck
        if (words.size() <= 1) { return new ArrayList<>(); }

        // remove the command word
        words.remove(0);

        return words;
    }
}
