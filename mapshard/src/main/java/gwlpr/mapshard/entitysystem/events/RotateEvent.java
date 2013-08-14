/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.events;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.models.WorldPosition;
import realityshard.container.events.Event;


/**
 * Triggered when an entity starts or stops moving
 *
 * @author _rusty
 */
public class RotateEvent implements Event
{

    private final Entity thisEntity;
    private final float cos;
    private final float sin;


    /**
     * Constructor.
     *
     * @param       thisEntity
     */
    public RotateEvent(Entity thisEntity, float cos, float sin)
    {
        this.thisEntity = thisEntity;
        this.cos = cos;
        this.sin = sin;
    }


    public Entity getThisEntity()
    {
        return thisEntity;
    }

    
    public float getCos() 
    {
        return cos;
    }

    
    public float getSin() 
    {
        return sin;
    }    
}
