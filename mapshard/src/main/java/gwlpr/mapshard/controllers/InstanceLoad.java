/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.actions.gameserver.stoc.P391_InstanceLoadSpawnPointAction;
import gwlpr.actions.gameserver.stoc.P230_UnknownAction;
import gwlpr.actions.gameserver.stoc.P206_UpdateSkillBarAction;
import gwlpr.actions.gameserver.stoc.P127_UnknownAction;
import gwlpr.actions.gameserver.stoc.P318_UpdateActiveWeaponsetAction;
import gwlpr.actions.gameserver.stoc.P310_UpdateGoldOnCharacterAction;
import gwlpr.actions.gameserver.stoc.P023_UnknownAction;
import gwlpr.actions.gameserver.stoc.P314_ItemStreamCreateAction;
import gwlpr.actions.gameserver.stoc.P126_UnknownAction;
import gwlpr.actions.gameserver.stoc.P393_UnknownAction;
import gwlpr.actions.gameserver.stoc.P221_UpdateFactionAction;
import gwlpr.actions.gameserver.stoc.P207_UnknownAction;
import gwlpr.actions.gameserver.stoc.P309_CreateInventoryPageAction;
import gwlpr.actions.gameserver.ctos.P129_UnknownAction;
import gwlpr.actions.gameserver.ctos.P137_UnknownAction;
import gwlpr.actions.gameserver.ctos.P138_UnknownAction;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import gwlpr.mapshard.ContextAttachment;
import gwlpr.mapshard.SessionAttachment;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.models.ClientLookupTable;
import gwlpr.mapshard.views.UpdateAttribPtsView;
import gwlpr.mapshard.views.UpdateGenericValueView;
import gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.models.GWVector;
import gwlpr.mapshard.models.MapData;
import gwlpr.mapshard.models.enums.GenericValue;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.models.enums.SpawnType;
import gwlpr.mapshard.views.EntitySpawningView;
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
    private MapData mapData;
    private SpawnType SpawningFaction;


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
        mapData = attach.getMapData();
    }


    @EventHandler
    public void onInstanceLoadRequestItems(P138_UnknownAction action)
    {
        // TODO: Create Items, update this to be dynamic!

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
        itemStreamTerminator.setUnknown2((short) mapData.getMapID());
        itemStreamTerminator.setUnknown3(0);

        session.send(itemStreamTerminator);
    }


    @EventHandler
    public void onInstanceLoadRequestSpawnPoint(P129_UnknownAction action)
    {
        LOGGER.debug("Got the instance load request spawn point packet");
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();

        // fetch the players postion (this was already initialized by the handshake controller,
        // when it instructed some other class to create the entity
        Entity et = attachment.getEntity();
        GWVector pos = et.get(Position.class).position;


        P391_InstanceLoadSpawnPointAction instanceLoadSpawnPoint = new P391_InstanceLoadSpawnPointAction();
        instanceLoadSpawnPoint.init(session);
        instanceLoadSpawnPoint.setMapFile(mapData.getMapFileHash());
        instanceLoadSpawnPoint.setPosition(pos.toFloatArray());
        instanceLoadSpawnPoint.setPlane(pos.getZPlane());
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

        // fetch the player entity..
        Entity et = attachment.getEntity();

        String name = et.get(Name.class).name;
        int agentID = et.get(AgentIdentifiers.class).agentID;
        int localID = et.get(AgentIdentifiers.class).localID;
        byte[] appear = et.get(Appearance.class).appearanceDump;
        GWVector pos = et.get(Position.class).position;
        float rotation = et.get(Direction.class).direction.toRotation();
        float speed = et.get(Movement.class).speed;


        P230_UnknownAction zoneBeginCharInfo = new P230_UnknownAction();
        zoneBeginCharInfo.init(session);
        zoneBeginCharInfo.setUnknown1(1886151033); // yalp
        session.send(zoneBeginCharInfo);


        UpdateAttribPtsView.send(session, agentID);
        UpdatePrivateProfessionsView.send(session, agentID);


        P206_UpdateSkillBarAction updateSkillbar = new P206_UpdateSkillBarAction();
        updateSkillbar.init(session);
        updateSkillbar.setAgentID(agentID);
        updateSkillbar.setSkillBar(new int[8]);
        updateSkillbar.setSkillBarPvPMask(new int[8]);
        updateSkillbar.setData3((byte) 1);

        session.send(updateSkillbar);


        UpdateGenericValueView.send(session, agentID, GenericValue.Energy, 20);
        UpdateGenericValueView.send(session, agentID, GenericValue.Health, 20);
        UpdateGenericValueView.send(session, agentID, GenericValue.EnergyRegen, 0.033F);
        UpdateGenericValueView.send(session, agentID, GenericValue.HealthRegen, 0);
        UpdateGenericValueView.send(session, agentID, GenericValue.PublicLevel, 1);


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


        // at this point we should spawn the player
        // all other players and such will be spawned automatically by the
        // spawning system when the player can see them.
        EntitySpawningView.spawnAgent(session, et);


        P023_UnknownAction fadeIntoMap = new P023_UnknownAction();
        fadeIntoMap.init(session);
        fadeIntoMap.setUnknown1(agentID);
        fadeIntoMap.setUnknown2(3);

        session.send(fadeIntoMap);


        // activate heart beat and ping and such
        attachment.setPlayerState(PlayerState.Playing);

        // unblind the player
        et.get(View.class).isBlind = false;
    }
}
