/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Controls the components of all registered entities.
 * Use this class to access the components for a specific entity,
 * or the components of a specific kind.
 * 
 * This class was heavily inspired by the design of
 * https://github.com/adamgit/Entity-System-RDBMS-Beta--Java-
 * 
 * @author _rusty
 */
public final class EntityManager 
{
    
    private final Map<Entity, Collection<Component>> compsOfEntity = new ConcurrentHashMap<>();
    private final Map<Component, Entity> entityOfComponent = new ConcurrentHashMap<>();
    private final Map<Class<? extends Component>, Map<Entity, Component>> compsOfCompType = new ConcurrentHashMap<>();
    
    
    /**
     * Constructor.
     */
    public EntityManager()
    {
    }
    
    
    /**
     * Add a new entity-component relation. 
     * The component will also be added to the components-by-class dictionary.
     * 
     * @param       entity                  The entity that this component is part of
     * @param       component               The component of the entity
     */
    public void register(Entity entity, Component component)
    {        
        // check if we already got a record of this entity/component
        Collection<Component> recEntity = compsOfEntity.get(entity);
        Map<Entity, Component> recClazz = compsOfCompType.get(component.getClass());
        
        // if not, create a new components list and add the entry
        if (recEntity == null) 
        { 
            recEntity = new ArrayList<>();
            compsOfEntity.put(entity, recEntity);
        }
        if (recClazz == null) 
        { 
            recClazz = new HashMap<>();
            compsOfCompType.put(component.getClass(), recClazz); 
        }
        
        // also register the entity for this component
        entityOfComponent.put(component, entity);
        
        // finally, register the component
        recEntity.add(component);
        recClazz.put(entity, component);
    }
    
    
    /**
     * Unregister any component linked to specified entity.
     * 
     * @param       entity                  The entity, whose components you want to delete
     */
    public void unregister(Entity entity)
    {
        // get the lists for this entity and its components
        Collection<Component> compsToDel = compsOfEntity.get(entity);
        
        // delete the entries
        compsOfEntity.remove(entity);
        
        // delete or clean up the lists
        for (Component comp : compsToDel) 
        {
            entityOfComponent.remove(comp);
            
            Map<Entity, Component> list = compsOfCompType.get(comp.getClass());
            list.remove(entity);
        }        
    }
    
    
    /**
     * Use this to get a specific component of an entity.
     * 
     * @param       entity                  The entity
     * @param       clazz                   The type of component you want
     * @return      The component or null if none was found.
     */
    public Component getComponent(Entity entity, Class<? extends Component> clazz)
    {
        Map<Entity, Component> comps = compsOfCompType.get(clazz);
        
        // failcheck
        if (comps == null){ return null; }
        
        return comps.get(entity);
    }
    
    
    /**
     * Use this method to access an entities attributes by the entity-reference directly.
     * 
     * @param       entity                  The entity, whose components you want to get
     * @return      The components of that entity, or null if we didnt recognize it
     */
    public Collection<Component> getComponentsOf(Entity entity)
    {
        return compsOfEntity.get(entity);
    }
    
    
    /**
     * Use this method to access all components of a certain kind.
     * 
     * @param       clazz                   The class of the components that you want to get
     * @return      The components, or an empty collection if we've got no entries for that class
     */
    public Collection<Component> getComponentsOf(Class<? extends Component> clazz)
    {
        Map<Entity, Component> comps = compsOfCompType.get(clazz);
        
        // failcheck
        if (comps == null){ return new ArrayList<>(); }
        
        return comps.values();
    }
    
    
    /**
     * Use this method to access the entity (owner) of a certain component
     * 
     * @param       component               The component in question
     * @return      The entity or NULL if none was found.
     */
    public Entity getEntityOf(Component component)
    {
        return entityOfComponent.get(component);
    }
    
    
    /**
     * Use this method to get all entities that have a certain component type
     * 
     * @param       clazz                   The type of component
     * @return      The entities possessing that component or an empty list.
     */
    public Collection<Entity> getEntitiesWith(Class<? extends Component> clazz)
    {
        Collection<Entity> result = new ArrayList<>();
        
        for (Component component : getComponentsOf(clazz)) 
        {
            Entity ent = getEntityOf(component);
            
            if (ent != null)
            {
                // failcheck
                result.add(ent);
            }
        }
        
        return result;
    }
    
    
    /**
     * Use this method to access an entities behaviours by the entity-reference directly.
     * 
     * @param       clazzes                 The types of components the entity should have
     * @return      The entities that have these component types, or empty list
     *              if we didnt recognize it
     */
    public Collection<Entity> getEntitiesWith(Class<? extends Component>... clazzes)
    {
        Collection<Entity> result = new ArrayList<>();
        
        // only do anything if we got sufficient parameters
        if (clazzes.length >= 1)
        {
            // pre-init this, so merging works later on
            result = getEntitiesWith(clazzes[1]);
            
            for (Class<? extends Component> clazz : clazzes) 
            {
                result.retainAll(getEntitiesWith(clazz));
            }
        }
        
        return result;
    }
}
