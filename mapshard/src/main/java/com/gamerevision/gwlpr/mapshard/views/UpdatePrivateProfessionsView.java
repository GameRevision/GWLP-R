/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P171_UpdatePrivProfessionsAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills a UpdatePrivateProfessions action
 * 
 * @author miracle444
 */
public class UpdatePrivateProfessionsView
{

    public static P171_UpdatePrivProfessionsAction create(Session session)
    {
        P171_UpdatePrivProfessionsAction updatePrivateProfessions = new P171_UpdatePrivProfessionsAction();
        updatePrivateProfessions.init(session);
        updatePrivateProfessions.setAgentID(50);
        updatePrivateProfessions.setPrimaryProf((byte) 1);
        updatePrivateProfessions.setSecondaryProf((byte) 0);
        updatePrivateProfessions.setisPvP((byte) 0);
        return updatePrivateProfessions;
    }
}