/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import realityshard.container.EventAggregator;
import realityshard.container.GlobalExecutor;
import realityshard.container.events.HeartBeatEvent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * This class is used to create periodically reoccuring HeartBeatEvents
 * 
 * @author _rusty
 */
public class Pacemaker
{
    
    private final ScheduledExecutorService excecutor;
    private final EventAggregator outputEventAggregator;
    private final int milliSecondTimeIntervall;
    
    private ScheduledFuture<?> future;
    private long lastInvocation = 0;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The scheduler that will trigger the pacemaker
     *                                      after a certain amount of time (2nd param)
     * @param       outputEventAggregator   The aggregator that will recieve the HeartBeatEvents
     * @param       milliSecondTimeIntervall
     *                                      The time intervall in miliseconds, that this pacemaker will
     *                                      be executed.
     */
    public Pacemaker(EventAggregator outputEventAggregator, int milliSecondTimeIntervall)
    {
        this.excecutor = GlobalExecutor.get();
        this.outputEventAggregator = outputEventAggregator;
        this.milliSecondTimeIntervall = milliSecondTimeIntervall;
    }
    
    
    /**
     * Starts the pacemaker
     */
    public void start()
    {
        lastInvocation = System.currentTimeMillis();
        future = excecutor.scheduleAtFixedRate(getRunnable(), milliSecondTimeIntervall, milliSecondTimeIntervall, TimeUnit.MILLISECONDS);
    }
    
    
    /**
     * Stops the pacemaker
     */
    public void stop()
    {
        future.cancel(true);
    }

    
    /**
     * Getter.
     * 
     * @return      A new runnable that triggers the HeartBeatEvent
     */
    private Runnable getRunnable() 
    {
        // create a new anonymous object that implements
        // Runnable, so it can be used with the scheduled executor service
        return new Runnable() 
        {
            /**
             * Triggers a heartbeat event that contains the time 
             * between this and the last invocation.
             */
            @Override
            public void run() 
            {
                outputEventAggregator.triggerEvent(
                    new HeartBeatEvent(
                        (int)(System.currentTimeMillis()-lastInvocation)));
                
                // dont forget to reset last invocation!!
                lastInvocation = System.currentTimeMillis();
            }
        };       
    }
}
