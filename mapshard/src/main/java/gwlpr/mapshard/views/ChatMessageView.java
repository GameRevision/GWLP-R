/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P081_ChatMessage;
import gwlpr.protocol.gameserver.outbound.P082_NoChatMessageOwner;
import gwlpr.protocol.gameserver.outbound.P085_ChatMessageOwner;
import gwlpr.mapshard.models.GWString;
import gwlpr.mapshard.models.enums.ChatColor;
import io.netty.channel.Channel;


/**
 * Use this view to send chat messages.
 *
 * @author _rusty
 */
public class ChatMessageView
{
    
    /**
     * Send all the packets necessary to display one chat message.
     *
     * @param channel
     */
    public static void sendMessage(Channel channel, int ownerLocalID, ChatColor color, String message)
    {
        // first construct the message
        P081_ChatMessage sendMessage = new P081_ChatMessage();
        sendMessage.init(channel);
        sendMessage.setFormattedMessage(GWString.formatChat(message));

        channel.writeAndFlush(sendMessage);

        // then tell the client about the owner/sender
        if (ownerLocalID == 0)
        {
            P082_NoChatMessageOwner noOwner = new P082_NoChatMessageOwner();
            noOwner.init(channel);
            noOwner.setOwner((short) 0);
            noOwner.setChatColor((byte) color.ordinal());//ChatColor.DarkOrange_DarkOrange.ordinal());

            channel.writeAndFlush(noOwner);
        }
        else
        {
            P085_ChatMessageOwner messageOwner = new P085_ChatMessageOwner();
            messageOwner.init(channel);
            messageOwner.setOwner((short) ownerLocalID);
            messageOwner.setChatColor((byte) color.ordinal());

            channel.writeAndFlush(messageOwner);
        }
    }
}
