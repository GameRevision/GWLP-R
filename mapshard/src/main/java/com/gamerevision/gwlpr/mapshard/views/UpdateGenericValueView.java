/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P147_UpdateGenericValueIntAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P148_UpdateGenericValueTargetAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P150_UpdateGenericValueFloatAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P151_UpdateGenericValueModifierAction;
import com.gamerevision.gwlpr.mapshard.models.enums.GenericValue;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateGenericValue action.
 *
 * This is used to send different packets, depending on the input.
 * See the nested enum for the known values
 *
 * @author miracle444, _rusty
 */
public class UpdateGenericValueView
{
    public static void send(Session session, int agentID, GenericValue valueID, int value)
    {
        P147_UpdateGenericValueIntAction updateGenericValueInt = new P147_UpdateGenericValueIntAction();
        updateGenericValueInt.init(session);
        updateGenericValueInt.setValueID(valueID.ordinal());
        updateGenericValueInt.setAgentID(agentID);
        updateGenericValueInt.setValue(value);

        session.send(updateGenericValueInt);
    }

    public static void send(Session session, int agentID, GenericValue valueID, float value)
    {
        P150_UpdateGenericValueFloatAction updateGenericValueFloat = new P150_UpdateGenericValueFloatAction();
        updateGenericValueFloat.init(session);
        updateGenericValueFloat.setValueID(valueID.ordinal());
        updateGenericValueFloat.setAgentID(agentID);
        updateGenericValueFloat.setValue(value);

        session.send(updateGenericValueFloat);
    }

    public static void send(Session session, int targetAgentID, int casterAgentID, GenericValue valueID, int value)
    {
        P148_UpdateGenericValueTargetAction updateGenericValueTarget = new P148_UpdateGenericValueTargetAction();
        updateGenericValueTarget.init(session);
        updateGenericValueTarget.setValueID(valueID.ordinal());
        updateGenericValueTarget.setTarget(targetAgentID);
        updateGenericValueTarget.setCaster(casterAgentID);
        updateGenericValueTarget.setValue(value);

        session.send(updateGenericValueTarget);
    }

    public static void send(Session session, int targetAgentID, int casterAgentID, GenericValue valueID, float value)
    {
        P151_UpdateGenericValueModifierAction updateGenericValueModifier = new P151_UpdateGenericValueModifierAction();
        updateGenericValueModifier.init(session);
        updateGenericValueModifier.setValueID(valueID.ordinal());
        updateGenericValueModifier.setTarget(targetAgentID);
        updateGenericValueModifier.setCaster(casterAgentID);
        //updateGenericValueModifier.setValue(value); // TODO: fixme

        session.send(updateGenericValueModifier);
    }
}
