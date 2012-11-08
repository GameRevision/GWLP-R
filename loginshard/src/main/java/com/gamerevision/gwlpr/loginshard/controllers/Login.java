/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import com.gamerevision.gwlpr.actions.loginserver.ctos.P010_UnknownAction;
import com.gamerevision.gwlpr.database.DBAccount;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.gamerevision.gwlpr.loginshard.events.LoginShardStartupEvent;
import com.gamerevision.gwlpr.loginshard.models.CheckLoginInfo;
import com.gamerevision.gwlpr.loginshard.views.LoginView;
import com.gamerevision.gwlpr.loginshard.views.StreamTerminatorView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericShardlet;
import java.nio.charset.Charset;
import java.util.List;
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
    private DatabaseConnectionProvider db;
    private LoginView loginView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        LOGGER.debug("LoginShard: init Login controller");
    }
    
    
    /**
     * Executes startup features, like storing database references etc.
     * 
     * @param event 
     */
    @EventHandler
    public void onStartUp(LoginShardStartupEvent event)
    {
        // get the database provider
        db = event.getConnectionProvider();
        
        // and initialize the LoginView that we use to handle incoming clients.
        loginView = new LoginView(getShardletContext(), db);
    }
    
    
    /**
     * Executed when a client wants to log in.
     * 
     * @param action 
     */
    @EventHandler
    public void onAccountLogin(P004_AccountLoginAction action)
    {
        LOGGER.debug("LoginShard: a new client wants to log in.");
        
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
        CheckLoginInfo checkInfo = new CheckLoginInfo(db, email);

        // TODO: STATIC Login verification!!!
        if (!(checkInfo.isValid(password) || true)) 
        {
            // login failed, abort the login process
            sendAction(StreamTerminatorView.create(session, checkInfo.getErrorCode()));
            
            LOGGER.debug("LoginShard: client login failed");
            return;
        }
        
        LOGGER.debug("LoginShard: client successfully logged in");

        // update the attachment with the data (because we now know 
        // that it is correct)
        attach.setAccountId(DBAccount.getByEMail(db, email).getId());        
        attach.setCharacterName(characterName);

        // it's our turn to continue with the login process now.
        // the view we are using is
        List<DBCharacter> characters = DBCharacter.getAllCharacters(db, attach.getAccountId());

        // send all login specific packets as a reply
        loginView.sendLoginInfo(session, attach.getAccountId());

    }
    
    
    @EventHandler
    public void characterPlayNameHandler(P010_UnknownAction action)
    {
        LOGGER.debug("got the character play name packet");
        Session session = action.getSession();
        
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        attachment.setLoginCount(action.getUnknown1());
        attachment.setCharacterName(new String(action.getUnknown2()));
        
        loginView.sendFriendInfo(session, 0);
    }
    
}
