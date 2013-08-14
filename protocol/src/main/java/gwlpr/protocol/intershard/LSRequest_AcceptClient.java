/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.intershard;

import gwlpr.database.entities.Account;
import gwlpr.database.entities.Character;
import java.util.UUID;
import realityshard.container.events.Event;

/**
 * Request from the LoginShard to a MapShard to accept a session.
 * 
 * Note that the keys defined here are randomly chosen integer values.
 * They will be different for each client.
 *
 * @author miracle444, _rusty
 */
public final class LSRequest_AcceptClient implements Event
{

    private final UUID clientUid;
    private final Account account;
    private final Character character;
    
    
    /**
     * Constructor.
     * 
     * @param       clientUid 
     * @param       account                 The account DAO of the client
     * @param       character               The character DAO of the client
     */
    public LSRequest_AcceptClient(UUID clientUid, Account account, Character character)
    {
        this.clientUid = clientUid;
        this.account = account;
        this.character = character;
    }

    
    public UUID getClientUid() 
    {
        return clientUid;
    }
    
    
    public Account getAccount() 
    {
        return account;
    }
      
    
    public Character getCharacter() 
    {
        return character;
    }
}