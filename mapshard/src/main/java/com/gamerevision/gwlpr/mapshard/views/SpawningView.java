/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P021_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P022_DespawnAgentAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P077_UnknownAction;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.realityshard.shardlet.Session;


/**
 * Handles agent spawning actions.
 *
 * @author _rusty
 */
public class SpawningView
{

    /**
     * Spawns an agent. This is very incomplete...
     *
     * @param session
     * @param name
     * @param localID
     * @param agentID
     * @param appearance
     * @param position
     * @param rotation
     * @param speed
     */
    public static void spawnAgent(
            Session session,
            String name,
            int localID,
            int agentID,
            byte[] appearance,
            GWVector position,
            float rotation,
            float speed)
    {
        // send update agent appearance
        P077_UnknownAction updateAppear= new P077_UnknownAction();
        updateAppear.init(session);
        updateAppear.setUnknown1(localID);
        updateAppear.setUnknown2(agentID);
        updateAppear.setUnknown3(byteArrayToInt(appearance));
        updateAppear.setUnknown4((byte) 0);
        updateAppear.setUnknown5(0);
        updateAppear.setUnknown6(0x3CBFA094);
        updateAppear.setUnknown7(name.toCharArray());

        session.send(updateAppear);

        // send spawn agent packet
        P021_UnknownAction spawnAgent = new P021_UnknownAction();
        spawnAgent.init(session);
        spawnAgent.setUnknown1(agentID);
        spawnAgent.setUnknown2((0x30 << 24) | localID); // is this the localid?
        spawnAgent.setUnknown3((byte) 1);
        spawnAgent.setUnknown4((byte) 5);
        spawnAgent.setUnknown5(position.toFloatArray());
        spawnAgent.setUnknown6(position.getZPlane());
        spawnAgent.setUnknown7(new float[] {Float.POSITIVE_INFINITY, rotation});
        spawnAgent.setUnknown8((byte) 1);
        spawnAgent.setUnknown9(speed);
        spawnAgent.setUnknown10(Float.POSITIVE_INFINITY);
        spawnAgent.setUnknown11(0x41400000);
        spawnAgent.setUnknown12(1886151033); // "play" backwards
        spawnAgent.setUnknown18(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        spawnAgent.setUnknown19(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        spawnAgent.setUnknown22(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        
        session.send(spawnAgent);
    }


    /**
     * Despawn an agent.
     *
     * @param   session
     * @param   agentID
     */
    public static void despawnAgent(Session session, int agentID)
    {
        P022_DespawnAgentAction despawn = new P022_DespawnAgentAction();
        despawn.init(session);
        despawn.setAgentID(agentID);

        session.send(despawn);
    }


    /**
     * Helper.
     * (Taken from StackOverflow.)
     */
    private static int byteArrayToInt(byte[] b)
    {
        return   b[0] & 0xFF |
                (b[1] & 0xFF) << 8 |
                (b[2] & 0xFF) << 16 |
                (b[3] & 0xFF) << 24;
    }
}
