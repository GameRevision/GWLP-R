/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import com.realityshard.shardlet.Event;


/**
 * Decorates the ConcurrentEventAggregator to implement the functionality
 * of the EntityEventAggregator.
 * 
 * TODO: Implement me!
 * 
 * @author _rusty
 */
public class ConcurrentEntityEventAggregator implements EntityEventAggregator
{

    @Override
    public void addListener(Object listener) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void triggerEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void triggerEntityEvent(Entity entity, Event event) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
