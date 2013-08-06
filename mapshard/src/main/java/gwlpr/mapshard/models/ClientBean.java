/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import gwlpr.database.entities.Account;
import gwlpr.database.entities.Character;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.Components.AgentIdentifiers;
import gwlpr.mapshard.models.enums.PlayerState;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import realityshard.container.util.Handle;


/**
 * This class is used to store account related data for a single client.
 * 
 * @author miracle444, _rusty
 */
public final class ClientBean
{
    
    /**
     * Used to access the client-handle attribute of a login session
     */
    public static final AttributeKey<Handle<ClientBean>> HANDLE_KEY = new AttributeKey<>(ClientBean.class.getName());
    
    
    private final Channel channel;
    private final Account account;
    private final Character character;
    
    private PlayerState playerState = PlayerState.LoadingInstance;
    
    private int latency;
    
    private AgentIdentifiers agentIDs;
    private Entity entity = null;

    
    /**
     * Constructor.
     * Note that the entity is created separately and is thus not included in the
     * parameters.
     * 
     * @param       channel                 The netty network channel
     * @param       acc                     The account DAO
     * @param       chara                   The character DAO
     */
    public ClientBean(Channel channel, Account acc, Character chara)
    {
        this.channel = channel;
        this.account = acc;
        this.character = chara;
    }

    
    public Channel getChannel() 
    {
        return channel;
    }

    
    public Account getAccount() 
    {
        return account;
    }

    
    public Character getCharacter() 
    {
        return character;
    }
    

    public PlayerState getPlayerState() 
    {
        return playerState;
    }

    
    public void setPlayerState(PlayerState playerState) 
    {
        this.playerState = playerState;
    }


    public int getLatency() 
    {
        return latency;
    }


    public void setLatency(int latency) 
    {
        this.latency = latency;
    }


    public AgentIdentifiers getAgentIDs() 
    {
        return agentIDs;
    }


    public void setAgentIDs(AgentIdentifiers agentID) 
    {
        this.agentIDs = agentID;
    }


    public Entity getEntity() 
    {
        return entity;
    }


    public void setEntity(Entity entity) 
    {
        this.entity = entity;
    }
}