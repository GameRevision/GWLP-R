/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P074_NPCGeneralAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P075_NPCModelAction;
import com.realityshard.shardlet.Session;


/**
 * This view is used to spawn or update NPC's and their related data
 *
 * @author _rusty
 */
public class NPCView
{
    public void sendNPCGeneralStats(
            Session session,
            int localID,
            int fileID,
            int scale,
            int flags,
            int profession,
            int level,
            int texture,
            String name)
    {
        P074_NPCGeneralAction genStats = new P074_NPCGeneralAction();
        genStats.init(session);
        genStats.setLocalID(localID);
        genStats.setNPCFile(fileID);
        genStats.setScale(scale);
        genStats.setFlags(flags);
        genStats.setProfession((byte) profession);
        genStats.setLevel((byte) level);
        genStats.setTexture(texture);
        genStats.setName(name.toCharArray());
        genStats.setData2(0);

        session.send(genStats);
    }

    public void sendNPCModel(Session session, int localID, int[] modelHash)
    {
        P075_NPCModelAction npcModel = new P075_NPCModelAction();
        npcModel.init(session);
        npcModel.setLocalID(localID);
        npcModel.setModelFile(modelHash);

        session.send(npcModel);
    }
}
