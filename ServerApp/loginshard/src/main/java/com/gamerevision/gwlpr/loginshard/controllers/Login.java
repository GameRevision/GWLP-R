/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P003_StreamTerminatorAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P017_AccountPermissionsAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P020_FriendListEndAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P022_AccountGuiSettingsAction;
import com.realityshard.shardlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the login process.
 * 
 * @author miracle444
 */
public class Login extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Login.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("login shardlet initialized!");
        
        // fake always accept verifier
        ShardletActionVerifier verf = new ShardletActionVerifier() {

            @Override
            public boolean check(ShardletAction action) 
            {
                return true;
            }
        };
        
        getShardletContext().addClientVerifier(verf, true);
    }
    
    
    @EventHandler
    public void accountLoginHandler(P004_AccountLoginAction action)
    {
        LOGGER.debug("got the account login packet");
        
        Session session = action.getSession();
        session.setAttribute("SyncCount", action.getUnknown1());
        
        
        LOGGER.debug("sending account gui settings");
        
        P022_AccountGuiSettingsAction accountGuiSettings = new P022_AccountGuiSettingsAction();
        accountGuiSettings.init(session);
        accountGuiSettings.setUnknown1((int) session.getAttribute("SyncCount"));
        sendAction(accountGuiSettings);
        
        
        LOGGER.debug("sending friend list end");
        
        P020_FriendListEndAction friendListEnd = new P020_FriendListEndAction();
        friendListEnd.init(session);
        friendListEnd.setUnknown1((int) session.getAttribute("SyncCount"));
        friendListEnd.setUnknown2(1);
        sendAction(friendListEnd);
        
        
        LOGGER.debug("sending account permissions");
        
        P017_AccountPermissionsAction accountPermissions = new P017_AccountPermissionsAction();
        accountPermissions.init(session);
        accountPermissions.setUnknown1((int) session.getAttribute("SyncCount"));
        accountPermissions.setUnknown2(2);
        accountPermissions.setUnknown3(4);
        accountPermissions.setUnknown4(new byte[] { 0x3F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 });
        accountPermissions.setUnknown5(new byte[] { -0x80, 0x3F, 0x02, 0x00, 0x03, 0x00, 0x08, 0x00 });
        accountPermissions.setUnknown6(new byte[] { 0x37, 0x4B, 0x09, -0x45, -0x3E, -0x0A, 0x74, 0x43, -0x56, -0x55, 0x35, 0x4D, -0x12, -0x49, -0x51, 0x08 });
        accountPermissions.setUnknown7(new byte[] { 0x55, -0x4A, 0x77, 0x59, 0x0C, 0x0C, 0x15, 0x46, -0x53, -0x56, 0x33, 0x43, 0x4A, -0x6F, 0x23, 0x6A });
        accountPermissions.setUnknown8(8);
        accountPermissions.setUnknown9(new byte[] { 0x01, 0x00, 0x06, 0x00, 0x57, 0x00, 0x01, 0x00 });
        accountPermissions.setUnknown10((byte) 23);
        accountPermissions.setUnknown11(0);
        sendAction(accountPermissions);
        
        
        LOGGER.debug("sending stream terminator");
        
        P003_StreamTerminatorAction streamTerminator = new P003_StreamTerminatorAction();
        streamTerminator.init(session);
        streamTerminator.setUnknown1((int) session.getAttribute("SyncCount"));
        streamTerminator.setUnknown2(0);
        sendAction(streamTerminator);
    }
}
