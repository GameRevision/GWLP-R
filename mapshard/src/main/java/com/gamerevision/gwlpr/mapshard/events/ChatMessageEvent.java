/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.events;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.realityshard.shardlet.Event;


/**
 * Triggered when we got a chat message (can come from NPCs too ;)
 * 
 * @author _rusty
 */
public class ChatMessageEvent implements Event
{
    
    private final Entity sender;
    private final String message;
    
    
    /**
     * Constructor.
     * 
     * @param       sender                  The entity that sent
     * @param       message                 The message
     */
    public ChatMessageEvent(Entity sender, String message)
    {
        this.sender = sender;
        this.message = message;   
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public String getMessage() 
    {
        return message;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public Entity getSender() 
    {
        return sender;
    }
}
