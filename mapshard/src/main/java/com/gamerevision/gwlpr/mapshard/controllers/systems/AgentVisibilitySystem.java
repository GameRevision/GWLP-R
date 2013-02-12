/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers.systems;

import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.events.HeartBeatEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import java.util.Collection;


/**
 * This shardlet is a system used for visibility computations.
 * 
 * We need to switch agents visible / invisible depending on their
 * positions to each other.
 * 
 * TODO: Enable different distance settings by reading out the
 * shardlet's config value.
 * 
 * BUG: THIS IS EXECUTED ON EACH GAME TICK!
 * 
 * @author _rusty
 */
public class AgentVisibilitySystem extends GenericShardlet
{
    
    private EntityManager entityManager;
    private ClientLookupTable clientlookup;
    
    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() {}

    
    /**
     * Extracts some data from the ContextAttachment we need for computation.
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        // this event indicates that all shardlets have been loaded (including
        // the startup shardlet) so we can safely use the context attachment now.
        
        ContextAttachment attach = ((ContextAttachment) getShardletContext().getAttachment());
        
        entityManager = attach.getEntitySystem();
        clientlookup = attach.getClientLookup();
    }
    
    
    /**
     * 
     * @param event 
     */
    @EventHandler
    public void onheartBeat(HeartBeatEvent event)
    {
        // this is where it gets interesting.
        // we fetch all the entities that have the components we need
        // then we do the tedious computation work.
        // this is quite complex, roughly O(n²) - there is no other method of
        // doing this though
        
        Collection<Entity> entities = entityManager.getEntitiesWith(
                AgentID.class,
                LocalID.class,
                Position.class,
                View.class,
                Visibility.class);
        
        for (Entity thisEntity : entities) 
        {
            for (Entity otherEntity : entities) 
            {
                if (thisEntity == otherEntity) { continue; }
                
                // if the other entity is visible by its Visibility component
                
                    // get this entities view distance
                    // calculate the distance between both entities
                
                // then check the following (if it is invisible, the second
                // part should be executed
                
                // if this entity can see the other one:
                    
                    // check if the other entity is in this entities
                    // can-see list, if not, 
                    // 1) trigger a OnCanSee event 
                    // 2) add it to the list, (remove it from cannot-see list)
                
                    // (optional) check if we or another system sends the
                    // spawn player packet
                
                // if this entity cannot see the other one:
                
                    // check if the other entity is in this entities
                    // cannot-see list, if not,
                    // 1) trigger a OnLostSight event
                    // 2) add it to the list, (remove it from can-see list)
                
                    // (optional) check if we or another system sends the
                    // de-spawn player packet
            }
        }
    }
}
