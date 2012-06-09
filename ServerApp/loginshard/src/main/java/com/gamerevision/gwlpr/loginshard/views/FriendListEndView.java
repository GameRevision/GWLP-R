/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P020_FriendListEndAction;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the FriendListEnd action.
 * 
 * @author miracle444
 */
public class FriendListEndView
{

    public static P020_FriendListEndAction create(Session session)
    {
        P020_FriendListEndAction friendListEnd = new P020_FriendListEndAction();
        friendListEnd.init(session);
        friendListEnd.setUnknown1((int) session.getAttribute("SyncCount"));
        friendListEnd.setUnknown2(1);
        return friendListEnd;
    }
}
