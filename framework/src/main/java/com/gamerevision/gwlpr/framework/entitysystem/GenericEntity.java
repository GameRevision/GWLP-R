/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import java.util.UUID;


/**
 * Implementation of the Entity interface.
 * 
 * @author _rusty
 */
public class GenericEntity implements Entity
{
    
    private final UUID uuid;
    private final String name;
    
    
    /**
     * Constructor.
     * 
     * @param       uuid                    The unique ID of this entity.
     * @param       name                    The name of the entity-type 
     *                                      (e.g. "Player" or "Mob" or "Signpost" etc.)
     */
    public GenericEntity(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
    }

    
    /**
     * Getter.
     * 
     * @return      The Entity-ID of this Entity.
     */
    @Override
    public UUID getUuid() 
    {
        return uuid;
    }

    
    /**
     * Getter.
     * 
     * @return      The name of the entity-type (e.g. "Player" or "Mob" or "Signpost" etc.)
     */
    @Override
    public String getName() 
    {
        return name;
    }
    
    
    /**
     * Check if this is the same entity (if it has the same UUID)
     * 
     * @param       entity                  Another entity instance
     * @return      True if this entities UUID equals the other entities UUID.
     */
    @Override
    public int compareTo(Entity entity)
    {
        return this.uuid.compareTo(entity.getUuid());
    }
}
