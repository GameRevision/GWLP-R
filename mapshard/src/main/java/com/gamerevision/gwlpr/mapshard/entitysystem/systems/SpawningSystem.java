/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.systems;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.GenericSystem;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.events.CanSeeEvent;
import com.gamerevision.gwlpr.mapshard.events.LostSightEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.SpawningFaction;
import com.gamerevision.gwlpr.mapshard.views.SpawningView;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;


/**
 * This system handles agent spawning, based on agents can see each other.
 *
 * Basically, if agent A can see agent B (we wait for the event), we need to
 * spawn agent B for agent A, if A is a network client.
 *
 * TODO: we might need to send extra information with B's stuff like guild info etc.
 *
 * @author _rusty
 */
public class SpawningSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = 0;
    private final ClientLookupTable clientLookup;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       clientLookup
     */
    public SpawningSystem(EventAggregator aggregator, ClientLookupTable clientLookup)
    {
        super(aggregator, UPDATEINTERVAL);
        this.clientLookup = clientLookup;
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
    @EventHandler
    public void onEntityCanSeeOther(CanSeeEvent canSee)
    {
        // check if this entity is a network client
        Session session = clientLookup.getByEntity(canSee.getThisEntity());
        if (session == null) { return; }

        Entity et = canSee.getOtherEntity();

        String name = et.get(Name.class).name;
        int agentID = et.get(AgentID.class).agentID;
        int localID = et.get(LocalID.class).localID;
        byte[] appear = et.get(Appearance.class).appearanceDump;
        GWVector pos = et.get(Position.class).position;
        GWVector dir = et.get(Direction.class).direction;
        float speed = et.get(Movement.class).speed;
        
        SpawningFaction fac = SpawningFaction.Ally;
        
        if (et.has(NPCData.class))
        {
            // TODO this should probably be a separate spawning component...
            fac = SpawningFaction.NPC;
        }

        // send the spawn packets
        SpawningView.spawnAgent(
                session,
                name,
                agentID,
                localID,
                appear,
                pos,
                dir.toRotation(),
                speed,
                fac);
    }


    /**
     * Event handler.
     * This despawns an entity if this entity lost sight of it and is a network
     * client.
     *
     * @param lostSight
     */
    @EventHandler
    public void onEntityLostSight(LostSightEvent lostSight)
    {
        // check if this entity is a network client
        Session session = clientLookup.getByEntity(lostSight.getThisEntity());
        if (session == null) { return; }

        Entity et = lostSight.getOtherEntity();

        int agentID = et.get(AgentID.class).agentID;

        // send the despawn packets
        SpawningView.despawnAgent(session, agentID);
    }
}
