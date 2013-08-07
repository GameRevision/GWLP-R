/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;

import realityshard.container.events.Event;


/**
 * Triggered when a system wants to schedule (or simply delay till the next
 * server tick)
 *
 * @author _rusty
 */
public class SchedulingEvent implements Event
{

    /**
     * The kind of scheduling
     */
    public static enum On
    {
        ServerTicks,
        Delay,
        AbsoluteTime
    }


    private final Event scheduleThis;
    private final On byMethod;
    private final long withCount;


    /**
     * Constructor.
     *
     * @param       scheduleThis            The event that will be triggered later on
     * @param       byMethod                Schedule by server ticks, delay or absolute time?
     * @param       withCount               The parameter for the chosen method.
     *                                      (Count of server ticks, delay in ms or
     *                                      absolute future system time)
     */
    public SchedulingEvent(Event scheduleThis, On byMethod, long withCount)
    {
        this.scheduleThis = scheduleThis;
        this.byMethod = byMethod;
        this.withCount = withCount;
    }


    /**
     * This event will be triggered later on
     *
     * @return
     */
    public Event scheduleThis()
    {
        return scheduleThis;
    }


    /**
     * With the chosen method of scheduling
     *
     * @return
     */
    public On byMethod()
    {
        return byMethod;
    }


    /**
     * And the parameter
     *
     * @return
     */
    public long withCount()
    {
        return withCount;
    }
}
