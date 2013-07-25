/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import realityshard.shardlet.Action;
import realityshard.shardlet.ProtocolFilter;
import realityshard.shardlet.TriggerableAction;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Part of the GW1 protocol chain.
 * Encrypts/Decrypts the network stream of GW
 * 
 * Constructor remains empty. Should always be the case for ProtocolFilters/Shardlets
 * 
 * @author _rusty
 */
public class RC4EncryptionFilter implements ProtocolFilter
{

    /**
     * Init this filter.
     * 
     * @param       params                  Init parameters.
     */
    @Override
    public void init(Map<String, String> params)
    {
        // TODO: Check if we need to do smth here.
    }

    
    /**
     * Decrypts incoming actions, in case of activated encryption.
     * 
     * @param       action                  The new action coming from a network client.
     * @return      The list of decrypted actions 
     *              (should always be one because we decrypt the stream and no single 
     *              packets)
     * @throws      IOException             If we couldn't decrypt the buffer
     */
    @Override
    public List<TriggerableAction> doInFilter(TriggerableAction action) 
            throws IOException 
    {
        // TODO: Implement me!
        return Arrays.asList(action);
    }

    
    /**
     * Encrypts outgoing actions, in case of activated encryption.
     * 
     * @param       action                  The action that will be decrypted
     *                                      Hint: We encrypt the data from the buffer
     *                                      of this action, so it should have been
     *                                      serialized before
     * @return      The encrypted action
     * @throws      IOException             If we coudn't encrypt the buffer
     */
    @Override
    public Action doOutFilter(Action action) 
            throws IOException 
    {
        // TODO: Implement me!
        return action;
    }
    
}
