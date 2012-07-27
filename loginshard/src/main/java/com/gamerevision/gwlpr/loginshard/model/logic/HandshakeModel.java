/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.model.logic;

import com.gamerevision.gwlpr.loginshard.events.GotClientSeedEvent;
import com.gamerevision.gwlpr.loginshard.events.WrongClientVersionEvent;
import com.gamerevision.gwlpr.loginshard.views.HandshakeView;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HandshakeModel
{
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(HandshakeModel.class);

    private ShardletContext shardletContext;
    private Map<Session,HandshakeModelEntity> sessionMapping = new HashMap<>();
    
    
    public HandshakeModel(ShardletContext shardletContext) 
    {
        this.shardletContext = shardletContext;
    }
    
    
    private HandshakeModelEntity getEntity(Session session)
    {
        HandshakeModelEntity entity = sessionMapping.get(session);
        
        if (entity == null)
        {
            entity = new HandshakeModelEntity(session);
            sessionMapping.put(session, entity);
        }
        
        return entity;
    }
    
    
    public void setClientVersion(Session session, int clientVersion)
    {
        getEntity(session).setClientVersion(clientVersion);
    }
    
    
    public void setClientSeed(Session session, byte[] clientSeed)
    {
        getEntity(session).setClientSeed(clientSeed);
    }
    
        
    private class HandshakeModelEntity
    {
        
        
        private Session session;
        private HandshakeView handshakeModelView;
        private EventAggregator aggregator;
        
        
        public HandshakeModelEntity(Session session)
        {
            this.session = session;
            this.aggregator = shardletContext.getAggregator();
            this.handshakeModelView = new HandshakeView(shardletContext, session);
        }
        
        
        public void setClientVersion(int clientVersion)
        {
            if (false)
            {
                // wrong version
                aggregator.triggerEvent(new WrongClientVersionEvent());
            }
        }

        
        private void setClientSeed(byte[] clientSeed) 
        {
            aggregator.triggerEvent(new GotClientSeedEvent());
        }
    }
}
