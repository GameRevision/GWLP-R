/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;

import realityshard.container.events.Event;


/**
 * Base class for all events that are some kind of heartbeat (periodically
 * reoccuring)
 *
 * @author _rusty
 */
public class HeartBeatEvent implements Event 
{

    private final int milliSecondsSinceLastHeartBeat;

    
    /**
     * Constructor.
     * 
     * @param       milliSecondsSinceLastHeartBeat
     */
    public HeartBeatEvent(int milliSecondsSinceLastHeartBeat) 
    {
        this.milliSecondsSinceLastHeartBeat = milliSecondsSinceLastHeartBeat;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The amount of milliseconds of the time interval
     *              that has passed since the last HeartBeat event.
     */
    public int getPassedTimeInterval()
    {
        return milliSecondsSinceLastHeartBeat;
    }
}
