/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;

import realityshard.container.events.Event;


/**
 * Base class for all the different update events
 * 
 * @author _rusty
 */
public abstract class AbstractTimedeltaEvent implements Event, Cloneable
{

    private int timeDelta;
    
    
    /**
     * Getter.
     * 
     * @return      The amount of milliseconds of the time interval
     *              that has passed since the last event of this kind.
     */
    public int getTimeDelta()
    {
        return timeDelta;
    }

    
    /**
     * Setter.
     * 
     * @param       timeDelta               The new time delta of this.
     */
    public void setTimeDelta(int timeDelta) 
    {
        this.timeDelta = timeDelta;
    }
    
    
    @Override
    public abstract AbstractTimedeltaEvent clone();
}