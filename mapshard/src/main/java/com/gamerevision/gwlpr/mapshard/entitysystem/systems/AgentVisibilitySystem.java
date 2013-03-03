/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.systems;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.GenericSystem;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.events.CanSeeEvent;
import com.gamerevision.gwlpr.mapshard.events.LostSightEvent;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.realityshard.shardlet.EventAggregator;
import java.util.Collection;


/**
 * This system is used for agent visibility computations.
 *
 * We need to switch agents visible / invisible depending on their
 * positions to each other.
 *
 * @author _rusty
 */
public final class AgentVisibilitySystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = 500;

    private EntityManager entityManager;


    /**
     * Constructor.
     *
     * @param aggregator
     * @param entityManager
     * @param clientLookupTable
     */
    public AgentVisibilitySystem(
            EventAggregator aggregator,
            EntityManager entityManager)
    {
        // we'll set some default time invocation value here...
        super(aggregator, UPDATEINTERVAL);

        // DO NOT REGISTER WITH THE AGGREGATOR! THE STARTUP SHARDLET WILL DO THAT

        this.entityManager = entityManager;
    }


    /**
     * This is invoked periodically.
     *
     * @param timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
        // this is where it gets interesting.
        // we fetch all the entities that have the components we need
        // then we do the tedious computation work.
        // this is quite complex, roughly O(n^2) - there is no other method of
        // doing this though

        Collection<Entity> entities = entityManager.getEntitiesWith(
                AgentID.class,
                LocalID.class,
                Position.class,
                Visibility.class);

        for (Entity thisEntity : entities)
        {
            // we dont need to calculate anything if this
            // entity is 'blind' by not having a view component:
            if (!thisEntity.has(View.class)) { continue; }

            // or by having the 'blind' option set
            View thisView = thisEntity.get(View.class);
            if (thisView.isBlind) { continue; }

            // get the position for distance calcs later on
            GWVector thisPostion = thisEntity.get(Position.class).position;

            for (Entity otherEntity : entities)
            {
                if (thisEntity == otherEntity) { continue; }

                // get the position for distance calcs later on
                GWVector otherPostion = otherEntity.get(Position.class).position;

                boolean canSee = false;

                // if the other entity is visible by its Visibility component
                if (otherEntity.get(Visibility.class).visible)
                {
                    // get this entities view distance
                    // calculate the distance between both entities
                    if (thisPostion.getDistanceTo(otherPostion) <= thisView.viewDistance)
                    {
                        canSee = true;
                    }
                }

                // then check the following (if it is invisible, the second
                // part should be executed

                // if this entity can see the other one:
                if (canSee)
                {
                    // check if the other entity is in this entities
                    // can-see list, if not,
                    if (!thisView.agentsICanSee.contains(otherEntity))
                    {
                        // 1) add it to the list, (remove it from cannot-see list)
                        thisView.agentsICanSee.add(otherEntity);
                        thisView.agentsICannotSee.remove(otherEntity);

                        // 2) trigger a OnCanSee event
                        trigger(new CanSeeEvent(thisEntity, otherEntity));
                    }
                }
                // if this entity cannot see the other one:
                else
                {
                    // check if the other entity is in this entities
                    // cannot-see list, if not,
                    if (!thisView.agentsICanSee.contains(otherEntity))
                    {
                        // 1) add it to the list, (remove it from can-see list)
                        thisView.agentsICannotSee.add(otherEntity);
                        thisView.agentsICanSee.remove(otherEntity);

                        // 2) trigger a OnLostSight event
                        trigger(new LostSightEvent(thisEntity, otherEntity));
                    }
                }
            }
        }
    }
}