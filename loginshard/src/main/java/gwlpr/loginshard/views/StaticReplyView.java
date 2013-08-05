/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P001_ComputerInfoReply;
import gwlpr.loginshard.models.ClientBean;
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
        computerInfoReply.setLoginCount(ClientBean.get(channel).getLoginCount());
        computerInfoReply.setUnknown2(0);
        computerInfoReply.setUnknown3(1);
        
        channel.write(computerInfoReply);
    }
}
