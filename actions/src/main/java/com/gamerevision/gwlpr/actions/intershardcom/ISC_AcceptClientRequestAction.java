/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.actions.intershardcom;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericTriggerableAction;

/**
 * Request from the LoginShard to a MapShard to accept a session.
 *
 * @author miracle444
 */
public final class ISC_AcceptClientRequestAction extends GenericTriggerableAction
{

    private int key1;
    private int key2;
    private int accountId;
    private String characterName;
    
    
    /**
     * Constructor.
     * 
     * @param       session                 The session to be accepted by the MapShard.
     */
    public ISC_AcceptClientRequestAction(Session session, int key1, int key2, int accountId, String characterName)
    {
        init(session);
        this.key1 = key1;
        this.key2 = key2;
        this.accountId = accountId;
        this.characterName = characterName;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getKey1() 
    {
        return key1;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getKey2() 
    {
        return key2;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getAccountId() 
    {
        return accountId;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public String getCharacterName()
    {
        return characterName;
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