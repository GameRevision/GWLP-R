/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
import com.gamerevision.gwlpr.loginshard.model.logic.EncryptionDataHolder;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HandshakeView
{
    private static Logger LOGGER = LoggerFactory.getLogger(HandshakeView.class);
    private final ShardletContext shardletContext;

    
    public HandshakeView(ShardletContext shardletContext)
    {
        this.shardletContext = shardletContext;
    }

    
    public void wrongClientVersion(Session session)
    {
        // TODO: Implement me!
        // create some new action here, using the given session
        // then do context.sendAction(whatever)
    }
    
    
    public void sendServerSeed(Session session, EncryptionDataHolder data)
    {
        // do smthin with the data to create the server seed byte array
        byte[] serverSeed = new byte[20]; // TODO: replace me!
        
        P5633_ServerSeedAction seedAction = new P5633_ServerSeedAction();
        seedAction.init(session);
        seedAction.setServerSeed(serverSeed);
        
        shardletContext.sendAction(seedAction);
    }
}
