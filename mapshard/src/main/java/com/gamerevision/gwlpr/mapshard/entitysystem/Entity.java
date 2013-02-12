/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Represents an entity (by its ID)
 * The component manager uses it's ID to access its components.
 * 
 * This should never carry any references to its actual components.
 * 
 * This class was heavily inspired by the design of
 * https://github.com/adamgit/Entity-System-RDBMS-Beta--Java-
 * 
 * @author _rusty
 */
public final class Entity
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Entity.class);
    
    private UUID uuid;
    private EntityManager manager;
    
    
    /**
     * Constructor.
     * 
     * @param       manager                 The EntityManager that this entity
     *                                      will register itself with
     * @param       components              The actual components of this entity
     */
    public Entity(EntityManager manager, Component... components)
    {
        this.manager = manager;
        addAll(components);
    }
    
    
    /**
     * Convenience method. Use this to register new components.
     * @param       components              The components that should be registered
     *                                      for this entity.
     */
    public void addAll(Component... components)
    {
        for (Component component : components) 
        {
            manager.register(this, component);   
        }
    }
    
    
    // Convenienece method. Use this to access a component of this entity.
    public <T extends Component> T get(Class<T> clazz)
    {
        Component comp = manager.getComponent(this, clazz);
        
        // failcheck
        if (comp == null) 
        { 
            LOGGER.debug("This entity does not own a component of type {}.", clazz);
            return null; 
        }
        
        // yes, this is an unchecked cast...
        return (T) comp;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The unique ID of this entity. 
     */
    public UUID getUuid()
    {
        return uuid;
    }
    
    
    /**
     * Performance:
     * Use the UUID as an identifier.
     * 
     * @return      The hash code of the uuid.
     */
    @Override
    public int hashCode()
    {
        return uuid.hashCode();
    }
    
    
    /**
     * Performance:
     * Use the UUID as an identifier.
     * 
     * @return      True if the UUID's hash code equals that of the given object.
     */
    @Override
    public boolean equals(Object obj)
    {
        // auto-generated comparison stuff...
        
        if (obj == null) 
        {
            return false;
        }
        
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        
        final Entity other = (Entity) obj;
        
        if (this.uuid != other.getUuid() && (this.uuid == null || !this.uuid.equals(other.getUuid()))) 
        {
            return false;
        }
        
        return true;
    }
}
