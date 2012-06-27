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
     * Use this as a constructor replacement.
     * 
     * @param       manager                 The component manager that will
     *                                      handle the components for us.
     *                                      This will be the same for all entities this
     *                                      builder creates.
     * @param       aggregator              The event aggregator that the components of the
     *                                      entity will use
     *                                      This will be the same for all entities this
     *                                      builder creates.
     */
    public void init(ComponentManager manager, EntityEventAggregator aggregator);
    
            
    /**
     * Creates a new entity, depending on the concrete implementation.
     * 
     * @param       uuid                    The Entity-ID of this (new) entity.
     
     * @return      The created Entity 
     *              Note that 'Entity' is a very _simple_ interface, you wont be able to use
     *              this to call any methods on the entity
     */
    public Entity create(UUID uuid);
    
    
    /**
     * Deletes an entity
     * Note: Only the builders can safely delete an entity, 
     * because they know which components are involved
     * 
     * @param       entity                  The entity that we want to delete
     */
    public void delete(Entity entity);
    
    
    /**
     * Getter.
     * 
     * @return      The name of the concrete entity. 
     */
    public String getEntityName();
}
