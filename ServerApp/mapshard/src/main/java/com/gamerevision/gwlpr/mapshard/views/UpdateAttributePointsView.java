/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P044_UpdateAttributePointsAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the UpdateAttributePoints action.
 * 
 * @author miracle444
 */
public class UpdateAttributePointsView
{

    public static P044_UpdateAttributePointsAction create(Session session)
    {
        P044_UpdateAttributePointsAction updateAttributePoints = new P044_UpdateAttributePointsAction();
        updateAttributePoints.init(session);
        updateAttributePoints.setUnknown1(50);
        updateAttributePoints.setUnknown2((byte) 0);
        updateAttributePoints.setUnknown3((byte) 0);
        return updateAttributePoints;
    }
}
