/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import com.gamerevision.gwlpr.framework.entitysystem.builders.PlayerBuilder;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Controls the EntitySystem.
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
        
        // add the builders (yes, we'r doing it the hard coded way atm)
        addEntityBuilder(new PlayerBuilder());
    }
    
    
    /**
     * Creates a new entity and returns it's identifier
     * 
     * @param       name                    The name of the entity-type.
     *                                      See the entity builders or XML file
     *                                      (or whatever we use to describe entities)
     * @return      The newly created entity, or null if nothing has been created.
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
     * 
     * Note that the builder that created the entity will decide on how to delete it
     * correctly. (Because the builder knows what the entity consists of, and where
     * it needs to delete / unsubscribe etc.)
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
     * Adds an external object to this EntitySystem event-aggregator.
     * 
     * Use this method to gain access to EntitySystem internal events,
     * e.g. a 'View' object from the shardlet layer could listen for state changes
     * of any entity within this framework. You could also listen for general-
     * purpose events that need to be transmitted to a number of clients by not
     * specifying an entity (providing a null reference).
     * 
     * CAUTION! When adding a listener object that is not directly linked to any 
     * entity, you won't be able to remove the listener again, because the internal
     * event aggregator can only remove handler methods that are linked to an entity.
     * 
     * @param       listener                The object that implements EventHandlers.
     *                                      You might want to handle special Events only implemented
     *                                      within this Entity System.
     * @param       entity                  The entity that the handlers will be associated with.
     *                                      When this value is not null, you will only receive events
     *                                      from that entity.
     */
    public void addExternalListener(Object listener, Entity entity)
    {
        if (entity == null)
        {
            aggregator.addListener(listener);
        }
        else
        {
            aggregator.addListener(entity, listener);
        }
    }
    
    
    /**
     * Helper.
     * Adds a new builder to the builder's map.
     */
    private void addEntityBuilder(EntityBuilder builder)
    { 
        // initalize the builder
        builder.init(compman, aggregator);
        
        // and add it (and the name of entity it builds as the key) to our map
        builders.put(builder.getEntityName(), builder);
    }
}
