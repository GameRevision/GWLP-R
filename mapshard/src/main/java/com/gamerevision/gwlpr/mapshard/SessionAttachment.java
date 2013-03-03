/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.models.enums.PlayerState;
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
    
    private PlayerState playerState = PlayerState.LoadingInstance;
    
    private int latency;
    
    private Name characterName;
    private AgentIdentifiers agentIDs;
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
    public PlayerState getPlayerState() 
    {
        return playerState;
    }

    
    /**
     * Setter.
     * 
     * @param playerState 
     */
    public void setPlayerState(PlayerState playerState) 
    {
        this.playerState = playerState;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getLatency() 
    {
        return latency;
    }

    
    /**
     * Setter.
     * 
     * @param latency 
     */
    public void setLatency(int latency) 
    {
        this.latency = latency;
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
    public AgentIdentifiers getAgentIDs() 
    {
        return agentIDs;
    }

    
    /**
     * Setter.
     * 
     * @param agentID 
     */
    public void setAgentIDs(AgentIdentifiers agentID) 
    {
        this.agentIDs = agentID;
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