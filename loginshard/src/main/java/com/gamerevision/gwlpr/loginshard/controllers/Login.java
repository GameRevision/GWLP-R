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
    private DatabaseConnectionProvider connectionProvider;
    private LoginView loginView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        LOGGER.debug("Login shardlet initialized!");
    }
    
    
    /**
     * Event handler
     * 
     * @param action 
     */
    @EventHandler
    public void accountLoginHandler(P004_AccountLoginAction action)
    {
        // extract the data from the action
        String eMail = new String(action.getEmail());
        String password = new String(action.getPassword(), Charset.forName("UTF-16LE"));
        String characterName = new String(action.getCharacterName());
        
        LOGGER.debug("got the account login packet");
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        
        attachment.setLoginCount(action.getLoginCount());
        
  
        CheckLoginInfo checkInfo = new CheckLoginInfo(connectionProvider, eMail);

        if (checkInfo.isValid(password) || true)
        {
            LOGGER.debug("successfully logged in");
            attachment.setAccountId(DBAccount.getByEMail(connectionProvider, eMail).getId());        
            attachment.setCharacterName(characterName);
            
            
            List<DBCharacter> characters = DBCharacter.getAllCharacters(connectionProvider, attachment.getAccountId());
            
            for (DBCharacter character : characters)
            {
                loginView.addCharacter(session, character);
            }
            
            loginView.loginSuccessful(session);
        }
        else
        {
            sendAction(StreamTerminatorView.create(session, checkInfo.getErrorCode()));
        }
    }
    
    
    @EventHandler
    public void characterPlayNameHandler(P010_UnknownAction action)
    {
        LOGGER.debug("got the character play name packet");
        Session session = action.getSession();
        
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        attachment.setLoginCount(action.getUnknown1());
        attachment.setCharacterName(new String(action.getUnknown2()));
        
        loginView.operationCompleted(session, 0);
    }
    
    
    @EventHandler
    public void databaseConnectionProviderHandler(LoginShardStartupEvent event)
    {
        this.connectionProvider = event.getConnectionProvider();
        this.loginView = new LoginView(getShardletContext(), connectionProvider);
    }
}
