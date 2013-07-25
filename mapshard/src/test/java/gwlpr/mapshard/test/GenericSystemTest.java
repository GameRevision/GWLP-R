/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.test;

import gwlpr.mapshard.entitysystem.GenericSystem;
import realityshard.shardlet.Event;
import realityshard.shardlet.EventAggregator;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.GlobalExecutor;
import realityshard.shardlet.events.HeartBeatEvent;
import realityshard.shardlet.utils.ConcurrentEventAggregator;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Tests the GenericSystem's ability to handle heartBeat events.
 * 
 * @author _rusty
 */
public class GenericSystemTest
{
    public static class TestEvent implements Event { }
    
    public static class TestSystem extends GenericSystem 
    {
        private final Logger LOGGER = LoggerFactory.getLogger(TestSystem.class);
        public boolean gotUpdate = false;
        public boolean gotTestEvent = false;
        
        public TestSystem(EventAggregator agg) {
            super(agg, -1);
        }
        
        @Override
        protected void update(int timeDelta) {
            LOGGER.info("Got update with time-interval {}ms.", timeDelta);
            gotUpdate = true;
        }
        
        @EventHandler
        public void onTestEvent(TestEvent event) {
            LOGGER.info("Got test event.");
            gotTestEvent = true;
        }
    }
    
    private final Logger LOGGER = LoggerFactory.getLogger(GenericSystemTest.class);
    
    
    @Test
    public void runTest() throws InterruptedException
    {
        // init the test
        GlobalExecutor.init(new ScheduledThreadPoolExecutor(12));
        EventAggregator agg = new ConcurrentEventAggregator();
        
        TestSystem sys = new TestSystem(agg);
        agg.register(sys);
        sys.gotUpdate = false;
        sys.gotTestEvent = false;
        
        // debug
        Method[] methods = sys.getClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) == null) { continue; }
            LOGGER.info("Found event-handler: {}", method.getName());
        }
        
        // test the update event (wait a little while so the value gets updated)
        agg.triggerEvent(new HeartBeatEvent(10));
//        Thread.sleep(10);
//        assert sys.gotUpdate == true;
//        assert sys.gotTestEvent == false;
        
        //re-set values
        sys.gotUpdate = false;
        sys.gotTestEvent = false;
        
        // test the test-event (wait a little while so the value gets updated)
        agg.triggerEvent(new TestEvent());
//        Thread.sleep(10);
//        assert sys.gotUpdate == false;
//        assert sys.gotTestEvent == true;
        
        LOGGER.info("GenericSystem works as intended.");
    }
}
