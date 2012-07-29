/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P022_AccountGuiInfoAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the AccountGuiSettings action.
 * 
 * @author miracle444
 */
public class AccountGuiInfoView
{

    public static P022_AccountGuiInfoAction create(Session session)
    {
        P022_AccountGuiInfoAction accountGuiSettings = new P022_AccountGuiInfoAction();
        accountGuiSettings.init(session);
        accountGuiSettings.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
        return accountGuiSettings;
    }
}
