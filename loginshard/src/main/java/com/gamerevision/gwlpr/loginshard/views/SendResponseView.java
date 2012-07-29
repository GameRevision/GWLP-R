/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P038_SendResponseAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the RequestResponse action.
 * 
 * @author miracle444
 */
public class SendResponseView
{

    public static P038_SendResponseAction create(Session session)
    {
        P038_SendResponseAction sendResponse = new P038_SendResponseAction();
        sendResponse.init(session);
        sendResponse.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
        sendResponse.setData1(0);
        return sendResponse;
    }
}
