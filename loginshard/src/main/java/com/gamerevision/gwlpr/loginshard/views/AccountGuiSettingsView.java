/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P022_AccountGuiSettingsAction;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the AccountGuiSettings action.
 * 
 * @author miracle444
 */
public class AccountGuiSettingsView
{

    public static P022_AccountGuiSettingsAction create(Session session)
    {
        P022_AccountGuiSettingsAction accountGuiSettings = new P022_AccountGuiSettingsAction();
        accountGuiSettings.init(session);
        accountGuiSettings.setUnknown1((int) session.getAttribute("SyncCount"));
        return accountGuiSettings;
    }
}
