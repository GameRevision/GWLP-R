/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.realityshard.entitysystem.Entity;
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
    
    private final int accountId;
    private final int characterId;
    private String characterName;
    private Entity entity = null;

    
    /**
     * Constructor.
     * Note that the entity is created separately and is thus not included in the
     * parameters.
     * 
     * @param       accountId               The account ID of this session (as used in the DB)
     * @param       characterId             The character ID of the char that the client wants
     *                                      to play with (as used in the DB)
     */
    public SessionAttachment(int accountId, int characterId)
    {
        this.accountId = accountId;
        this.characterId = characterId;
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
    public int getCharacterId()
    {
        return characterId;
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
     * Setter.
     * 
     * @param characterName 
     */
    public void setCharacterName(String characterName) 
    {
        this.characterName = characterName;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public Entity getEntity() 
    {
        return entity;
    }

    
    /**
     * Setter.
     * 
     * @param entity 
     */
    public void setEntity(Entity entity) 
    {
        this.entity = entity;
    }
}