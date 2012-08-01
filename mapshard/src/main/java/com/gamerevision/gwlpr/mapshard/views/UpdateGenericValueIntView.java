/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P147_UpdateGenericValueIntAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateGenericValueInt action.
 * 
 * @author miracle444
 */
public class UpdateGenericValueIntView
{

    public static P147_UpdateGenericValueIntAction create(Session session, int valueId, int value)
    {
        P147_UpdateGenericValueIntAction updateGenericValueInt = new P147_UpdateGenericValueIntAction();
        updateGenericValueInt.init(session);
        updateGenericValueInt.setValueID(valueId);
        updateGenericValueInt.setAgentID(50);
        updateGenericValueInt.setValue(value);
        return updateGenericValueInt;
    }
}
