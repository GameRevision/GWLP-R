/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.actions.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.GenericTriggerableAction;
import com.realityshard.shardlet.Session;

/**
 * Answer of the AcceptSessionRequest from a MapShard to the LoginShard.
 * This determines whether the map shard wants to accept the client or not.
 *
 * @author miracle444
 */
public final class ISC_AcceptClientReplyAction extends GenericTriggerableAction
{

    private boolean accepted;   // whether the MapShard accepted the session or not
    private final int accountId;
    private int mapId;          // the map id of this MapShard
    
    
    /**
     * Constructor.
     * 
     * @param       session
     * @param       accountId 
     * @param       mapId 
     * @param       accepted
     */
    public ISC_AcceptClientReplyAction(Session session, int accountId, int mapId, boolean accepted)
    {
        init(session);
        this.accepted = accepted;
        this.accountId = accountId;
        this.mapId = mapId;
    }

    
    /**
     * Getter.
     * 
     * @return      The account id of the account that we accepted to play on this mapshard.
     */
    public int getAccountId() 
    {
        return accountId;
    }
    
        
    /**
     * Getter.
     * 
     * @return      The map id of this MapShard.
     */
    public int getMapId()
    {
        return this.mapId;
    }
    
    
    /**
     * Getter.
     * 
     * @return      True if the MapShard accepted the session.
     */
    public boolean acceptedSession()
    {
        return this.accepted;
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