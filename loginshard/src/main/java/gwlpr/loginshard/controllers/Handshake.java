/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.actions.loginserver.ctos.P1024_ClientVersionAction;
import gwlpr.actions.loginserver.ctos.P16896_ClientSeedAction;
import gwlpr.loginshard.models.EncryptionDataHolder;
import gwlpr.loginshard.models.HandshakeModel;
import gwlpr.loginshard.views.HandshakeView;
import realityshard.shardlet.EventHandler;
import realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the handshake process for GW clients.
 * It establishes an encrypted session.
 * 
 * @author miracle444, _rusty
 */
public class Handshake extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Handshake.class);
    private HandshakeView handshakeView;
    
    
    /**
     * Initialize this shardlet
     */
    @Override
    protected void init() 
    {
        this.handshakeView = new HandshakeView(getShardletContext());
        
        LOGGER.info("LoginShard: init Handshake controller");
    }
    
    
    /**
     * Handles the client version. This is actually the first packet that is send
     * by the client after connecting to the server
     * 
     * @param action 
     */
    @EventHandler
    public void onClientVersion(P1024_ClientVersionAction action)
    {
        LOGGER.debug("Got the client version packet");
        
        int clientVersion = action.getUnknown2();
        
        // lets's ask the model to check the version for us
        if (!HandshakeModel.verifyClientVersion(clientVersion))
        {
            // do something if the versio is wrong...
            handshakeView.wrongClientVersion(action.getSession());
        }        
    }
    
    
    /**
     * Handles the client encryption seed.
     * 
     * @param action 
     */
    @EventHandler
    public void onClientSeed(P16896_ClientSeedAction action)
    {        
        LOGGER.debug("Got the client seed packet");
        
        byte[] clientSeed = action.getClientSeed();
        
        // get the encryption data needed by the protocol filter
        EncryptionDataHolder data = HandshakeModel.getEncrpytionData(clientSeed);
        
        // create the server seed packet out of that and send it (indirectly)
        handshakeView.sendServerSeed(action.getSession(), data);
    }
}
