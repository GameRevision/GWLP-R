/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models.entitysystem.behaviours;

import com.realityshard.entitysystem.AttributeComponent;
import com.realityshard.entitysystem.BehaviourComponent;
import com.realityshard.entitysystem.Entity;
import com.realityshard.entitysystem.EntityEventAggregator;
import com.gamerevision.gwlpr.mapshard.models.entitysystem.attributes.HealthAttribute;
import com.gamerevision.gwlpr.mapshard.models.entitysystem.events.OnDamage;
import com.realityshard.shardlet.EventHandler;
import java.util.Map;


/**
 * This class implements the behaviour of taking damage.
 * 
 * Every entity that is composed of this behaviour may be reduced in health.
 * 
 * TODO: This is only a prototype-implementation!
 * 
 * @author _rusty
 */
public class TakeDamageBehaviour implements BehaviourComponent
{
    
    private final Entity entity;
    private final EntityEventAggregator aggregator;
    private final HealthAttribute health;

    
    /**
     * Constructor.
     * 
     * @param       entity                  The entity that this behaviour concerns
     * @param       attributes              The attributes of the entity
     * @param       aggregator              The event aggregator where we will post
     *                                      events to
     */
    public TakeDamageBehaviour(Entity entity, Map<Class<? extends AttributeComponent>, AttributeComponent> attributes, EntityEventAggregator aggregator) 
    {
        this.entity = entity;
        this.aggregator = aggregator;
        
        this.health = (HealthAttribute) attributes.get(HealthAttribute.class);
    }

    
    /**
     * Update this behaviour
     * 
     * @param       deltaTime               The time that passed between now an the
     *                                      last invocation of this method.
     */
    @Override
    public void update(int deltaTime) 
    {
        // do nothing actually :D
        // maybe we want to check if any negative effects are constantly
        // reducing the health of an entity
    }
    
    
    /**
     * Event handler for on damage events.
     * 
     * @param       damage                  The damage event
     */
    @EventHandler
    public void handleDamageEvent(OnDamage damage)
    {
        // usually, we would want to make sure that the event only concerns our entity
        // but we must trust the aggregator to distribute only those events that concern
        // this object
        
        int current = health.getValue();
        health.setValue(current - damage.getAmount());
    }
}
