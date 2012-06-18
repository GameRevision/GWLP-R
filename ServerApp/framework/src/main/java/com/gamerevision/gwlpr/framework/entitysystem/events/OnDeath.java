/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem.events;

import com.realityshard.shardlet.Event;


/**
 * Thrown when an entity dies.
 * 
 * Usually thrown by the health component,
 * if not, we want to make sure that the entity really has 0 life points,
 * so the health component should make sure that its value is 0
 * 
 * @author _rusty
 */
public final class OnDeath implements Event
{

}
