/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P150_UpdateGenericValueFloatAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateGenericValueFloat action.
 * 
 * @author miracle444
 */
public class UpdateGenericValueFloatView
{

    public static P150_UpdateGenericValueFloatAction create(Session session, int valueId, float value)
    {
        P150_UpdateGenericValueFloatAction updateGenericValueFloat = new P150_UpdateGenericValueFloatAction();
        updateGenericValueFloat.init(session);
        updateGenericValueFloat.setValueID(valueId);
        updateGenericValueFloat.setAgentID(50);
        updateGenericValueFloat.setValue(value);
        return updateGenericValueFloat;
    }
}
