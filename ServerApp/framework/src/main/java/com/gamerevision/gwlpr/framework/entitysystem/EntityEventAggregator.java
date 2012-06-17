/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;


/**
 * This is a customized event aggregator based on the
 * shardlet.EventAggregator design
 * 
 * It is needed because we need to trigger some events only on a certain entity-scope
 * (which cant be done with the usual EventAggregator)
 * 
 * @author _rusty
 */
public interface EntityEventAggregator extends EventAggregator
{
    
    /**
     * Trigger an event, the EventAggregator will try to distribute it to all 
     * registered listeners
     * 
     * Note that the generic type parameter is necessary in order to
     * be able to call the listener later on.
     * 
     * @param       entity                  The entity that this event concerns
     * @param       event                   The event that will be published on this Event-Aggregator
     */
    public void triggerEntityEvent(Entity entity, Event event);
}
