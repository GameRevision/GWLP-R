/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.actions.loginserver.stoc.P003_StreamTerminatorAction;
import gwlpr.loginshard.SessionAttachment;
import realityshard.shardlet.Session;


/**
 * This is a view fills the StreamTerminator action.
 * 
 * @author miracle444
 */
public class StreamTerminatorView
{

    public static void create(Session session, int errorCode)
    {
        P003_StreamTerminatorAction streamTerminator = new P003_StreamTerminatorAction();
        streamTerminator.init(session);
        streamTerminator.setLoginCount(SessionAttachment.getLoginCount(session));
        streamTerminator.setErrorCode(errorCode);
        
        session.send(streamTerminator);
    }
}
