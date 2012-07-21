/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import com.gamerevision.gwlpr.framework.entitysystem.builders.PlayerBuilder;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Controls the EnetitySystem.
 * Use this class to create new entities and manage the EntitySystem.
 * 
 * @author _rusty
 */
public class EntitySystemFacade 
{
    
    private final ScheduledExecutorService executor;
    private final EntityEventAggregator aggregator;
    
    private final ComponentManager compman;
    private final Map<String, EntityBuilder> builders;
    
    
    /**
     * Constructor.
     * 
     * @param       executor                The global thread pool manager. Use this
     *                                      parameter to avoid flooding by new-thread-creation.
     */
    public EntitySystemFacade(ScheduledExecutorService executor)
    {
        this.executor = executor;
        
        // start the aggregator
        aggregator = new ConcurrentEntityEventAggregator(executor);
        
        compman = new ComponentManager();
        builders = new ConcurrentHashMap<>();
        
        // TODO: change this if we want to support dynamical entity creation
        // also change this if we want to support automatical scanning of the builders
        // package
        
        // add the player builder
        EntityBuilder bld = new PlayerBuilder(); 
        bld.init(compman, aggregator);
        addEntityBuilder(new PlayerBuilder());
    }
    
    
    /**
     * Creates a new entity and returns it's identifier
     * 
     * @param       name                    The name of the entity-type.
     *                                      See the entity builders or XML file
     *                                      (or whatever we use to describe entities)
     * @return      The newly created entity.
     */
    public Entity createEntity(String name)
    {
        // get the right builder
        EntityBuilder builder = builders.get(name);
        
        // failcheck
        if (builder == null) { return null; }
        
        // if everything went correctly, we can simply create the new entity now
        return builder.create(UUID.randomUUID());
    }
    
    
    /**
     * Removes an entity and deletes all its components.
     * You can choose to instantly remove the entity, or letting it's components
     * do some final work before being deleted.
     * 
     * @param       entity                  The entity reference that we will use to delete the entity
     */
    public void removeEntity(Entity entity)
    {
        // get the builder of this entity
        EntityBuilder builder = builders.get(entity.getName());
        
        // failcheck
        if (builder == null) { return; }
        
        // if everything went correctly, we can simply delete the entity now
        builder.delete(entity);
    }
    
    
    /**
     * Helper.
     * Adds a new builder to the builder's map.
     */
    private void addEntityBuilder(EntityBuilder builder)
    {
        builders.put(builder.getEntityName(), builder);
    }
}
