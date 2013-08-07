/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.database.entities.Spawnpoint;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.entityfactories.CharacterFactory;
import gwlpr.mapshard.models.HandleRegistryNotificationDecorator;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.WorldBean;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.views.InstanceLoadView;
import gwlpr.protocol.gameserver.inbound.P129_InstanceLoadRequestSpawnPoint;
import gwlpr.protocol.gameserver.inbound.P137_InstanceLoadRequestAgentData;
import gwlpr.protocol.gameserver.inbound.P138_InstanceLoadRequestItems;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;


/**
 * This shardlet guides a client trough the instance load process.
 *
 * TODO: review, clean-up, refactor me!
 *
 * @author miracle444, _rusty
 */
public class InstanceLoad
{

    private static Logger LOGGER = LoggerFactory.getLogger(InstanceLoad.class);

    private WorldBean world;
    private EntityManager entityManager;


    /**
     * Constructor.
     * 
     * @param       world
     * @param       entityManager 
     */
    public InstanceLoad(WorldBean world, EntityManager entityManager)
    {
        this.world = world;
        this.entityManager = entityManager;
    }
    
    
    /**
     * Event handler.
     * Some other component registered a client with the local client registry
     * 
     * This means we can start the instance load process now, sending the first packets.
     * 
     * @param event 
     */
    @Event.Handler
    public void onClientRegistered(HandleRegistryNotificationDecorator.Registered<ClientBean> event)
    {
        if (event.getHandle().get().getPlayerState() == PlayerState.LoadingInstance)
        {
            LOGGER.debug("Starting instance load.");
            
            ClientBean client = event.getHandle().get();
            
            // create a new entity of the character of this client
            // NOTE THAT FOR IDENTIFICATION PURPOSES, WE USE THE CLIENTS UID FOR
            // ITS CHARACTER ENTITY AS WELL!
            WorldPosition pos = getRandomSpawn();
            Entity player = CharacterFactory.createCharacter(event.getHandle().getUid(), client.getCharacter(), pos, entityManager);

            client.setEntity(player);
            client.setAgentIDs(player.get(AgentIdentifiers.class));

            // using the attachment now looks a bit ugly,
            // because we use the components here directly
            // (this should not happen with other components!)
            InstanceLoadView.instanceHead(client.getChannel());
            InstanceLoadView.charName(client.getChannel(), client.getCharacter().getName());
            InstanceLoadView.districtInfo(client.getChannel(), client.getAgentIDs().localID, world);
        }
    }


    @Event.Handler
    public void onInstanceLoadRequestItems(P138_InstanceLoadRequestItems action)
    {
        // TODO: Create Items, update this to be dynamic!

        LOGGER.debug("Got the instance load request items packet");
        
        InstanceLoadView.sendItems(action.getChannel(), world.getMap().getGameID());
    }


    @Event.Handler
    public void onInstanceLoadRequestSpawnPoint(P129_InstanceLoadRequestSpawnPoint action)
    {
        LOGGER.debug("Got the instance load request spawn point packet");
        
        ClientBean client = ClientBean.get(action.getChannel());

        // fetch the players postion (this was already initialized by the handshake controller,
        // when it instructed some other class to create the entity
        Entity et = client.getEntity();
        WorldPosition pos = et.get(Position.class).position;

        InstanceLoadView.sendSpawnPoint(client.getChannel(), pos, world.getMap().getHash());
    }


    @Event.Handler
    public void onInstanceLoadRequestAgentData(P137_InstanceLoadRequestAgentData action)
    {
        LOGGER.debug("Got the instance load request player data packet");
        
        ClientBean client = ClientBean.get(action.getChannel());

        // fetch the player entity..
        Entity et = client.getEntity();

        InstanceLoadView.sendAgentData(client.getChannel(), et);

        // activate heart beat and ping and such
        client.setPlayerState(PlayerState.Playing);

        // unblind the player
        et.get(View.class).isBlind = false;
    }
    
    
    /**
     * Pick a random spawnpoint from the world, then pick a random position near
     * this spawn in the radius the world's map object defines.
     * 
     * @return      The spawnpoint as WorldPosition.
     */
    private WorldPosition getRandomSpawn()
    {        
        // pick a random spawnpoint
        Iterator<Spawnpoint> it = world.getMap().getSpawnpointCollection().iterator();
        for (int i = 0; i < ((int)Math.random() * world.getMap().getSpawnpointCollection().size() -1); i++) {
            it.next();
        }
        Spawnpoint spawn = it.next();
        int radius = spawn.getRadius();
        
        // pick a random position in a certain radius
        float t = (float)(2 * Math.PI * Math.random());
        float r = (float)(radius * -1 + (Math.random() * radius));
        WorldPosition pos = new WorldPosition(
                (float)(spawn.getX() + (r * Math.cos(t))), 
                (float)(spawn.getY() + (r * Math.sin(t))), 
                spawn.getPlane());
        
        return pos;
    }
}
