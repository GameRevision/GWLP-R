/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.actions.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.GenericTriggerableAction;
import com.realityshard.shardlet.Session;

/**
 * Answer of the AcceptSessionRequest from a MapShard to the LoginShard.
 *
 * @author miracle444
 */
public final class ISC_AcceptClientReplyAction extends GenericTriggerableAction
{

    private boolean accepted;   // whether the MapShard accepted the session or not
    private int mapId;          // the map id of this MapShard
    
    
    /**
     * Constructor.
     * 
     * @param   session     the session we are talking about.
     * @param   accepted    whether it was accepted by the MapShard or not.
     */
    public ISC_AcceptClientReplyAction(Session session, boolean accepted, int mapId)
    {
        init(session);
        this.accepted = accepted;
        this.mapId = mapId;
    }

    
    /**
     * Getter.
     * 
     * @return  whether the MapShard accepted the session or not.
     */
    public boolean getAccepted()
    {
        return this.accepted;
    }
    
    
    /**
     * Getter.
     * 
     * @return  he map id of this MapShard.
     */
    public int getMapId()
    {
        return this.mapId;
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