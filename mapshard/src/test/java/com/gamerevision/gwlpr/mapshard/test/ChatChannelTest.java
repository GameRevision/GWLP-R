/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gamerevision.gwlpr.mapshard.test;

import com.gamerevision.gwlpr.mapshard.models.enums.ChatChannel;
import org.junit.Test;


/**
 * Trying to read in different chars to get different chat channels.
 *
 * @author _rusty
 */
public class ChatChannelTest
{

    @Test
    public void testChatChannelValues()
    {
        ChatChannel chan = ChatChannel.getFromPrefix('@');
        assert chan.equals(ChatChannel.Guild);

        chan = ChatChannel.getFromPrefix('?');
        assert chan == null;
    }
}
