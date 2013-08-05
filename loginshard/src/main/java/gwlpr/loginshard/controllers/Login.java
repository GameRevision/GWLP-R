/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.database.entities.Character;
import gwlpr.database.jpa.CharacterJpaController;
import gwlpr.loginshard.ChannelAttachment;
import gwlpr.loginshard.models.LoginModel;
import gwlpr.loginshard.views.LoginView;
import gwlpr.loginshard.views.StreamTerminatorView;
import gwlpr.protocol.loginserver.inbound.P004_AccountLogin;
import gwlpr.protocol.loginserver.inbound.P010_CharacterPlayName;
import io.netty.channel.Channel;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;


/**
 * This shardlet handles the login process.
 * 
 * @author miracle444, _rusty
 */
public class Login
{
    private static Logger LOGGER = LoggerFactory.getLogger(Login.class);
    private static final Charset CHARSET_UTF16 = Charset.forName("utf-16le");
    
    
    /**
     * Executed when a client wants to log in.
     * 
     * @param action 
     */
    @Event.Handler
    public void onAccountLogin(P004_AccountLogin action)
    {
        LOGGER.debug("A new client wants to log in");
        
        // get the session attachment for that session
        Channel channel = action.getChannel();
        ChannelAttachment attach = channel.attr(ChannelAttachment.KEY).get();
        
        // set the login counter
        attach.setLoginCount(action.getLoginCount());
        
        // get the login credentials
        String email = action.getEmail();
        String password = new String(action.getPassword(), CHARSET_UTF16);
        String characterName = action.getCharacterName();
        
        // now lets verify that data we just got,
        // so call the model that handles that
        LoginModel model = new LoginModel(email, characterName);

        // TODO: STATIC Login verification!!!
        if (!model.isValid(password)) 
        {
            // login failed, abort the login process
            StreamTerminatorView.send(channel, model.getErrorCode());
            
            LOGGER.debug("Client login failed.");
            return;
        }
        
        LOGGER.info("Client successfully logged in. [email {} ]", email);

        // update the attachment with the data (because we now know 
        // that it is correct)
        attach.setAccountId(model.getAccId());        
        attach.setCharacterId(model.getCharId());

        // send all login specific packets as a reply
        LoginView.sendLoginInfo(channel, model.getAccount());
    }
    
    
    /**
     * Executed when the client chooses a character to play with.
     * 
     * @param action 
     */
    @Event.Handler
    public void onCharacterPlayName(P010_CharacterPlayName action)
    {
        LOGGER.debug("Got the character play name packet");
        
        // get the session attachment for that session
        Channel channel = action.getChannel();
        ChannelAttachment attach = channel.attr(ChannelAttachment.KEY).get();
        
        // set login counter
        attach.setLoginCount(action.getLoginCount());
        
        // check the submitted information
        LoginModel model = new LoginModel(attach.getAccountId(), action.getCharacterName());
        
        if (!model.hasChar())
        {
            LOGGER.info("Manipulation detected: Trying to acces a character that is not connected to this account.");
            channel.close();
        }
        
        attach.setCharacterId(model.getCharId());
        
        LoginView.sendFriendInfo(channel, 0);
    }
    
}
