/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class describes the attachment of a session inside the map shard.
 * 
 * @author miracle444, _rusty
 */
public class SessionAttachment
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(SessionAttachment.class);
    private int loginCount;
    private int accountId;
    private int characterId;

    
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
     * Setter.
     * 
     * @param accountId 
     */
    public void setAccountId(int accountId) 
    {
        this.accountId = accountId;
    }
    
    
    /**
     * Setter.
     *
     * @param characterId
     */
    public void setCharacterId(int characterId)
    {
        this.characterId = characterId;
    }
    
    
    /**
     * Getter.
     *
     * @return
     */
    public int getCharacterId()
    {
        return characterId;
    }
}