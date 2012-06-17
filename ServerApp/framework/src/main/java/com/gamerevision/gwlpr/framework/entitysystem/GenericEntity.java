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
    
    
    /**
     * Constructor.
     * 
     * @param       uuid                    The unique ID of this entity.
     */
    public GenericEntity(UUID uuid)
    {
        this.uuid = uuid;
    }

    
    /**
     * Getter.
     * 
     * @return      The Entity-ID of this Entity.
     */
    public UUID getUuid() {
        return uuid;
    }
}
