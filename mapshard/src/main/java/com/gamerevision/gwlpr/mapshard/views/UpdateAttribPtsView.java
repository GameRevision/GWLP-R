/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P044_UpdateAttribPtsAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateAttributePoints action.
 *
 * @author miracle444
 */
public class UpdateAttribPtsView
{

    public static void send(Session session, int agentID)
    {
        P044_UpdateAttribPtsAction updateAttributePoints = new P044_UpdateAttribPtsAction();
        updateAttributePoints.init(session);
        updateAttributePoints.setAgentID(agentID);
        updateAttributePoints.setFreePts((byte) 0);
        updateAttributePoints.setMaxPts((byte) 0);

        session.send(updateAttributePoints);
    }
}
