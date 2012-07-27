/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Controls the components (Attributes/Behaviours)
 * This class is used to access components by entities
 * 
 * The ComponentManager currently makes use of several Maps that will
 * be accessible by either the entity or the class of components you are looking for
 * 
 * @author _rusty
 */
public final class ComponentManager 
{
    
    private final Map<Entity, List<AttributeComponent>> entityAttribs                               = new ConcurrentHashMap<>();
    private final Map<Entity, List<BehaviourComponent>> entityBehavs                                = new ConcurrentHashMap<>();
    private final Map<Class<? extends AttributeComponent>, List<AttributeComponent>> attribs        = new ConcurrentHashMap<>();
    private final Map<Class<? extends BehaviourComponent>, List<BehaviourComponent>> behavs         = new ConcurrentHashMap<>();

    
    /**
     * Add a new entity-component relation. 
     * The component will also be added to the components-by-class dictionary.
     * 
     * @param       entity                  The entity that this component is part of
     * @param       component               The component of the entity
     */
    public void registerAttribute(Entity entity, AttributeComponent component)
    {
        // check if we already got a record of this entity/component
        List<AttributeComponent> recEntity = entityAttribs.get(entity);
        List<AttributeComponent> recClazz = attribs.get(component.getClass());
        
        // if not, create a new components list and add the entry
        if (recEntity == null) 
        { 
            recEntity = new ArrayList<>();
            entityAttribs.put(entity, recEntity);
        }
        if (recClazz == null) 
        { 
            recClazz = new ArrayList<>();
            attribs.put(component.getClass(), recClazz); 
        }
        
        // finally, register the component
        recEntity.add(component);
        recClazz.add(component);
    }
    
    
    /**
     * Add a new entity-component relation. 
     * The component will also be added to the components-by-class dictionary.
     * 
     * @param       entity                  The entity that this component is part of
     * @param       component               The component of the entity
     */
    public void registerBehaviour(Entity entity, BehaviourComponent component)
    {
        // check if we already got a record of this entity/component
        List<BehaviourComponent> recEntity = entityBehavs.get(entity);
        List<BehaviourComponent> recClazz = behavs.get(component.getClass());
        
        // if not, create a new components list and add the entry
        if (recEntity == null) 
        { 
            recEntity = new ArrayList<>();
            entityBehavs.put(entity, recEntity);
        }
        if (recClazz == null) 
        { 
            recClazz = new ArrayList<>();
            behavs.put(component.getClass(), recClazz); 
        }
        
        // finally, register the component
        recEntity.add(component);
        recClazz.add(component);
    }
    
    
    /**
     * Unregister any component linked to specified entity.
     * 
     * @param       entity                  The entity, whose components you want to delete
     */
    public void unregisterComponents(Entity entity)
    {
        // get the lists for this entity and its components
        List<AttributeComponent> attribsToDel = entityAttribs.get(entity);
        List<BehaviourComponent> behavsToDel = entityBehavs.get(entity);
        
        // delete the entries
        entityAttribs.remove(entity);
        entityBehavs.remove(entity);
        
        // delete or clean up the lists
        for (AttributeComponent attr : attribsToDel) 
        {
            List<AttributeComponent> list = attribs.get(attr.getClass());
            list.remove(attr);
        }
        for (BehaviourComponent beha : behavsToDel) 
        {
            List<BehaviourComponent> list = behavs.get(beha.getClass());
            list.remove(beha);
        }
    }
    
    
    /**
     * Use this method to access an entities attributes by the entity-reference directly.
     * 
     * @param       entity                  The entity, whose attributes you want to get
     * @return      The attributes of that entity, or null if we didnt recognize it
     */
    public List<AttributeComponent> getAttributes(Entity entity)
    {
        return entityAttribs.get(entity);
    }
    
    
    /**
     * Use this method to access all attributes of a certain kind.
     * 
     * @param       clazz                   The class of the attributes that you want to get
     * @return      The attributes, or null if we've got no entries for that class
     */
    public List<AttributeComponent> getAttributes(Class<? extends AttributeComponent> clazz)
    {
        return attribs.get(clazz);
    }
    
    
    /**
     * Use this method to access an entities behaviours by the entity-reference directly.
     * 
     * @param       entity                  The entity, whose behaviours you want to get
     * @return      The behaviours of that entity, or null if we didnt recognize it
     */
    public List<BehaviourComponent> getBehaviours(Entity entity)
    {
        return entityBehavs.get(entity);
    }
    
    
    /**
     * Use this method to access all behaviours of a certain kind.
     * 
     * @param       clazz                   The class of the behaviours that you want to get
     * @return      The behaviours, or null if we've got no entries for that class
     */
    public List<BehaviourComponent> getBehaviours(Class<? extends BehaviourComponent> clazz)
    {
        return behavs.get(clazz);
    }
}
