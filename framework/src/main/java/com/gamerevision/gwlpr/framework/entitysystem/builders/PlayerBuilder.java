/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem.builders;

import com.gamerevision.gwlpr.framework.entitysystem.*;
import com.gamerevision.gwlpr.framework.entitysystem.components.attributes.HealthAttribute;
import com.gamerevision.gwlpr.framework.entitysystem.components.behaviours.TakeDamageBehaviour;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * This Builder is used to create a new player Entity.
 * 
 * TODO: This is a reference implementation! It is neither complete nor flawless!
 * 
 * @author _rusty
 */
public class PlayerBuilder implements EntityBuilder
{
    
    private static final String name = "Player";
    
    private ComponentManager compman;
    private EntityEventAggregator aggregator;
    
    
    /**
     * Initialize this builder
     */
    @Override
    public void init(ComponentManager manager, EntityEventAggregator aggregator)
    {
        this.compman = manager;
        this.aggregator = aggregator;
    }
    
    
    /**
     * Creates a new player Entity.
     */
    @Override
    public Entity create(UUID uuid) 
    {
        // create the GenericEntity using the UUID
        Entity entity = new GenericEntity(uuid, name);
        
        // initialize attribute components one by one and
        // add them to the component manager and aggregator
        HealthAttribute health = new HealthAttribute(100); // TODO: Replace static max health
        // ... and create all the other components
        
        compman.registerAttribute(entity, health);
        aggregator.addListener(entity, health);
        // ... and register all the other components
        
        // compile a map of attribute-component class -> attribute component object
        Map<Class<? extends AttributeComponent>, AttributeComponent> attributes = new HashMap<>();
        attributes.put(health.getClass(), health);
        // ... and add all the other components
        
        // initialize the behaviours using the attributename/attribute-object map
        // add them to the component manager and aggregator
        TakeDamageBehaviour takeDamage = new TakeDamageBehaviour(entity, attributes, aggregator);
        // ... and create all the other components
        
        compman.registerBehaviour(entity, takeDamage);
        aggregator.addListener(entity, takeDamage);
        // ... and register all the other components
        
        // return the entity object
        return null;
    }
    
    
    /**
     * Delete the player
     */
    @Override
    public void delete(Entity entity)
    {
        // let's check if this is really a player...
        if (!entity.getName().equals(name)) { return; }
        
        // simply remove the entities components from the
        // Component Manager and Entity Event Aggregator
        
        compman.unregisterComponents(entity);
        aggregator.removeListener(entity);
    }

    
    /**
     * Getter. 
     */
    @Override
    public String getEntityName() 
    {
        return name;
    }
    
}
