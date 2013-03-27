/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.actions.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.GenericTriggerableAction;


/**
 * This is send to a mapshard, to request its shut down.
 * 
 * @author _rusty
 */
public class ISC_ShutdownMapshardRequestAction extends GenericTriggerableAction
{
    
    /**
     * Constructor.
     */
    public ISC_ShutdownMapshardRequestAction()
    {
        init(null); // no session needed here!
    }
    
    
    /**
     * This method is used by the context of a game app, when this action is 
     * transmitted to another game app.
     * The purpose is to let the action trigger the event by itself instead of
     * having to know the exact type.
     */
    @Override
    public void triggerEvent(EventAggregator aggregator)
    {
        aggregator.triggerEvent(this);
    }
}
