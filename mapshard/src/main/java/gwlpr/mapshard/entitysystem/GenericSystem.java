/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem;

import gwlpr.mapshard.events.SystemsUpdateEvent;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;


/**
 * Implements some commonly used features.
 * 
 * Systems do have access to the event system (they can trigger events)
 * and are invoked by special controllers.
 * 
 * @author _rusty
 */
public abstract class GenericSystem 
{
    
    private final EventAggregator aggregator;
    private int invocationInterval;
    private int timePassed = 0;
    
    
    /**
     * Constructor.
     * 
     * @param       aggregator              The local event aggregator
     * @param       invocationInterval      Invokes the update() method after this
     *                                      interval. Note that the smallest possible time
     *                                      interval is game-app specific (the server tick,
     *                                      a.k.a. heart beat)
     *                                      (-1 = each server tick, 0 = never)
     */
    public GenericSystem(EventAggregator aggregator, int invocationInterval)
    {
        this.aggregator = aggregator;
        this.invocationInterval = invocationInterval;
        
        // DO NOT REGISTER WITH THE AGGREGATOR! THE STARTUP SHARDLET WILL DO THAT
    }
    
    
    /**
     * Convenience method.
     * Triggers an event on the game-apps event-aggregator.
     * 
     * Note that this can be used to communicate with the shardlets as well
     * as with other systems. (Or any other registered event handler)
     * 
     * @param       event 
     */
    protected void trigger(Event event)
    {
        aggregator.triggerEvent(event);
    }
    
    
    /**
     * Server tick event handler.
     * 
     * @param event 
     */
    @Event.Handler
    public void onUpdate(SystemsUpdateEvent event)
    {
        // check for special interval values:
        if (invocationInterval == 0) { return; }
        if (invocationInterval < 0) 
        { 
            update(event.getTimeDelta());
            return;
        }
        
        // now to the more complex cases
        
        // update the time that has passed
        timePassed += event.getTimeDelta();
        
        if (timePassed >= invocationInterval)
        {
            update(timePassed);
            timePassed = 0;
        }
    }
    
    
    /**
     * Template method.
     * 
     * @param       timeDelta               The time that has passe between this and
     *                                      the last invocation.
     */
    protected abstract void update(int timeDelta);

    
    /**
     * Getter.
     * 
     * @return      The latest invocation time interval
     */
    protected int getInvocationInterval() 
    {
        return invocationInterval;
    }

    
    /**
     * Setter.
     * 
     * Use this to pause the execution of a certain system, by
     * temporarily setting this value to 0.
     * (and unpause it by setting it to something else)
     * 
     * @param       invocationInterval 
     */
    protected void setInvocationInterval(int invocationInterval) 
    {
        timePassed = 0;
        this.invocationInterval = invocationInterval;
    }
}
