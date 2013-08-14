/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.database.entities.Account;
import gwlpr.database.entities.Character;
import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.models.enums.ErrorCode;
import gwlpr.protocol.loginserver.outbound.P007_CharacterInfo;
import gwlpr.protocol.loginserver.outbound.P017_AccountPermissions;
import gwlpr.protocol.loginserver.outbound.P020_FriendsListEnd;
import gwlpr.protocol.loginserver.outbound.P022_AccountGuiInfo;
import io.netty.channel.Channel;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This view creates the packets used for the login process.
 * 
 * @author miracle444, _rusty
 */
public class LoginView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(LoginView.class);

    
    public static void sendLoginInfo(Channel channel, Account account)
    {
        LOGGER.debug("Sending character info");
        
        // and build the characterInfo actions
        for (Character character : account.getCharacterCollection()) 
        {
            // assemble the action
            P007_CharacterInfo characterInfo = new P007_CharacterInfo();
            
            characterInfo.init(channel);
            
            characterInfo.setLoginCount(ClientBean.get(channel).getLoginCount());
            characterInfo.setCharacterUID(UUID.randomUUID());
            characterInfo.setUnknown1(0);
            characterInfo.setCharacterName(character.getName());                

            // prepare the char info
            ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
            buffer.putShort((short) 6);
            buffer.putShort((short) character.getLastOutpost().getGameID());
            buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});

            buffer.put((byte) ((character.getSkin().byteValue() << 5) | (character.getHeight().byteValue() << 1) | character.getSex().byteValue()));
            buffer.put((byte) ((character.getFace().byteValue() << 7) | (character.getHaircolor().byteValue() << 2) | (character.getSkin().byteValue() >> 3)));
            buffer.put((byte) ((character.getPrimaryProfession().getId().byteValue() << 4) | (character.getFace().byteValue() >> 1)));
            buffer.put((byte) ((character.getCampaign().byteValue() << 6) | character.getHairstyle().byteValue()));

            buffer.put(new byte[16]);

            // TODO implement the level as not null
            byte level = 0;//character.getLevel().getLevel().byteValue();
            buffer.put((byte) ((level << 4) | character.getCampaign()));                                                   

            buffer.put(new byte[] {-1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
            byte[] a = new byte[buffer.position()];
            buffer.position(0);
            buffer.get(a);
            characterInfo.setCharacterInfo(a);

            channel.writeAndFlush(characterInfo);
        }

        // next step:
        sendAccountGuiInfo(channel, account);
    }
    
            
    public static void sendAccountGuiInfo(Channel channel, Account account)    
    {
        LOGGER.debug("Sending account gui settings");
        
        P022_AccountGuiInfo accountGuiSettings = new P022_AccountGuiInfo();
        accountGuiSettings.init(channel);
        accountGuiSettings.setLoginCount(ClientBean.get(channel).getLoginCount());
        accountGuiSettings.setSettings(account.getGui());
        channel.writeAndFlush(accountGuiSettings);
        
        // next step:
        sendFriendInfo(channel, ErrorCode.None);
    }
    
    
    public static void sendFriendInfo(Channel channel, ErrorCode errorNumber)
    {
        LOGGER.debug("Sending friend list end");
        
        P020_FriendsListEnd friendListEnd = new P020_FriendsListEnd();
        friendListEnd.init(channel);
        friendListEnd.setLoginCount(ClientBean.get(channel).getLoginCount());
        friendListEnd.setUnknown1(1);
        channel.writeAndFlush(friendListEnd);


        LOGGER.debug("Sending account permissions");
        
        P017_AccountPermissions accountPermissions = new P017_AccountPermissions();
        accountPermissions.init(channel);
        accountPermissions.setLoginCount(ClientBean.get(channel).getLoginCount());
        accountPermissions.setTerritory(2);
        accountPermissions.setTerritoryChanges(4);
        accountPermissions.setUnknown1(new byte[] { 0x3F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 });
        accountPermissions.setUnknown2(new byte[] { -0x80, 0x3F, 0x02, 0x00, 0x03, 0x00, 0x08, 0x00 });
        // TODO: the account id is the client ID right?
        accountPermissions.setAccountUID(ClientBean.getHandle(channel).getUid());
        accountPermissions.setUnknown3(new byte[] { 0x55, -0x4A, 0x77, 0x59, 0x0C, 0x0C, 0x15, 0x46, -0x53, -0x56, 0x33, 0x43, 0x4A, -0x6F, 0x23, 0x6A });
        accountPermissions.setChangeAccountSettings(8);
        accountPermissions.setAccountFeatures(new byte[] { 0x01, 0x00, 0x06, 0x00, 0x57, 0x00, 0x01, 0x00 });
        accountPermissions.setEulaAccepted((byte) 23);
        accountPermissions.setUnknown4(0);
        channel.writeAndFlush(accountPermissions);

        
        LOGGER.debug("Sending stream terminator");
            
        StreamTerminatorView.send(channel, errorNumber);
    }
}
