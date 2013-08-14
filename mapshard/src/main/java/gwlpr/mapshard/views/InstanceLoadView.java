/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.protocol.gameserver.outbound.P370_InstanceLoadHead;
import gwlpr.protocol.gameserver.outbound.P371_InstanceLoadCharName;
import gwlpr.protocol.gameserver.outbound.P395_InstanceLoadDistrictInfo;
import gwlpr.mapshard.models.WorldBean;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.GenericValue;
import gwlpr.mapshard.models.enums.SpawnType;
import gwlpr.protocol.gameserver.outbound.P023_FadeIntoMap;
import gwlpr.protocol.gameserver.outbound.P126_Unknown;
import gwlpr.protocol.gameserver.outbound.P127_Unknown;
import gwlpr.protocol.gameserver.outbound.P230_InstanceLoadAgentInfo;
import gwlpr.protocol.gameserver.outbound.P309_CreateInventoryPage;
import gwlpr.protocol.gameserver.outbound.P310_UpdateGoldOnCharacter;
import gwlpr.protocol.gameserver.outbound.P314_ItemStreamCreate;
import gwlpr.protocol.gameserver.outbound.P318_UpdateActiveWeaponset;
import gwlpr.protocol.gameserver.outbound.P391_InstanceLoadSpawnPoint;
import gwlpr.protocol.gameserver.outbound.P393_Unknown;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import java.nio.ByteOrder;


/**
 * Handshake actions.
 * 
 * @author miracle444, _rusty
 */
public class InstanceLoadView
{    
    
    /**
     * Step 1
     */
    public static void instanceHead(Channel channel)
    {
        P370_InstanceLoadHead instanceLoadHead = new P370_InstanceLoadHead();
        instanceLoadHead.init(channel);
        instanceLoadHead.setUnknown1((byte) 0x1F);
        instanceLoadHead.setUnknown2((byte) 0x1F);
        instanceLoadHead.setUnknown3((byte) 0);
        instanceLoadHead.setUnknown4((byte) 0);
        
        channel.writeAndFlush(instanceLoadHead);
    }
    
    
    /**
     * Step 2
     */
    public static void charName(Channel channel, String name) 
    {
        P371_InstanceLoadCharName instanceLoadCharName = new P371_InstanceLoadCharName();
        instanceLoadCharName.init(channel);
        instanceLoadCharName.setCharName(name);
        
        channel.writeAndFlush(instanceLoadCharName);
    }
    
    
    /**
     * Step 3
     * 
     * TODO: implement observer here
     */
    public static void districtInfo(Channel channel, int localID, WorldBean world)
    {
        P395_InstanceLoadDistrictInfo instanceLoadDistrictInfo = new P395_InstanceLoadDistrictInfo();
        instanceLoadDistrictInfo.init(channel);
        instanceLoadDistrictInfo.setCharAgent(localID);
        instanceLoadDistrictInfo.setDistrictNumber((world.isOutpost() ? world.getInstanceNumber() : 0));
        instanceLoadDistrictInfo.setRegion((world.isOutpost() ? world.getRegion().get() : 0));
        instanceLoadDistrictInfo.setLanguage((short)(world.isOutpost() ? world.getLanguage().get() : 0));
        instanceLoadDistrictInfo.setMapID((short)world.getMap().getGameID());
        instanceLoadDistrictInfo.setIsExplorable((byte)(!world.isOutpost() ? 1 : 0));
        instanceLoadDistrictInfo.setIsObserver((byte) 0);
        
        channel.writeAndFlush(instanceLoadDistrictInfo);
    }
    
    
    /**
     * TODO: Refactor this to be non-static
     * 
     * @param       channel
     * @param       gameMapId 
     */
    public static void sendItems(Channel channel, int gameMapId)
    {
        P314_ItemStreamCreate itemStreamCreate = new P314_ItemStreamCreate();
        itemStreamCreate.init(channel);
        itemStreamCreate.setStreamID((short) 1);
        itemStreamCreate.setIsHero((byte) 0);

        channel.writeAndFlush(itemStreamCreate);


        P318_UpdateActiveWeaponset updateActiveWeaponset = new P318_UpdateActiveWeaponset();
        updateActiveWeaponset.init(channel);
        updateActiveWeaponset.setStreamID((short) 1);
        updateActiveWeaponset.setSlot((byte) 0);

        channel.writeAndFlush(updateActiveWeaponset);


        P309_CreateInventoryPage createInventoryPage = new P309_CreateInventoryPage();
        createInventoryPage.init(channel);
        createInventoryPage.setItemStreamID((short) 1);
        createInventoryPage.setType((byte) 2);
        createInventoryPage.setSlots((byte) 9);
        createInventoryPage.setPageID((short) 16);
        createInventoryPage.setStorage((byte) 16);
        createInventoryPage.setAssociatedItem(0);

        channel.writeAndFlush(createInventoryPage);


        P310_UpdateGoldOnCharacter updateGoldOnCharacter = new P310_UpdateGoldOnCharacter();
        updateGoldOnCharacter.init(channel);
        updateGoldOnCharacter.setStreamID((short) 1);
        updateGoldOnCharacter.setGold(0);

        channel.writeAndFlush(updateGoldOnCharacter);


        P393_Unknown itemStreamTerminator = new P393_Unknown();
        itemStreamTerminator.init(channel);
        itemStreamTerminator.setUnknown1((byte) 0);
        itemStreamTerminator.setUnknown2((short) gameMapId);
        itemStreamTerminator.setUnknown3(0);

        channel.writeAndFlush(itemStreamTerminator);
    }
    
    
    /**
     * Send the spawn point for a certain client
     * 
     * @param       channel
     * @param       pos
     * @param       mapFileHash 
     */
    public static void sendSpawnPoint(Channel channel, WorldPosition pos, int mapFileHash)
    {
        P391_InstanceLoadSpawnPoint instanceLoadSpawnPoint = new P391_InstanceLoadSpawnPoint();
        instanceLoadSpawnPoint.init(channel);
        instanceLoadSpawnPoint.setMapFile(mapFileHash);
        instanceLoadSpawnPoint.setPosition(pos);
        instanceLoadSpawnPoint.setPlane(pos.getZPlane());
        instanceLoadSpawnPoint.setIsCinematic((byte) 0);
        instanceLoadSpawnPoint.setUnknown1((byte) 0);

        channel.writeAndFlush(instanceLoadSpawnPoint);
    }
    
    
    /**
     * Send the instance load agent data (for this agent basically, agent stuff for other
     * agents will be sent by the systems later on)
     * 
     * @param       channel
     * @param       entity 
     */
    public static void sendAgentData(Channel channel, Entity entity)
    {
        // we will need the ID for several other packets:
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        
        sendGuiData(channel);
        
        // send this agent's spawn type
        SpawnType spawnType = entity.get(SpawnData.class).spawnType;
        
        P230_InstanceLoadAgentInfo beginAgentInfo = new P230_InstanceLoadAgentInfo();
        beginAgentInfo.init(channel);
        beginAgentInfo.setSpawnType(spawnType.getIntString());
        channel.writeAndFlush(beginAgentInfo);

        
        // set some initial values
        EntityUpdateAttributesView.sendInitial(channel, entity);
        EntityUpdatePrivateProfessionsView.send(channel, entity);

        EntityUpdateSkillsView.updateSkillbar(channel, entity);

        
        // some generic values...        
        UpdateGenericValueView.send(channel, agentID, GenericValue.Energy, 20);
        UpdateGenericValueView.send(channel, agentID, GenericValue.Health, 20);
        UpdateGenericValueView.send(channel, agentID, GenericValue.EnergyRegen, 0.033F);
        UpdateGenericValueView.send(channel, agentID, GenericValue.HealthRegen, 0);
        UpdateGenericValueView.send(channel, agentID, GenericValue.PublicLevel, 1);


        // more static stuff... we still have figured out how map data is structured
        P127_Unknown zoneDataPrepMapData = new P127_Unknown();
        zoneDataPrepMapData.init(channel);
        zoneDataPrepMapData.setUnknown1(64);
        zoneDataPrepMapData.setUnknown2(128);
        zoneDataPrepMapData.setUnknown3(27);

        channel.writeAndFlush(zoneDataPrepMapData);

        P126_Unknown zoneDataMapData = new P126_Unknown();
        zoneDataMapData.init(channel);
        zoneDataMapData.setUnknown1(new P126_Unknown.NestedUnknown1[] { new P126_Unknown.NestedUnknown1() });

        channel.writeAndFlush(zoneDataMapData);


        // send the faction values
        EntityUpdateFactionPtsView.sendFactionData(channel, entity);


        EntityUpdateSkillsView.updateAvailableSkills(channel, entity);


        // at this point we should spawn the player
        // all other players and such will be spawned automatically by the
        // spawning system when the player can see them.
        EntitySpawningView.spawnAgent(channel, entity);


        P023_FadeIntoMap fadeIntoMap = new P023_FadeIntoMap();
        fadeIntoMap.init(channel);
        fadeIntoMap.setAgentID(agentID);
        fadeIntoMap.setUnknown1(3);

        channel.writeAndFlush(fadeIntoMap);
    }
    
    
    public static void sendGuiData(Channel channel)
    {
        // TODO fixme!
        
        short[] data = new short[] {
            0x66, 0xE4, 0xA6, 0xFE, 0xE7, 0x5D, 0x0C, 0x66,
            0x3A, 0x3F, 0xFB, 0x11, 0x50, 0xE3, 0xFC, 0x0D, 0xA0, 0xEE, 0x06, 0x7B,
            0x74, 0xB3, 0xDB, 0xFD, 0xFF, 0x0D, 0xC8, 0xDF, 0x22, 0x58, 0xBE, 0xD7,
            0xBF, 0x9C, 0xB3, 0x8E, 0xF7, 0xFB, 0xFE, 0x63, 0x4C, 0x7C, 0xDB, 0xB4,
            0x8D, 0x4C, 0x09, 0x55, 0x43, 0xDD, 0x00, 0x18, 0x00, 0x07, 0x1B, 0x46,
            0xEC, 0x6F, 0x5A, 0x3D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x94, 0x97, 0xDF, 0x03, 0x71, 0x60, 0x8B, 0x57,
            0x01, 0x01, 0x20, 0x0C, 0x82, 0x50, 0xD3, 0x00, 0x04, 0xC0, 0x8C, 0x2A,
            0x56, 0x81, 0x40, 0x41, 0x10, 0x20, 0xE8, 0xA4, 0x00, 0x00, 0x00, 0x5C,
            0x9D, 0x21, 0x38, 0x42, 0x40, 0x00, 0x60, 0x10, 0x68, 0x01, 0x00, 0x2C,
            0x4A, 0x29, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0x90, 0x00, 0x28,
            0xCC, 0xE2, 0x76, 0xC0, 0x3A, 0x2E, 0x3A, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x80, 0x10, 0x05, 0x11, 0x39, 0xCC, 0x78, 0x00, 0x9A, 0x93,
            0x58, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xDF, 0xB2, 0x25, 0xD0,
            0x2F, 0xFD, 0xE7, 0x85, 0xB8, 0xBD, 0xF8, 0x30, 0x20, 0x16, 0xA3, 0x48,
            0x2B, 0x00, 0x00, 0x00, 0x60, 0xC6, 0xCE, 0x44, 0x00, 0x00, 0xC0, 0x0E,
            0x22, 0x00, 0x00, 0x85, 0x71, 0xD7, 0x71, 0xA1, 0xDC, 0x6B, 0x7B, 0x01,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x58, 0x09, 0x5C, 0x18, 0x03, 0x00, 0x00, 0x00, 0x00, 0xE5, 0xB3, 0x06,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, 0x59, 0x50, 0x00, 0x00, 0x00,
            0x00, 0xA0, 0x4C, 0x65, 0x01, 0x00, 0x00, 0x40, 0x0D, 0x00, 0x01, 0x00,
            0xFA, 0x4E, 0xAD, 0x0F
        };
        
        ByteBuf buf = channel.alloc().buffer(2 + 2 + data.length).order(ByteOrder.LITTLE_ENDIAN);
        
        // write the header
        buf.writeShort(18);
        
        // write length of the data in ints
        buf.writeShort(data.length / 4);
        
        for (int i = 0; i < data.length; i++) 
        {
            buf.writeByte(data[i]);
        }
        
        channel.writeAndFlush(buf);
    }
}
