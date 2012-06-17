/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem.events;

import com.realityshard.shardlet.Event;


/**
 * This event updates a health attribute of an agent.
 * 
 * @author _rusty
 */
public final class OnDamage implements Event
{
    
    private final int amount;
    
    
    /**
     * Constructor.
     * 
     * @param       amount                  The amount of damage
     */
    public OnDamage(int amount)
    {
        this.amount = amount;
    }

    
    /**
     * Getter.
     * 
     * @return      The value that the Health attribute of an entity will be updated with.
     */
    public int getAmount() 
    {
        return amount;
    }
}
