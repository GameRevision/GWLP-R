/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P021_SpawnAgent;
import gwlpr.protocol.gameserver.outbound.P022_DespawnAgent;
import gwlpr.protocol.gameserver.outbound.P074_NPCGeneral;
import gwlpr.protocol.gameserver.outbound.P075_NPCModel;
import gwlpr.protocol.gameserver.outbound.P077_UpdateAppearance;
import gwlpr.protocol.gameserver.outbound.P143_UpdateNpcName;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.models.GWString;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.SpawnType;
import gwlpr.protocol.util.Vector2;
import io.netty.channel.Channel;


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
     * @param       channel
     * @param       entity
     */
    public static void spawnAgent(Channel channel,  Entity entity)
    {
        // retrieve entity-info
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        WorldPosition pos = entity.get(Position.class).position;
        Direction direction = entity.get(Direction.class);
        Vector2 dir = direction.direction;
        float rotation = direction.rotation;
        Movement move = entity.get(Movement.class);
        SpawnData faction = entity.get(SpawnData.class);
        
        if (faction.spawnType == SpawnType.Player)
        {
           sendPlayerAppearance(channel, entity);
        }
        else if (faction.spawnType == SpawnType.NPC || faction.spawnType == SpawnType.Ally)
        {
            sendNPCGeneralPackets(channel, entity);
        }
        else
        {
            // NOTE THIS BUG: hardcoded: we currently only spawn npcs and players automatically.
            return;
        }

        // send spawn agent packet
        P021_SpawnAgent spawnAgent = new P021_SpawnAgent();
        spawnAgent.init(channel);
        spawnAgent.setAgentID(agentIDs.agentID);
        spawnAgent.setFacColorLocalID((faction.factionColor << 24) | agentIDs.localID); // is this the localid?
        spawnAgent.setUnknown1((byte) 1);
        spawnAgent.setUnknown2((byte) 9);//5);
        spawnAgent.setPositionVector(pos);
        spawnAgent.setPositionPlane(pos.getZPlane());
        spawnAgent.setDirectionRotation(new Vector2(Float.POSITIVE_INFINITY, rotation));
        spawnAgent.setUnknown3((short)1);
        spawnAgent.setMoveSpeed(move.speed);
        spawnAgent.setUnknown4(1F);//Float.POSITIVE_INFINITY);
        spawnAgent.setUnknown5(0x41400000);
        spawnAgent.setSpawnType(faction.spawnType.getIntString()); // "play" backwards
        spawnAgent.setUnknown11(new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        spawnAgent.setUnknown12(new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        spawnAgent.setUnknown15(new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        
        channel.writeAndFlush(spawnAgent);
    }


    /**
     * Despawn an agent.
     *
     * @param   channel
     * @param   entity
     */
    public static void despawnAgent(Channel channel, Entity entity)
    {
        // retrieve some NPC data...
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        
        P022_DespawnAgent despawn = new P022_DespawnAgent();
        despawn.init(channel);
        despawn.setAgentID(agentIDs.agentID);

        channel.writeAndFlush(despawn);
    }
    
    
    /**
     * Necessary for NPC spawning
     * 
     * @param channel
     * @param entity 
     */
    public static void sendNPCGeneralPackets(Channel channel, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        NPCData npc = entity.get(NPCData.class);
        CharData charData = entity.get(CharData.class);
        
        // send the messages...
        P074_NPCGeneral genStats = new P074_NPCGeneral();
        genStats.init(channel);
        genStats.setLocalID(agentIDs.localID);
        genStats.setNPCFile(npc.fileID);
        genStats.setScale(npc.scale << 24);
        genStats.setFlags(npc.flags);
        genStats.setProfession((byte) charData.primary.ordinal());
        genStats.setLevel((byte) charData.level);
        genStats.setTexture(npc.texture);
        genStats.setName(npc.hashedName);
        genStats.setUnknown1(0);

        channel.writeAndFlush(genStats);
        
        // prepare NPC model file hash
        P075_NPCModel.NestedModelFile[] modelFile = new P075_NPCModel.NestedModelFile[npc.modelHashes.length];
        for (int i = 0; i < npc.modelHashes.length; i++) 
        {
            modelFile[i].setUnknown1(npc.modelHashes[i]);
        }
        
        P075_NPCModel npcModel = new P075_NPCModel();
        npcModel.init(channel);
        npcModel.setLocalID(agentIDs.localID);
        npcModel.setModelFile(modelFile);

        channel.writeAndFlush(npcModel);
        
        // if the NPC has got a special name, send it now:
        if (!"".equals(name) && !"NoName".equals(name))
        {
            sendNPCName(channel, entity);
        }
    }
    
    
    /**
     * Necessary for updating an NPCs name
     * 
     * @param channel
     * @param entity 
     */
    public static void sendNPCName(Channel channel, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        
        // send the packet
        P143_UpdateNpcName updNPCName = new P143_UpdateNpcName();
        updNPCName.init(channel);
        updNPCName.setAgentID(agentIDs.agentID);
        updNPCName.setName(GWString.formatChat(name));
        
        channel.writeAndFlush(updNPCName);
    }
    
    
    /**
     * Send right before spawning a player.
     * 
     * @param channel
     * @param entity 
     */
    public static void sendPlayerAppearance(Channel channel, Entity entity)
    {
        // retrieve some NPC data...
        String name = entity.get(Name.class).name;
        AgentIdentifiers agentIDs = entity.get(AgentIdentifiers.class);
        PlayerAppearance appearance = entity.get(PlayerAppearance.class);
        
        // send update agent appearance
        P077_UpdateAppearance updateAppear= new P077_UpdateAppearance();
        updateAppear.init(channel);
        updateAppear.setAgentID(agentIDs.localID);
        updateAppear.setLocalID(agentIDs.agentID);
        updateAppear.setAppearanceDump(byteArrayToInt(appearance.appearanceDump));
        updateAppear.setUnknown1((byte) 0);
        updateAppear.setUnknown2(0);
        updateAppear.setUnknown3(0x3CBFA094);
        updateAppear.setName(name);

        channel.writeAndFlush(updateAppear);
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
