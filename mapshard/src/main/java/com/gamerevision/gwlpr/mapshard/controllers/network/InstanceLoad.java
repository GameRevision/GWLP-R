/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers.network;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P129_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P137_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P138_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.*;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.views.UpdateAttribPtsView;
import com.gamerevision.gwlpr.mapshard.views.UpdateGenericValueView;
import com.gamerevision.gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet guides a client trough the instance load process.
 * 
 * TODO: review, clean-up, refactor me!
 * 
 * @author miracle444, _rusty
 */
public class InstanceLoad extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(InstanceLoad.class);
    
    private RemoteShardletContext loginShard;
    private DatabaseConnectionProvider db;
    private EntityManager entityManager;
    private ClientLookupTable clientlookup;
    private int mapId;
    
    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init InstanceLoad controller");
    }
    
    
    /**
     * Executes startup features, like storing database references etc.
     * 
     * @param event 
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        // this event indicates that all shardlets have been loaded (including
        // the startup shardlet) so we can safely use the context attachment now.
        
        // this is quite verbose, but luckily we only have to query for the 
        // necessary references once
        ContextAttachment attach = ((ContextAttachment) getShardletContext().getAttachment());
        
        loginShard = attach.getLoginShard();
        db = attach.getDatabaseProvider();
        entityManager = attach.getEntitySystem();
        clientlookup = attach.getClientLookup();
        mapId = attach.getMapId();
    }
    
    
    @EventHandler
    public void onInstanceLoadRequestItems(P138_UnknownAction action)
    {
        LOGGER.debug("Got the instance load request items packet");
        Session session = action.getSession();
        
        
        P314_ItemStreamCreateAction itemStreamCreate = new P314_ItemStreamCreateAction();
        itemStreamCreate.init(session);
        itemStreamCreate.setStreamID((short) 1);
        itemStreamCreate.setType((byte) 0);
        
        session.send(itemStreamCreate);
        
        
        P318_UpdateActiveWeaponsetAction updateActiveWeaponset = new P318_UpdateActiveWeaponsetAction();
        updateActiveWeaponset.init(session);
        updateActiveWeaponset.setStreamID((short) 1);
        updateActiveWeaponset.setSlot((byte) 0);
        
        session.send(updateActiveWeaponset);
        
        
        P309_CreateInventoryPageAction createInventoryPage = new P309_CreateInventoryPageAction();
        createInventoryPage.init(session);
        createInventoryPage.setItemStreamID((short) 1);
        createInventoryPage.setType((byte) 2);
        createInventoryPage.setSlots((byte) 9);
        createInventoryPage.setPageID((short) 16);
        createInventoryPage.setStorage((byte) 16);
        createInventoryPage.setAssociatedItem(0);
        
        session.send(createInventoryPage);
        
        
        P310_UpdateGoldOnCharacterAction updateGoldOnCharacter = new P310_UpdateGoldOnCharacterAction();
        updateGoldOnCharacter.init(session);
        updateGoldOnCharacter.setStreamID((short) 1);
        updateGoldOnCharacter.setGold(0);
        
        session.send(updateGoldOnCharacter);
        
        
        P393_UnknownAction itemStreamTerminator = new P393_UnknownAction();
        itemStreamTerminator.init(session);
        itemStreamTerminator.setUnknown1((byte) 0);
        itemStreamTerminator.setUnknown2((short) mapId);
        itemStreamTerminator.setUnknown3(0);
        
        session.send(itemStreamTerminator);
    }
    
    
    @EventHandler
    public void onInstanceLoadRequestSpawnPoint(P129_UnknownAction action)
    {
        LOGGER.debug("Got the instance load request spawn point packet");
        Session session = action.getSession();
        
        
        P391_InstanceLoadSpawnPointAction instanceLoadSpawnPoint = new P391_InstanceLoadSpawnPointAction();
        instanceLoadSpawnPoint.init(session);
        instanceLoadSpawnPoint.setMapFile(165811);
        instanceLoadSpawnPoint.setPosition(new float[] { -6558, -6010});
        instanceLoadSpawnPoint.setPlane((short) 0);
        instanceLoadSpawnPoint.setisCinematic((byte) 0);
        instanceLoadSpawnPoint.setData5((byte) 0);
        
        session.send(instanceLoadSpawnPoint);
    }
    
    
    @EventHandler
    public void onInstanceLoadRequestPlayerData(P137_UnknownAction action)
    {
        LOGGER.debug("Got the instance load request player data packet");
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        
        
        P230_UnknownAction zoneBeginCharInfo = new P230_UnknownAction();
        zoneBeginCharInfo.init(session);
        zoneBeginCharInfo.setUnknown1(1886151033);
        session.send(zoneBeginCharInfo);

        
        LOGGER.debug("Sending update attribute points");
        UpdateAttribPtsView.create(session);
        

        LOGGER.debug("Sending update private professions");
        UpdatePrivateProfessionsView.create(session);
        
        
        P206_UpdateSkillBarAction updateSkillbar = new P206_UpdateSkillBarAction();
        updateSkillbar.init(session);
        updateSkillbar.setAgentID(50);
        updateSkillbar.setSkillBar(new int[8]);
        updateSkillbar.setSkillBarPvPMask(new int[8]);
        updateSkillbar.setData3((byte) 1);
        
        session.send(updateSkillbar);
        
        
        // TODO: Fix agentIDs!
        LOGGER.debug("Sending the generic value for energy");
        UpdateGenericValueView.create(session, 50, UpdateGenericValueView.Type.Energy, 20);
        LOGGER.debug("Sending the  generic value for health");
        UpdateGenericValueView.create(session, 50, UpdateGenericValueView.Type.Health, 20);
        
        LOGGER.debug("Sending the generic value for energy regeneration");
        UpdateGenericValueView.create(session, 50, UpdateGenericValueView.Type.EnergyRegen, 0.033F);
        LOGGER.debug("Sending the generic value for health regeneration");
        UpdateGenericValueView.create(session, 50, UpdateGenericValueView.Type.HealthRegen, 0);
        
        LOGGER.debug("Sending the generic value for public level");
        UpdateGenericValueView.create(session, 50, UpdateGenericValueView.Type.PublicLevel, 1);
        
        P127_UnknownAction zoneDataPrepMapData = new P127_UnknownAction();
        zoneDataPrepMapData.init(session);
        zoneDataPrepMapData.setUnknown1(64);
        zoneDataPrepMapData.setUnknown2(128);
        zoneDataPrepMapData.setUnknown3(27);
        
        session.send(zoneDataPrepMapData);
        
        
        P126_UnknownAction zoneDataMapData = new P126_UnknownAction();
        zoneDataMapData.init(session);
        zoneDataMapData.setUnknown1(new int[1]);
        
        session.send(zoneDataMapData);
        
        
        P221_UpdateFactionAction updateFaction = new P221_UpdateFactionAction();
        updateFaction.init(session);
        updateFaction.setLevel(1);
        updateFaction.setMorale(100);
        
        session.send(updateFaction);
        
        
        P207_UnknownAction updateAvailableSkills = new P207_UnknownAction();
        updateAvailableSkills.init(session);
        updateAvailableSkills.setUnknown1(new int[] {0x000B0000, 0x0354FFFF, 0x043A043B, 0x00E8043A, 0x00000000, 0x00000000, 0x17000000} );
        
        session.send(updateAvailableSkills);

        
        P077_UnknownAction updateAgentAppearance = new P077_UnknownAction();
        updateAgentAppearance.init(session);
        updateAgentAppearance.setUnknown1(1);
        updateAgentAppearance.setUnknown2(50);
        updateAgentAppearance.setUnknown3(0);
        updateAgentAppearance.setUnknown6(0x3cbfa094);
        updateAgentAppearance.setUnknown7(attachment.getCharacterName().toCharArray());
        
        session.send(updateAgentAppearance);
        
        
        P021_UnknownAction spawnObject = new P021_UnknownAction();
        spawnObject.init(session);
        spawnObject.setUnknown1(50);
        spawnObject.setUnknown2((0x30 << 24) | 1);
        spawnObject.setUnknown3((byte) 1);
        spawnObject.setUnknown4((byte) 5);
        spawnObject.setUnknown5(new float[] { -6558, -6010});
        spawnObject.setUnknown6((short) 0);
        spawnObject.setUnknown7(new float[] { Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY});
        spawnObject.setUnknown8((byte) 1);
        spawnObject.setUnknown9(42);
        spawnObject.setUnknown10(Float.POSITIVE_INFINITY);
        spawnObject.setUnknown11(0x41400000);
        spawnObject.setUnknown12(1886151033);
        spawnObject.setUnknown18(new float[] { Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY});
        spawnObject.setUnknown19(new float[] { Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY});
        spawnObject.setUnknown22(new float[] { Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY});
        
        session.send(spawnObject);
  
        
        P023_UnknownAction fadeIntoMap = new P023_UnknownAction();
        fadeIntoMap.init(session);
        fadeIntoMap.setUnknown1(50);
        fadeIntoMap.setUnknown2(3);
        
        session.send(fadeIntoMap);
        
    }
}
