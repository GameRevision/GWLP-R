/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.gwprotocol;

import com.realityshard.shardlet.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Part of the GW1 protocol chain.
 * De/Serializes the network stream of GW
 * 
 * Constructor remains empty. Should always be the case for ProtocolFilters/Shardlets
 * 
 * @author miracle444
 */
public class SerialisationFilter implements ProtocolFilter
{
    
    private Map<Session, Deserializer> deserializers;

    /**
     * Init this filter.
     * 
     * @param       filterConfig            The deserialized XML config file
     *                                      (Deployment descriptor of this protocol)
     * @throws      Exception               If anything went wrong with initializations
     */
    @Override
    public void init(ConfigProtocolFilter filterConfig) throws Exception 
    {
        this.deserializers = new HashMap<>();
    }

    
    /**
     * Deserializes incoming actions.
     * 
     * @param       action                  The new action coming from a network client.
     * @return      The list of deseriailized actions 
     */
    @Override
    public List<ShardletEventAction> doInFilter(ShardletEventAction action)
    {
        // try to get the deserializer for this session
        Deserializer deserializer = deserializers.get(action.getSession());
        
        // check if the filter already knows this sessions deserializer
        if (deserializer == null)
        {
            // the filter didnt know the deserializer for this session yet
            
            // create a deserializer for this session
            deserializer = new Deserializer(action.getSession());
            
            // add the newly created deserializer to the dictionary
            this.deserializers.put(action.getSession(), deserializer);
        }
        
        // deserialize the action
        return deserializer.deserialize(action);
    }

    
    /**
     * Serializes outgoing actions.
     * 
     * @param       action                  The action that will be serialized
     * @return      The serialized action
     */
    @Override
    public ShardletAction doOutFilter(ShardletAction action) 
    {
        // TODO: check success and handle failure
        // serialize it
        action.serialize();

        return action;
    }
}
