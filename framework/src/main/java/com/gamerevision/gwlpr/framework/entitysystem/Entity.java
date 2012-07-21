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
public interface Entity extends Comparable<Entity>
{
    
    /**
     * Getter.
     * 
     * @return      The unique ID of this entity. 
     */
    public UUID getUuid();
    
    
    /**
     * Getter.
     * 
     * @return      The name of the entity-type (e.g. "Player" or "Mob" or "Signpost" etc.)
     */
    public String getName();
}
