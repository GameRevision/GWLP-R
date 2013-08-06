/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import gwlpr.actions.gameserver.ctos.P1280_VerifyClientAction;
import realityshard.shardlet.Action;
import realityshard.shardlet.ClientVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used to verify a new client, according to its
 * security keys given to it by the login shard.
 * 
 * We also do a client identification with these keys.
 * 
 * @author _rusty
 */
public class MapClientVerifier implements ClientVerifier
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(MapClientVerifier.class);
    
    private final int accountId;
    private final int characterId;
    private final int securityKey1;
    private final int securityKey2;
    

    /**
     * Constructor.
     * 
     * @param       accountId               The account id of the client that we
     *                                      want to accept with the given
     * @param       characterId             CharacterID of the char that the client
     *                                      will use
     * @param       securityKey1            Security keys that we use to identify
     *                                      the client.
     * @param       securityKey2
     */
    public MapClientVerifier(
            int accountId, 
            int characterId, 
            int securityKey1, 
            int securityKey2)
    {
        this.accountId = accountId;
        this.characterId = characterId;
        this.securityKey1 = securityKey1;
        this.securityKey2 = securityKey2;
    }
    
    
    /**
     * Check a client's first action for its validity...
     * 
     * @param       action
     * @return      True if this verifier knew the client's security keys
     */
    @Override
    public boolean check(Action action) 
    {
        // first step: ensure that the protocol matches ours
        if (!action.getSession().getProtocol().equals("GWGameServerProtocol"))
        {
            return false;
        }

        // second step: check if the first action received is of type VerifyClientAction ...
        if (!(action instanceof P1280_VerifyClientAction))
        {
            return false;
        }

        // ... if it is: cast it to get access to its properties
        P1280_VerifyClientAction thisAction = ((P1280_VerifyClientAction) action);

        // now, compare the security keys
        if (thisAction.getKey1() == securityKey1 && 
            thisAction.getKey2() == securityKey2)
        {
            // generate this new client's session's attachment
            action.getSession().setAttachment(new ClientBean(accountId, characterId));
            
            LOGGER.debug("Accepted a new client");
            return true;
        }

        return false;
    }
}
