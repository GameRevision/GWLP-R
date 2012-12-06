/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P009_ReferToGameServerAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This view assembles the actions needed to dispatch a client to a game server.
 * 
 * @author miracle444, _rusty
 */
public class DispatcherView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DispatcherView.class);
    private final ShardletContext shardletContext;

    
    /**
     * Constrcutor.
     * 
     * @param       shardletContext 
     */
    public DispatcherView(ShardletContext shardletContext)
    {
        this.shardletContext = shardletContext;
    }

    
    /**
     * This method is used to refer the client (session) to a game server.
     * TODO: BUG: IP and Port are STATIC!
     * 
     * @param       session
     * @param       key1
     * @param       key2
     * @param       mapId 
     */
    public void referToGameServer(Session session, int key1, int key2, int mapId)
    {
        P009_ReferToGameServerAction referToGameServer = new P009_ReferToGameServerAction();
        
        referToGameServer.init(session);
        referToGameServer.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
        referToGameServer.setSecurityKey1(key1);
        referToGameServer.setGameMapID(mapId);
        referToGameServer.setServerConnectionInfo(new byte[] {0x02, 0x00, 0x23, -0x68, 0x7F, 0x00, 0x00, 0x01,
                                                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                                                  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        referToGameServer.setSecurityKey2(key2);
        
        session.send(referToGameServer);
    }
}
