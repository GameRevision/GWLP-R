/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.models.WorldPosition;
import realityshard.shardlet.Event;


/**
 * Triggered when an entity starts or stops moving
 *
 * @author _rusty
 */
public class RotateEvent implements Event
{

    private final Entity thisEntity;
    private final WorldPosition newDirection;


    /**
     * Constructor.
     *
     * @param       thisEntity
     */
    public RotateEvent(Entity thisEntity, WorldPosition newDirection)
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
    public WorldPosition getNewDirection()
    {
        return newDirection;
    }
}
