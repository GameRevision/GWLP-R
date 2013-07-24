/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import gwlpr.actions.loginserver.ctos.P010_UnknownAction;
import com.gamerevision.gwlpr.database.Accounts;
import com.gamerevision.gwlpr.database.Characters;
import gwlpr.loginshard.SessionAttachment;
import gwlpr.loginshard.models.CheckLoginInfo;
import gwlpr.loginshard.views.LoginView;
import gwlpr.loginshard.views.StreamTerminatorView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import java.nio.charset.Charset;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the login process.
 * 
 * @author miracle444, _rusty
 */
public class Login extends GenericShardlet
{
    private static Logger LOGGER = LoggerFactory.getLogger(Login.class);
    private LoginView loginView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        LOGGER.info("LoginShard: init Login controller");
    }
    
    
    /**
     * Executed when a client wants to log in.
     * 
     * @param action 
     */
    @EventHandler
    public void onAccountLogin(P004_AccountLoginAction action)
    {
        LOGGER.debug("A new client wants to log in");
        
        // get the session attachment for that session
        Session session = action.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        // set the login counter
        attach.setLoginCount(action.getLoginCount());
        
        // get the login credentials
        String email = new String(action.getEmail());
        String password = new String(action.getPassword(), Charset.forName("UTF-16LE"));
        String characterName = new String(action.getCharacterName());
        
        // now lets verify that data we just got,
        // so call the model that handles that
        CheckLoginInfo checkInfo = new CheckLoginInfo(db, email, characterName);

        // TODO: STATIC Login verification!!!
        if (!checkInfo.isValid(password)) 
        {
            // login failed, abort the login process
            StreamTerminatorView.create(session, checkInfo.getErrorCode());
            
            LOGGER.debug("Client login failed");
            return;
        }
        
        LOGGER.info("LoginShard: client successfully logged in. [email {} ]", email);

        // update the attachment with the data (because we now know 
        // that it is correct)
        attach.setAccountId(AccountsfindByEMail(email).getId());        
        attach.setCharacterId(CharacterEntity.getCharacter(db, characterName).getId());

        // send all login specific packets as a reply
        loginView.sendLoginInfo(session, attach.getAccountId());

    }
    
    
    /**
     * Executed when the client chooses a character to play with.
     * 
     * @param action 
     */
    @EventHandler
    public void onCharacterPlayName(P010_UnknownAction action)
    {
        LOGGER.debug("Got the character play name packet");
        
        // get the session attachment for that session
        Session session = action.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        
        attach.setLoginCount(action.getUnknown1());
        attach.setCharacterId(CharacterEntity.getCharacter(db, new String(action.getUnknown2())).getId());
        
        loginView.sendFriendInfo(session, 0);
    }
    
}
