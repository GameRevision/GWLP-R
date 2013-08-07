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
import gwlpr.protocol.gameserver.outbound.P206_UpdateSkillBar;
import gwlpr.protocol.gameserver.outbound.P221_UpdateFaction;
import gwlpr.protocol.gameserver.outbound.P230_InstanceLoadAgentInfo;
import gwlpr.protocol.gameserver.outbound.P309_CreateInventoryPage;
import gwlpr.protocol.gameserver.outbound.P310_UpdateGoldOnCharacter;
import gwlpr.protocol.gameserver.outbound.P314_ItemStreamCreate;
import gwlpr.protocol.gameserver.outbound.P318_UpdateActiveWeaponset;
import gwlpr.protocol.gameserver.outbound.P391_InstanceLoadSpawnPoint;
import gwlpr.protocol.gameserver.outbound.P393_Unknown;
import io.netty.channel.Channel;


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
        
        channel.write(instanceLoadHead);
    }
    
    
    /**
     * Step 2
     */
    public static void charName(Channel channel, String name) 
    {
        P371_InstanceLoadCharName instanceLoadCharName = new P371_InstanceLoadCharName();
        instanceLoadCharName.init(channel);
        instanceLoadCharName.setCharName(name);
        
        channel.write(instanceLoadCharName);
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
        instanceLoadDistrictInfo.setDistrictAndRegion(((short)world.getInstanceNumber()) << 16 | world.getRegion().get());
        instanceLoadDistrictInfo.setLanguage(world.getLanguage().get());
        instanceLoadDistrictInfo.setMapID((short) world.getMap().getGameID());
        instanceLoadDistrictInfo.setIsExplorable((byte)(!world.isOutpost() ? 1 : 0));
        instanceLoadDistrictInfo.setIsObserver((byte) 0);
        
        channel.write(instanceLoadDistrictInfo);
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

        channel.write(itemStreamCreate);


        P318_UpdateActiveWeaponset updateActiveWeaponset = new P318_UpdateActiveWeaponset();
        updateActiveWeaponset.init(channel);
        updateActiveWeaponset.setStreamID((short) 1);
        updateActiveWeaponset.setSlot((byte) 0);

        channel.write(updateActiveWeaponset);


        P309_CreateInventoryPage createInventoryPage = new P309_CreateInventoryPage();
        createInventoryPage.init(channel);
        createInventoryPage.setItemStreamID((short) 1);
        createInventoryPage.setType((byte) 2);
        createInventoryPage.setSlots((byte) 9);
        createInventoryPage.setPageID((short) 16);
        createInventoryPage.setStorage((byte) 16);
        createInventoryPage.setAssociatedItem(0);

        channel.write(createInventoryPage);


        P310_UpdateGoldOnCharacter updateGoldOnCharacter = new P310_UpdateGoldOnCharacter();
        updateGoldOnCharacter.init(channel);
        updateGoldOnCharacter.setStreamID((short) 1);
        updateGoldOnCharacter.setGold(0);

        channel.write(updateGoldOnCharacter);


        P393_Unknown itemStreamTerminator = new P393_Unknown();
        itemStreamTerminator.init(channel);
        itemStreamTerminator.setUnknown1((byte) 0);
        itemStreamTerminator.setUnknown2((short) gameMapId);
        itemStreamTerminator.setUnknown3(0);

        channel.write(itemStreamTerminator);
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

        channel.write(instanceLoadSpawnPoint);
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
        
        
        // send this agent's spawn type
        SpawnType spawnType = entity.get(SpawnData.class).spawnType;
        
        P230_InstanceLoadAgentInfo beginAgentInfo = new P230_InstanceLoadAgentInfo();
        beginAgentInfo.init(channel);
        beginAgentInfo.setSpawnType(spawnType.getIntString());
        channel.write(beginAgentInfo);

        
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

        channel.write(zoneDataPrepMapData);

        P126_Unknown zoneDataMapData = new P126_Unknown();
        zoneDataMapData.init(channel);
        zoneDataMapData.setUnknown1(new P126_Unknown.NestedUnknown1[1]);

        channel.write(zoneDataMapData);


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

        channel.write(fadeIntoMap);
    }
}
