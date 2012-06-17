/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import java.util.UUID;


/**
 * This interface is used by the entity builders,
 * that define what an entity is composed of.
 * 
 * @author _rusty
 */
public interface EntityBuilder 
{
    
    /**
     * Creates a new entity, depending on the concrete implementation.
     * 
     * @param       uuid                    The Entity-ID of this (new) entity.
     * @param       manager                 The component manager that will
     *                                      handle the components for us.
     * @param       aggregator              The event aggregator that the components of the
     *                                      entity will use
     * @return      The created Entity (Note that this is a very _simple_ interface ;D)
     */
    public Entity create(UUID uuid, ComponentManager manager, EntityEventAggregator aggregator);
    
    
    /**
     * Getter.
     * 
     * @return      The name of the concrete entity. 
     */
    public String getEntityName();
}
