/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.test;

import gwlpr.mapshard.entitysystem.systems.SchedulingSystem;
import gwlpr.mapshard.events.SchedulingEvent;
import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GlobalExecutor;
import com.realityshard.shardlet.events.HeartBeatEvent;
import com.realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Tests the scheduling system
 *
 * @author _rusty
 */
public class SchedulingTest
{
    public static class TestEvent implements Event { public String method; public long sysTime; }

    private final Logger LOGGER = LoggerFactory.getLogger(GenericSystemTest.class);
    private final static int DELAY = 100; // ms

    @Test
    public void testScheduling() throws InterruptedException
    {
        // create the necessary objects
        GlobalExecutor.init(new ScheduledThreadPoolExecutor(12));
        EventAggregator agg = new ConcurrentEventAggregator();
        TestEvent event;

        agg.register(new SchedulingSystem(agg));
        agg.register(this);

         // test schedule on delay
        event = new TestEvent();
        event.sysTime = System.currentTimeMillis();
        event.method = "delay";
        agg.triggerEvent(new SchedulingEvent(event, SchedulingEvent.On.Delay, DELAY));

        // test schedule on absolute time
        event = new TestEvent();
        event.sysTime = System.currentTimeMillis();
        event.method = "absolute";
        agg.triggerEvent(new SchedulingEvent(event, SchedulingEvent.On.AbsoluteTime, System.currentTimeMillis() + DELAY));

        // test schedule on server tick (wait for 4 ticks)
        // (we have to emulate the server tick...)
        event = new TestEvent();
        event.sysTime = System.currentTimeMillis();
        event.method = "tick";
        agg.triggerEvent(new SchedulingEvent(event, SchedulingEvent.On.ServerTicks, 4));
        // tick one
        Thread.sleep(DELAY / 4);
        agg.triggerEvent(new HeartBeatEvent(DELAY / 4));
        // tick two
        Thread.sleep(DELAY / 4);
        agg.triggerEvent(new HeartBeatEvent(DELAY / 4));
        // tick three
        Thread.sleep(DELAY / 4);
        agg.triggerEvent(new HeartBeatEvent(DELAY / 4));
        // tick four
        Thread.sleep(DELAY / 4);
        agg.triggerEvent(new HeartBeatEvent(DELAY / 4));

        Thread.sleep(DELAY * 2);
    }


    @EventHandler
    public void onTestEvent(TestEvent evt)
    {
        LOGGER.info(
                String.format(
                    "Got test event.\n\tMethod used : %s\n\tTimediff : %d, should be roughly %d",
                    evt.method,
                    System.currentTimeMillis() - evt.sysTime,
                    DELAY));
    }
}
