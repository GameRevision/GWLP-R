/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P171_UpdatePrivateProfessionsAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a UpdatePrivateProfessions action
 * 
 * @author miracle444
 */
public class UpdatePrivateProfessionsView
{

    public static P171_UpdatePrivateProfessionsAction create(Session session)
    {
        P171_UpdatePrivateProfessionsAction updatePrivateProfessions = new P171_UpdatePrivateProfessionsAction();
        updatePrivateProfessions.init(session);
        updatePrivateProfessions.setUnknown1(50);
        updatePrivateProfessions.setUnknown2((byte) 1);
        updatePrivateProfessions.setUnknown3((byte) 0);
        updatePrivateProfessions.setUnknown4((byte) 0);
        return updatePrivateProfessions;
    }
}