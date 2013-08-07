/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.systems;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.GenericSystem;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.events.CanSeeEvent;
import gwlpr.mapshard.events.LostSightEvent;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.views.EntitySpawningView;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;
import realityshard.container.util.HandleRegistry;


/**
 * This system handles agent spawning, based on agents can see each other.
 *
 * Basically, if agent A can see agent B (we wait for the event), we need to
 * spawn agent B for agent A, if A is a network client.
 *
 * TODO: we might need to send extra information with B's stuff like guild info etc.
 * TODO: is this a controller?
 *
 * @author _rusty
 */
public class SpawningSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = 0; // doesnt need to be updated
    private final HandleRegistry<ClientBean> clientRegistry;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       clientRegistry
     */
    public SpawningSystem(EventAggregator aggregator, HandleRegistry<ClientBean> clientRegistry)
    {
        super(aggregator, UPDATEINTERVAL);
        this.clientRegistry = clientRegistry;
    }


    /**
     * This is invoked periodically. (Or never, depending on the interval)
     *
     * @param timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
    }


    /**
     * Event handler.
     * This spawns an entity if the one that can see it is a network client.
     *
     * @param canSee
     */
    @Event.Handler
    public void onEntityCanSeeOther(CanSeeEvent canSee)
    {
        // check if this entity is a network client
        ClientBean client = clientRegistry.getObj(canSee.getThisEntity().getUuid());
        if (client == null) { return; }

        Entity et = canSee.getOtherEntity();

        EntitySpawningView.spawnAgent(client.getChannel(), et);
    }


    /**
     * Event handler.
     * This despawns an entity if this entity lost sight of it and is a network
     * client.
     *
     * @param lostSight
     */
    @Event.Handler
    public void onEntityLostSight(LostSightEvent lostSight)
    {
        // check if this entity is a network client
        ClientBean client = clientRegistry.getObj(lostSight.getThisEntity().getUuid());
        if (client == null) { return; }

        Entity et = lostSight.getOtherEntity();

        // send the despawn packets
        EntitySpawningView.despawnAgent(client.getChannel(), et);
    }
}
