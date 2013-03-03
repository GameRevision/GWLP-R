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
 * Note on 'blindness':
 * If an entity was not blind before, and suddenly turned blind,
 * it will lose sight of the other entities it could see before. (LostSightEvent)
 * If an entity was blind before and can suddenly see again (a miracle! :P)
 * it will see entities could not see before (duh) (CanSeeEvent)
 * 
 * TODO: test if the update interval is OK
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
     * @param       aggregator
     * @param       entityManager
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
     * @param       timeDelta
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
                AgentIdentifiers.class,
                Position.class,
                Visibility.class);

        for (Entity thisEntity : entities)
        {
            // we dont need to calculate anything if this
            // entity is 'blind' by not having a view component:
            if (!thisEntity.has(View.class)) { continue; }

            // or by having the 'blind' option set
            View thisView = thisEntity.get(View.class);

            // get the position for distance calcs later on
            GWVector thisPostion = thisEntity.get(Position.class).position;

            for (Entity otherEntity : entities)
            {
                if (thisEntity == otherEntity) { continue; }

                // get the position for distance calcs later on
                GWVector otherPostion = otherEntity.get(Position.class).position;

                boolean canSee = false;

                // we need to be able to see the other entity, and it needs to be visible in general
                // then we can check if it is also in view-distance
                if (!thisView.isBlind && otherEntity.get(Visibility.class).visible)
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
                    // check if this entity did not yet see the other entity
                    if (!thisView.visibleAgents.contains(otherEntity))
                    {
                        // 1) add it to the list
                        thisView.visibleAgents.add(otherEntity);

                        // 2) trigger a OnCanSee event
                        trigger(new CanSeeEvent(thisEntity, otherEntity));
                    }
                }
                // if this entity cannot see the other one:
                else
                {
                    // check if this entity could see the other entity before
                    if (thisView.visibleAgents.contains(otherEntity))
                    {
                        // 1) remove it from list
                        thisView.visibleAgents.remove(otherEntity);

                        // 2) trigger a OnLostSight event
                        trigger(new LostSightEvent(thisEntity, otherEntity));
                    }
                }
            }
        }
    }
}
