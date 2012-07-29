/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P020_FriendsListEndAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the FriendListEnd action.
 * 
 * @author miracle444
 */
public class FriendsListEndView
{

    public static P020_FriendsListEndAction create(Session session)
    {
        P020_FriendsListEndAction friendListEnd = new P020_FriendsListEndAction();
        friendListEnd.init(session);
        friendListEnd.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
        friendListEnd.setData1(1);
        return friendListEnd;
    }
}
