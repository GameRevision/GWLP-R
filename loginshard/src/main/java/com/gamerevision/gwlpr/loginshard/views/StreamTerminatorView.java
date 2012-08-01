/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P003_StreamTerminatorAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the StreamTerminator action.
 * 
 * @author miracle444
 */
public class StreamTerminatorView
{

    public static P003_StreamTerminatorAction create(Session session, int errorCode)
    {
        P003_StreamTerminatorAction streamTerminator = new P003_StreamTerminatorAction();
        streamTerminator.init(session);
        streamTerminator.setLoginCount(SessionAttachment.getLoginCount(session));
        streamTerminator.setErrorCode(errorCode);
        return streamTerminator;
    }
}
