/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P004_AccountLoginAction;
import com.gamerevision.gwlpr.loginshard.views.AccountGuiSettingsView;
import com.gamerevision.gwlpr.loginshard.views.AccountPermissionsView;
import com.gamerevision.gwlpr.loginshard.views.FriendListEndView;
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
        
        
        session.setAttribute("SyncCount", action.getUnknown1());
        
        
        LOGGER.debug("sending account gui settings");
        sendAction(AccountGuiSettingsView.create(session));
        
        
        LOGGER.debug("sending friend list end");
        sendAction(FriendListEndView.create(session));
        
        
        LOGGER.debug("sending account permissions");
        sendAction(AccountPermissionsView.create(session));
        
        
        LOGGER.debug("sending stream terminator");
        sendAction(StreamTerminatorView.create(session));
    }
}
