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


/**
 * This view assembles all the packets that we use as static replies
 * (Meaning they are either static or very simple)
 * 
 * @author miracle444, _rusty
 */
public class StaticReplyView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StaticReplyView.class);
    private final ShardletContext shardletContext;

    
    /**
     * Constructor. 
     * 
     * @param       shardletContext 
     */
    public StaticReplyView(ShardletContext shardletContext)
    {
        this.shardletContext = shardletContext;
    }

    
    /**
     * Static computer user stuff...
     * 
     * @param       session 
     */
    public void computerInfoReply(Session session)
    {
        LOGGER.debug("Sending computer info reply");
        
        // assmble action
        P001_ComputerInfoReplyAction computerInfoReply = new P001_ComputerInfoReplyAction();
        
        computerInfoReply.init(session);
        computerInfoReply.setData1(1905605949);
        computerInfoReply.setLoginCount(SessionAttachment.getLoginCount(session));
        computerInfoReply.setData2(0);
        computerInfoReply.setData3(1);
        
        shardletContext.sendAction(computerInfoReply);
    }
    
    
    /**
     * RequestResponse/SendResponse crap.
     * This time, the GW protocol has really failed...
     * 
     * @param       session
     * @param       errorNumber 
     */
    public void sendResponse(Session session, int errorNumber)
    {
        LOGGER.debug("Sending send-response");
        
        P038_SendResponseAction sendResponse = new P038_SendResponseAction();
        
        sendResponse.init(session);
        sendResponse.setLoginCount(SessionAttachment.getLoginCount(session));
        sendResponse.setData1(0);
        
        shardletContext.sendAction(sendResponse);
        
        LOGGER.debug("Sending stream terminator");
        
        shardletContext.sendAction(StreamTerminatorView.create(session, errorNumber));
    }
}
