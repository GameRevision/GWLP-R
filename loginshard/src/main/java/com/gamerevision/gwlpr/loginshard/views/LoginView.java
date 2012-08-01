/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.*;
import com.gamerevision.gwlpr.framework.database.DBCharacter;
import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LoginView.class);
    private final ShardletContext shardletContext;
    private final DatabaseConnectionProvider connectionProvider;

    
    public LoginView(ShardletContext shardletContext, DatabaseConnectionProvider connectionProvider)
    {
        this.shardletContext = shardletContext;
        this.connectionProvider = connectionProvider;
    }

    
    public void addCharacter(Session session, DBCharacter character)
    {
        LOGGER.debug("sending character info");

        P007_UnknownAction characterInfo = new P007_UnknownAction();
        characterInfo.init(session);
        characterInfo.setUnknown1(SessionAttachment.getLoginCount(session));
        characterInfo.setUnknown2(new byte[16]);
        characterInfo.setUnknown3(0);
        characterInfo.setUnknown4(character.getName().toCharArray());                

        ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 6);
        buffer.putShort((short) 248);
        buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});
        buffer.put(character.getAppearance());
        buffer.put(new byte[16]);
        buffer.put(new byte[] {0, -1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
        byte[] a = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(a);
        characterInfo.setUnknown5(a);

        shardletContext.sendAction(characterInfo);
    }
    
            
    public void loginSuccessful(Session session)    
    {
        LOGGER.debug("sending account gui settings");
        
        P022_AccountGuiInfoAction accountGuiSettings = new P022_AccountGuiInfoAction();
        accountGuiSettings.init(session);
        accountGuiSettings.setLoginCount(SessionAttachment.getLoginCount(session));
        shardletContext.sendAction(accountGuiSettings);
        
        operationCompleted(session, 0);
    }
    
    
    public void operationCompleted(Session session, int errorNumber)
    {
        LOGGER.debug("sending friend list end");
        
        P020_FriendsListEndAction friendListEnd = new P020_FriendsListEndAction();
        friendListEnd.init(session);
        friendListEnd.setLoginCount(SessionAttachment.getLoginCount(session));
        friendListEnd.setData1(1);
        shardletContext.sendAction(friendListEnd);


        LOGGER.debug("sending account permissions");
        
        P017_AccountPermissionsAction accountPermissions = new P017_AccountPermissionsAction();
        accountPermissions.init(session);
        accountPermissions.setLoginCount(SessionAttachment.getLoginCount(session));
        accountPermissions.setTerritory(2);
        accountPermissions.setTerritoryChanges(4);
        accountPermissions.setData1(new byte[] { 0x3F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 });
        accountPermissions.setData2(new byte[] { -0x80, 0x3F, 0x02, 0x00, 0x03, 0x00, 0x08, 0x00 });
        accountPermissions.setData3(new byte[] { 0x37, 0x4B, 0x09, -0x45, -0x3E, -0x0A, 0x74, 0x43, -0x56, -0x55, 0x35, 0x4D, -0x12, -0x49, -0x51, 0x08 });
        accountPermissions.setData4(new byte[] { 0x55, -0x4A, 0x77, 0x59, 0x0C, 0x0C, 0x15, 0x46, -0x53, -0x56, 0x33, 0x43, 0x4A, -0x6F, 0x23, 0x6A });
        accountPermissions.setChangeAccountSettings(8);
        accountPermissions.setAccountFeatures(new byte[] { 0x01, 0x00, 0x06, 0x00, 0x57, 0x00, 0x01, 0x00 });
        accountPermissions.setEulaAccepted((byte) 23);
        accountPermissions.setData5(0);
        shardletContext.sendAction(accountPermissions);

        
        LOGGER.debug("sending stream terminator");
            
        shardletContext.sendAction(StreamTerminatorView.create(session, errorNumber));
    }
}
