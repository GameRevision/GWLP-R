/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import gwlpr.protocol.serialization.Deserializer;
import gwlpr.protocol.serialization.GameServerDeserializer;
import gwlpr.protocol.serialization.LoginServerDeserializer;
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
public class SerializationFilter implements ProtocolFilter
{
    
    private Map<Session, Deserializer> deserializers;
    private boolean isLoginServer;
    
    /**
     * Init this filter.
     * 
     * @param       params                  Init parameters.
     */
    @Override
    public void init(Map<String, String> params)
    {
        final String serverType = params.get("ServerType");
        
        isLoginServer = serverType.equals("LoginServer");

        deserializers = new HashMap<>();
    }

    
    /**
     * Deserializes incoming actions.
     * 
     * @param       action                  The new action coming from a network client.
     * @return      The list of deseriailized actions 
     */
    @Override
    public List<TriggerableAction> doInFilter(TriggerableAction action)
    {
        // try to get the deserializer for this session
        Deserializer deserializer = deserializers.get(action.getSession());
        
        // check if the filter already knows this sessions deserializer
        if (deserializer == null)
        {
            // the filter didnt know the deserializer for this session yet
            
            // create a deserializer for this session
            deserializer = this.isLoginServer ?
                                new LoginServerDeserializer(action.getSession()) :
                                new GameServerDeserializer(action.getSession());
            
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
    public Action doOutFilter(Action action) 
    {
        // TODO: check success and handle failure
        // serialize it
        action.serialize();

        return action;
    }
}
