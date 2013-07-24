/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
import gwlpr.loginshard.models.EncryptionDataHolder;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This view assembles the packets used for the GW login server handshake.
 * This is not session specific
 * 
 * @author miracle444, _rusty
 */
public class HandshakeView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(HandshakeView.class);
    private final ShardletContext shardletContext;

    
    /**
     * Constructor.
     * 
     * @param       shardletContext 
     */
    public HandshakeView(ShardletContext shardletContext)
    {
        this.shardletContext = shardletContext;
    }

    
    /**
     * Send some kind of notification if the client does not use the right
     * version.
     * 
     * @param       session 
     */
    public void wrongClientVersion(Session session)
    {
        // TODO: Implement me!
        // create some new action here, using the given session
        // then do context.sendAction(whatever)
    }
    
    
    /**
     * Use the encryption data to send the server seed to the client.
     * 
     * @param       session
     * @param       data                    The encryption data
     */
    public void sendServerSeed(Session session, EncryptionDataHolder data)
    {
        // do something with the data to create the server seed byte array
        byte[] serverSeed = new byte[20]; // TODO: replace me!
        
        // assemble the action
        P5633_ServerSeedAction seedAction = new P5633_ServerSeedAction();
        seedAction.init(session);
        seedAction.setServerSeed(serverSeed);
        
        session.send(seedAction);
    }
}
