/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P017_AccountPermissionsAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the AccountPermission action.
 * 
 * @author miracle444
 */
public class AccountPermissionsView
{

    public static P017_AccountPermissionsAction create(Session session)
    {
        P017_AccountPermissionsAction accountPermissions = new P017_AccountPermissionsAction();
        accountPermissions.init(session);
        accountPermissions.setLoginCount(((SessionAttachment) session.getAttachment()).getLoginCount());
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
        return accountPermissions;
    }
}
