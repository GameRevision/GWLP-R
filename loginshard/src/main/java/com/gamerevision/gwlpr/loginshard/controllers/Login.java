/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.loginshard.events.DatabaseConnectionProviderEvent;
import com.gamerevision.gwlpr.loginshard.model.logic.CheckLoginInfo;
import com.gamerevision.gwlpr.loginshard.views.AccountGuiInfoView;
import com.gamerevision.gwlpr.loginshard.views.AccountPermissionsView;
import com.gamerevision.gwlpr.loginshard.views.FriendsListEndView;
import com.gamerevision.gwlpr.loginshard.views.StreamTerminatorView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the login process.
 * 
 * @author miracle444
 */
public class Login extends GenericShardlet
{
    
    private DatabaseConnectionProvider connectionProvider;
    private static Logger LOGGER = LoggerFactory.getLogger(Login.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("login shardlet initialized!");
    }
    
    
    @EventHandler
    public void accountLoginHandler(P004_AccountLoginAction action)
    {
        LOGGER.debug("got the account login packet");
        Session session = action.getSession();
     
        session.setAttribute("SyncCount", action.getLoginCount());
  
        CheckLoginInfo checkInfo = new CheckLoginInfo(connectionProvider, action);
        
        if (checkInfo.isValid())
        {
            LOGGER.debug("successfully logged in");
        
            
            LOGGER.debug("sending account gui settings");
            sendAction(AccountGuiInfoView.create(session));


            LOGGER.debug("sending friend list end");
            sendAction(FriendsListEndView.create(session));


            LOGGER.debug("sending account permissions");
            sendAction(AccountPermissionsView.create(session));


            LOGGER.debug("sending stream terminator");
            sendAction(StreamTerminatorView.create(session, 0));
        }
        else
        {
            sendAction(StreamTerminatorView.create(session, checkInfo.getErrorCode()));
        }
    }
    
    
    @EventHandler
    public void databaseConnectionProviderHandler(DatabaseConnectionProviderEvent event)
    {
        this.connectionProvider = event.getConnectionProvider();
    }
}
