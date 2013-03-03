/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P081_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P082_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P085_UnknownAction;
import com.gamerevision.gwlpr.mapshard.models.GWString;
import com.gamerevision.gwlpr.mapshard.models.enums.ChatColor;
import com.realityshard.shardlet.Session;


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
     * @param session
     */
    public static void sendMessage(Session session, int ownerLocalID, ChatColor color, String message)
    {
        // first construct the message
        P081_UnknownAction sendMessage = new P081_UnknownAction();
        sendMessage.init(session);
        sendMessage.setUnknown1(GWString.formatChat(message).toCharArray());

        session.send(sendMessage);

        // then tell the client about the owner/sender
        if (ownerLocalID == 0)
        {
            P082_UnknownAction noOwner = new P082_UnknownAction();
            noOwner.init(session);
            noOwner.setUnknown1((short) 0);
            noOwner.setUnknown2((byte) color.ordinal());//ChatColor.DarkOrange_DarkOrange.ordinal());

            session.send(noOwner);
        }
        else
        {
            P085_UnknownAction messageOwner = new P085_UnknownAction();
            messageOwner.init(session);
            messageOwner.setUnknown1((short) ownerLocalID);
            messageOwner.setUnknown2((byte) color.ordinal());

            session.send(messageOwner);
        }
    }
}
