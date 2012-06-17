/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem.builders;

import com.gamerevision.gwlpr.framework.entitysystem.ComponentManager;
import com.gamerevision.gwlpr.framework.entitysystem.Entity;
import com.gamerevision.gwlpr.framework.entitysystem.EntityBuilder;
import com.gamerevision.gwlpr.framework.entitysystem.EntityEventAggregator;
import java.util.UUID;


/**
 * This Builder is used to create a new player Entity.
 * 
 * @author _rusty
 */
public class PlayerBuilder implements EntityBuilder
{
    
    private static final String name = "Player";
    
    
    /**
     * Creates a new player Entity.
     */
    @Override
    public Entity create(UUID uuid, ComponentManager manager, EntityEventAggregator aggregator) 
    {
        // initialize attribute components one by one and
        // add them to the aggregator
        
        // compile a map of attribute-component name -> attribute component object
        
        // initialize the behaviours using the attributename/attribute-object map
        // add them to the aggregator
        
        // create the GenericEntity using the UUID
        
        // compile a list of attribute objects and behaviour objects
        // add them to the ComponentManager using the entity object we just created
        
        // return the entity object
        return null;
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
