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
        computerInfoReply.setData1(1905605949);
        computerInfoReply.setLoginCount((int) session.getAttribute("SyncCount"));
        computerInfoReply.setData2(0);
        computerInfoReply.setData3(1);
        return computerInfoReply;
    }
}
