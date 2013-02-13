/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
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
    
    private boolean heartBeatEnabled = false;
    
    private Name characterName;
    private AgentID agentID;
    private LocalID localID;
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
    public boolean isHeartBeatEnabled() 
    {
        return heartBeatEnabled;
    }

    
    /**
     * Setter.
     * 
     * @param heartBeatEnabled 
     */
    public void setHeartBeatEnabled(boolean heartBeatEnabled) 
    {
        this.heartBeatEnabled = heartBeatEnabled;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public Name getCharacterName() 
    {
        return characterName;
    }

    
    /**
     * Setter.
     * 
     * @param characterName 
     */
    public void setCharacterName(Name characterName) 
    {
        this.characterName = characterName;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public AgentID getAgentID() 
    {
        return agentID;
    }

    
    /**
     * Setter.
     * 
     * @param agentID 
     */
    public void setAgentID(AgentID agentID) 
    {
        this.agentID = agentID;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public LocalID getLocalID() 
    {
        return localID;
    }

    
    /**
     * Setter.
     * @param localID 
     */
    public void setLocalID(LocalID localID) 
    {
        this.localID = localID;
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