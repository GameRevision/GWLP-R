/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P007_UnknownAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P017_AccountPermissionsAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P020_FriendsListEndAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P022_AccountGuiInfoAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class can be generically to send the appropriate actions to give sessions.
 * This is not session specific. (Thats why session must be provided by as param)
 * 
 * @author miracle444, _rusty
 */
public class LoginView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LoginView.class);
    private final ShardletContext shardletContext;
    private final DatabaseConnectionProvider db;

    
    /**
     * Constrcutor.
     * 
     * @param shardletContext
     * @param connectionProvider 
     */
    public LoginView(ShardletContext shardletContext, DatabaseConnectionProvider connectionProvider)
    {
        this.shardletContext = shardletContext;
        this.db = connectionProvider;
    }
    
    
    /**
     * This method sends the char info actions to the client, filled with
     * the database stuff.
     * 
     * @param session
     * @param accountID
     * @return 
     */
    public LoginView sendLoginInfo(Session session, int accountID)
    {
        LOGGER.debug("LoginShard: sending character info");
        
        // get the characters
        List<DBCharacter> characters = DBCharacter.getAllCharacters(db, accountID);
        
        // and build the characterInfo actions
        for (DBCharacter character : characters) 
        {
            // assemble the action
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

            buffer.put((byte) ((character.getSkin() << 5) | (character.getHeight() << 1) | character.getSex()));
            buffer.put((byte) ((character.getFace() << 7) | (character.getHaircolor() << 2) | (character.getSkin() >> 3)));
            buffer.put((byte) ((character.getPrimary() << 4) | (character.getFace() >> 1)));
            buffer.put((byte) ((character.getCampaign() << 6) | character.getHairstyle()));

            buffer.put(new byte[16]);

            byte level = 0; // TODO: replace this dummy variable
            buffer.put((byte) ((level << 4) | character.getCampaign()));                                                   

            buffer.put(new byte[] {-1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
            byte[] a = new byte[buffer.position()];
            buffer.position(0);
            buffer.get(a);
            characterInfo.setUnknown5(a);

            shardletContext.sendAction(characterInfo);
        }

        // next step:
        sendAccountGuiInfo(session);
        
        // for convenience:
        return this;
    }
    
            
    public LoginView sendAccountGuiInfo(Session session)    
    {
        LOGGER.debug("LoginShard: sending account gui settings");
        
        P022_AccountGuiInfoAction accountGuiSettings = new P022_AccountGuiInfoAction();
        accountGuiSettings.init(session);
        accountGuiSettings.setLoginCount(SessionAttachment.getLoginCount(session));
        shardletContext.sendAction(accountGuiSettings);
        
        // next step:
        sendFriendInfo(session, 0);
        
        // for convenience:
        return this;
    }
    
    
    public LoginView sendFriendInfo(Session session, int errorNumber)
    {
        LOGGER.debug("LoginShard: sending friend list end");
        
        P020_FriendsListEndAction friendListEnd = new P020_FriendsListEndAction();
        friendListEnd.init(session);
        friendListEnd.setLoginCount(SessionAttachment.getLoginCount(session));
        friendListEnd.setData1(1);
        shardletContext.sendAction(friendListEnd);


        LOGGER.debug("LoginShard: sending account permissions");
        
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

        
        LOGGER.debug("LoginShard: sending stream terminator");
            
        shardletContext.sendAction(
                StreamTerminatorView.create(session, errorNumber));
        
        // for convenience:
        return this;
    }
}
