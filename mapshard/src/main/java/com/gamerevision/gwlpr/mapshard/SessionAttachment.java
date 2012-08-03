/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class describes the attachment of a session inside the map shard.
 * 
 * @author miracle444
 */
public class SessionAttachment
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(SessionAttachment.class);
    private int accountId;
    private String characterName;

    
    /**
     * Constructor
     * 
     * @param accountId
     * @param characterName 
     */
    public SessionAttachment(int accountId, String characterName) 
    {
        this.accountId = accountId;
        this.characterName = characterName;
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
}