/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.events;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.realityshard.shardlet.Event;


/**
 * Triggered when an entity starts or stops moving
 *
 * @author _rusty
 */
public class RotateEvent implements Event
{

    private final Entity thisEntity;
    private final GWVector newDirection;


    /**
     * Constructor.
     *
     * @param       thisEntity
     */
    public RotateEvent(Entity thisEntity, GWVector newDirection)
    {
        this.thisEntity = thisEntity;
        this.newDirection = newDirection;
    }


    /**
     * Getter.
     *
     * @return
     */
    public Entity getThisEntity()
    {
        return thisEntity;
    }


    /**
     * Getter.
     *  
     * @return
     */
    public GWVector getNewDirection()
    {
        return newDirection;
    }
}
