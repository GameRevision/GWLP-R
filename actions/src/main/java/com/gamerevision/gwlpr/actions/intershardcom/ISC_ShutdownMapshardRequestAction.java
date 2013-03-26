/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.actions.intershardcom;

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
}
