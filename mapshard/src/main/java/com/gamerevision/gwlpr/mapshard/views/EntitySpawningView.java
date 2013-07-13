/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P021_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P022_DespawnAgentAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P074_NPCGeneralAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P075_NPCModelAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P077_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P143_UpdateNpcNameAction;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWString;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.SpawnType;
import com.realityshard.shardlet.Session;


/**
 * Handles agent spawning actions.
 * 
 * TODO: we should make sure the entity actually has all the required components
 * before trying to spawn it.
 *
 * @author _rusty
 */
public class EntitySpawningView
{

    /**
     * Spawns an agent. This is very incomplete...
     * 
     * TODO refactor me!
     *
     * @param       session
     * @param       entity
     */
    public static void spawnAgent(Session session,  Entity entity)
    {
        // retrieve entity-info
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        GWVector pos = entity.get(Position.class).position;
        GWVector dir = entity.get(Direction.class).direction;
        Movement move = entity.get(Movement.class);
        FactionData faction = entity.get(FactionData.class);
        
        byte factionColor = 0x20; // TODO: are these the color of the names of npcs and player etc.?
        
        if (faction.spawnType == SpawnType.Player)
        {
           sendPlayerAppearance(session, entity);
        }
        else if (faction.spawnType == SpawnType.NPC || faction.spawnType == SpawnType.Ally)
        {
            sendNPCGeneralPackets(session, entity);
        }
        else
        {
            // NOTE THIS BUG: hardcoded: we currently only spawn npcs and players automatically.
            return;
        }

        // send spawn agent packet
        P021_UnknownAction spawnAgent = new P021_UnknownAction();
        spawnAgent.init(session);
        spawnAgent.setUnknown1(agentIDs.agentID);
        spawnAgent.setUnknown2((faction.factionColor << 24) | agentIDs.localID); // is this the localid?
        spawnAgent.setUnknown3((byte) 1);
        spawnAgent.setUnknown4((byte) 9);//5);
        spawnAgent.setUnknown5(pos.toFloatArray());
        spawnAgent.setUnknown6(pos.getZPlane());
        spawnAgent.setUnknown7(new float[] {Float.POSITIVE_INFINITY, dir.toRotation()});
        spawnAgent.setUnknown8((byte) 1);
        spawnAgent.setUnknown9(move.speed);
        spawnAgent.setUnknown10(1F);//Float.POSITIVE_INFINITY);
        spawnAgent.setUnknown11(0x41400000);
        spawnAgent.setUnknown12(faction.spawnType.getIntString()); // "play" backwards
        spawnAgent.setUnknown18(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        spawnAgent.setUnknown19(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        spawnAgent.setUnknown22(new float[] {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY});
        
        session.send(spawnAgent);
    }


    /**
     * Despawn an agent.
     *
     * @param   session
     * @param   entity
     */
    public static void despawnAgent(Session session, Entity entity)
    {
        // retrieve some NPC data...
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        
        P022_DespawnAgentAction despawn = new P022_DespawnAgentAction();
        despawn.init(session);
        despawn.setAgentID(agentIDs.agentID);

        session.send(despawn);
    }
    
    
    /**
     * Necessary for NPC spawning
     * 
     * @param session
     * @param entity 
     */
    public static void sendNPCGeneralPackets(Session session, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        NPCData npc = entity.get(NPCData.class);
        CharData charData = entity.get(CharData.class);
        
        // send the messages...
        P074_NPCGeneralAction genStats = new P074_NPCGeneralAction();
        genStats.init(session);
        genStats.setLocalID(agentIDs.localID);
        genStats.setNPCFile(npc.fileID);
        genStats.setScale(npc.scale << 24);
        genStats.setFlags(npc.flags);
        genStats.setProfession((byte) charData.primary.ordinal());
        genStats.setLevel((byte) charData.level);
        genStats.setTexture(npc.texture);
        genStats.setName(npc.hashedName.toCharArray());
        genStats.setData2(0);

        session.send(genStats);
        
        
        P075_NPCModelAction npcModel = new P075_NPCModelAction();
        npcModel.init(session);
        npcModel.setLocalID(agentIDs.localID);
        npcModel.setModelFile(npc.modelHashes);

        session.send(npcModel);
        
        // if the NPC has got a special name, send it now:
        if (!"".equals(name) && !"NoName".equals(name))
        {
            sendNPCName(session, entity);
        }
    }
    
    
    /**
     * Necessary for updating an NPCs name
     * 
     * @param session
     * @param entity 
     */
    public static void sendNPCName(Session session, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        
        // send the packet
        P143_UpdateNpcNameAction updNPCName = new P143_UpdateNpcNameAction();
        updNPCName.init(session);
        updNPCName.setAgentID(agentIDs.agentID);
        updNPCName.setName(GWString.formatChat(name).toCharArray());
        
        session.send(updNPCName);
    }
    
    
    /**
     * Send right before spawning a player.
     * 
     * @param session
     * @param entity 
     */
    public static void sendPlayerAppearance(Session session, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        Appearance appearance = entity.get(Appearance.class);
        
        // send update agent appearance
        P077_UnknownAction updateAppear= new P077_UnknownAction();
        updateAppear.init(session);
        updateAppear.setUnknown1(agentIDs.localID);
        updateAppear.setUnknown2(agentIDs.agentID);
        updateAppear.setUnknown3(byteArrayToInt(appearance.appearanceDump));
        updateAppear.setUnknown4((byte) 0);
        updateAppear.setUnknown5(0);
        updateAppear.setUnknown6(0x3CBFA094);
        updateAppear.setUnknown7(name.toCharArray());

        session.send(updateAppear);
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
