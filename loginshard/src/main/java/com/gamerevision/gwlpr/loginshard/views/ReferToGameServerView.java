/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P009_ReferToGameServerAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the AccountGuiSettings action.
 * 
 * @author miracle444
 */
public class ReferToGameServerView
{

    public static P009_ReferToGameServerAction create(Session session)
    {
        P009_ReferToGameServerAction referToGameServer = new P009_ReferToGameServerAction();
        referToGameServer.init(session);
        referToGameServer.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
        referToGameServer.setSecurityKey1(1);
        referToGameServer.setGameMapID(0);
        referToGameServer.setServerConnectionInfo(new byte[] {0x02, 0x00, 0x23, -0x68, 0x7F, 0x00, 0x00, 0x01,
                                                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                                                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        referToGameServer.setSecurityKey2(2);
        return referToGameServer;
    }
}
