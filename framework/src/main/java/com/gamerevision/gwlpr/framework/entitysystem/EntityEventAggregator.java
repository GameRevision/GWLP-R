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
     * Add an entity-dependent listener to the listener collection.
     * Note that by using the usual entity independent addListener method,
     * you will always receive events, even if they dont concern your entity/component.
     * 
     * @param       entity                  The concrete entity that you want to receive events for.
     * @param       listener                The listener object (may contain several event-handlers, as usual!)
     *                                      This should almost always be a component of some type...
     */
    public void addListener(Entity entity, Object listener);
    
    
    /**
     * Remove all event handlers of a single entity from this aggregator.
     * Note that handlers that are not entity-specific can not be removed.
     * 
     * @param       entity                  All handlers from this entity will be removed from the
     *                                      event-aggregator. (No matter which object holds them)
     */
    public void removeListener(Entity entity);
    
    
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
