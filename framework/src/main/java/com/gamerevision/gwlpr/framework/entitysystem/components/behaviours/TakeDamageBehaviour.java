/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem.components.behaviours;

import com.gamerevision.gwlpr.framework.entitysystem.AttributeComponent;
import com.gamerevision.gwlpr.framework.entitysystem.BehaviourComponent;
import com.gamerevision.gwlpr.framework.entitysystem.Entity;
import com.gamerevision.gwlpr.framework.entitysystem.EntityEventAggregator;
import com.gamerevision.gwlpr.framework.entitysystem.components.attributes.HealthAttribute;
import com.gamerevision.gwlpr.framework.entitysystem.events.OnDamage;
import com.realityshard.shardlet.EventAggregator;
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
    public TakeDamageBehaviour(Entity entity, Map<String, AttributeComponent> attributes, EntityEventAggregator aggregator) 
    {
        this.entity = entity;
        this.aggregator = aggregator;
        
        this.health = (HealthAttribute) attributes.get(HealthAttribute.name);
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
