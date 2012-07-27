/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
import com.gamerevision.gwlpr.loginshard.events.GotClientSeedEvent;
import com.gamerevision.gwlpr.loginshard.events.WrongClientVersionEvent;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HandshakeView
{
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(HandshakeView.class);

    private Session session;
    private ShardletContext shardletContext;

    
    public HandshakeView(ShardletContext shardletContext, Session session)
    {
        this.session = session;
        this.shardletContext = shardletContext;
        shardletContext.getAggregator().addListener(this);
    }

    
    @EventHandler
    public void wrongClientVersionEventHandler(WrongClientVersionEvent event)
    {
        // TODO: Implement me!
    }
    
    
    @EventHandler
    public void gotClientSeedEventHandler(GotClientSeedEvent event)
    {
        LOGGER.debug("bla");
        P5633_ServerSeedAction serverSeed = new P5633_ServerSeedAction();
        serverSeed.init(session);
        serverSeed.setServerSeed(new byte[20]);
        shardletContext.sendAction(serverSeed);
    }
}
