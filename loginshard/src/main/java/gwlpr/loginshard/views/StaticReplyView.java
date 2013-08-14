/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P001_ComputerInfoReply;
import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.models.enums.ErrorCode;
import gwlpr.protocol.loginserver.outbound.P038_SendResponse;
import io.netty.channel.Channel;
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
    
    
    /**
     * Static computer user stuff...
     * 
     * @param       channel 
     */
    public static void computerInfoReply(Channel channel)
    {
        LOGGER.debug("Sending computer info reply");
        
        // assmble action
        P001_ComputerInfoReply computerInfoReply = new P001_ComputerInfoReply();
        
        computerInfoReply.init(channel);
        computerInfoReply.setUnknown1(1905605949); // TODO: wtf is that? the client's UID (hashed)?
        computerInfoReply.setLoginCount(ClientBean.getPerformedActionsCount(channel));
        computerInfoReply.setUnknown2(0);
        computerInfoReply.setUnknown3(1);
        
        channel.writeAndFlush(computerInfoReply);
    }
    
    
    /**
     * RequestResponse/SendResponse crap.
     * This time, the GW protocol has really failed...
     *
     * @param channel 
     * @param errorNumber
     */
    public static void sendResponse(Channel channel, ErrorCode errorNumber)
    {
        LOGGER.debug("Sending send-response");
        
        P038_SendResponse sendResponse = new P038_SendResponse();
        
        sendResponse.init(channel);
        sendResponse.setLoginCount(ClientBean.getPerformedActionsCount(channel));
        
        channel.writeAndFlush(sendResponse);
        
        LOGGER.debug("Sending stream terminator");
        
        StreamTerminatorView.send(channel, errorNumber);
    }
}
