/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;


/**
 * A Behaviour is an updateable component, that also reacts to
 * events depending on its implementation.
 * 
 * @author _rusty
 */
public interface BehaviourComponent
{

    /**
     * Updates this behaviour component.
     * 
     * @param       deltaTime               The time that passed between now an the
     *                                      last invocation of this method.
     */
    public void update(int deltaTime);
}
