/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.systems;

import gwlpr.mapshard.entitysystem.GenericSystem;
import gwlpr.mapshard.events.SchedulingEvent;
import realityshard.shardlet.Event;
import realityshard.shardlet.EventAggregator;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.GlobalExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * This component can be used by issuing a SchedulingEvent
 * @author _rusty
 */
public class SchedulingSystem extends GenericSystem
{

    /**
     * Used to manage event that have to be triggered after
     * a certain count of server ticks.
     */
    private static class EventAfterTicks
    {
        public Event event;
        public int afterTicks;
    }


    private final static int UPDATEINTERVAL = -1;
    private final List<EventAfterTicks> eventsOnServerTick;


    /**
     * Constructor.
     *
     * @param aggregator
     */
    public SchedulingSystem(EventAggregator aggregator)
    {
        super(aggregator, UPDATEINTERVAL);

        eventsOnServerTick = new ArrayList<>();
    }


    /**
     * This is invoked periodically.
     * (Or not, depending on the update interval)
     *
     * @param timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
        List<EventAfterTicks> toRemove = new ArrayList<>();

        for (EventAfterTicks event : eventsOnServerTick)
        {
            event.afterTicks--;

            // if we waited long enough,
            if (event.afterTicks <= 0)
            {
                // we can trigger the scheduled event
                trigger(event.event);
                // and remove it from the list (later on!)
                toRemove.add(event);
            }
        }

        // clean up the event-on-server-tick-list
        for (EventAfterTicks removeMe : toRemove)
        {
            eventsOnServerTick.remove(removeMe);
        }
    }


    /**
     * Event handler.
     * Manages the event scheduling.
     *
     * @param event
     */
    @EventHandler
    public void onScheduling(final SchedulingEvent event)
    {
        switch (event.byMethod())
        {
            case ServerTicks:
                EventAfterTicks evt = new EventAfterTicks();
                evt.event = event.scheduleThis();
                evt.afterTicks = (int) event.withCount();
                eventsOnServerTick.add(evt);
                break;
            case Delay:
                // failcheck
                if (event.withCount() <= 0) { trigger(event.scheduleThis()); break; }
                // else schedule this
                GlobalExecutor.get().schedule(
                        new Runnable() {
                            @Override public void run() {
                                trigger(event.scheduleThis());
                            }
                        },
                        event.withCount(),
                        TimeUnit.MILLISECONDS);
                break;
            case AbsoluteTime:
                // failcheck for delay time
                int delay = (int) (event.withCount() - System.currentTimeMillis());
                if (delay <= 0) { trigger(event.scheduleThis()); break; }
                // else schedule this
                GlobalExecutor.get().schedule(
                        new Runnable() {
                            @Override public void run() {
                                trigger(event.scheduleThis());
                            }
                        },
                        delay,
                        TimeUnit.MILLISECONDS);
                break;
        }
    }
}
