/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import java.util.UUID;


/**
 * Represents an entity (by its ID)
 * This interface is used for convienience. The component manager
 * uses it's ID to access its components.
 * 
 * @author _rusty
 */
public interface Entity 
{
    
    /**
     * Getter.
     * 
     * @return      The unique ID of this entity. 
     */
    public UUID getUuid();
}
