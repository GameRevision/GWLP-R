/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P001_ComputerInfoReplyAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P038_SendResponseAction;
import com.gamerevision.gwlpr.loginshard.SessionAttachment;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.ShardletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenericHandlerView
{
    private static Logger LOGGER = LoggerFactory.getLogger(GenericHandlerView.class);
    private final ShardletContext shardletContext;

    
    public GenericHandlerView(ShardletContext shardletContext)
    {
        this.shardletContext = shardletContext;
    }

    
    public void computerInfoReply(Session session)
    {
        LOGGER.debug("sending computer info reply");
        
        P001_ComputerInfoReplyAction computerInfoReply = new P001_ComputerInfoReplyAction();
        computerInfoReply.init(session);
        computerInfoReply.setData1(1905605949);
        computerInfoReply.setLoginCount(SessionAttachment.getLoginCount(session));
        computerInfoReply.setData2(0);
        computerInfoReply.setData3(1);
        shardletContext.sendAction(computerInfoReply);
    }
    
    
    public void sendResponse(Session session, int errorNumber)
    {
        LOGGER.debug("sending send response");
        
        P038_SendResponseAction sendResponse = new P038_SendResponseAction();
        sendResponse.init(session);
        sendResponse.setLoginCount(SessionAttachment.getLoginCount(session));
        sendResponse.setData1(0);
        shardletContext.sendAction(sendResponse);
        
        
        LOGGER.debug("sending stream terminator");
        
        shardletContext.sendAction(StreamTerminatorView.create(session, errorNumber));
    }
}
