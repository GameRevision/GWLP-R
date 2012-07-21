/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P001_ComputerInfoReplyAction;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the ComputerInfoReply action.
 * 
 * @author miracle444
 */
public class ComputerInfoReplyView
{

    public static P001_ComputerInfoReplyAction create(Session session)
    {
        P001_ComputerInfoReplyAction computerInfoReply = new P001_ComputerInfoReplyAction();
        computerInfoReply.init(session);
        computerInfoReply.setUnknown1(1905605949);
        computerInfoReply.setUnknown2((int) session.getAttribute("SyncCount"));
        computerInfoReply.setUnknown3(0);
        computerInfoReply.setUnknown4(1);
        return computerInfoReply;
    }
}
