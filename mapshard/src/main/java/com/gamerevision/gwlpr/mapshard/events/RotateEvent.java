/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.events;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.realityshard.shardlet.Event;


/**
 * Triggered when an entity starts or stops moving
 *
 * @author _rusty
 */
public class RotateEvent implements Event
{

    private final Entity thisEntity;
    private final float rotation;
    private final boolean isRotating;


    /**
     * Constructor.
     *
     * @param       thisEntity
     * @param       rotation
     * @param       isRotating
     */
    public RotateEvent(Entity thisEntity, float rotation, boolean isRotating)
    {
        this.thisEntity = thisEntity;
        this.rotation = rotation;
        this.isRotating = isRotating;
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
    public float getRotation()
    {
        return rotation;
    }


    /**
     * Getter.
     *
     * @return
     */
    public boolean isRotating()
    {
        return isRotating;
    }
}
