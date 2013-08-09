/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P003_StreamTerminator;
import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.models.enums.ErrorCode;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is a view fills the StreamTerminator action.
 * 
 * @author miracle444
 */
public class StreamTerminatorView
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamTerminatorView.class);

    
    public static void send(Channel channel, ErrorCode errorCode)
    {
        P003_StreamTerminator streamTerminator = new P003_StreamTerminator();
        streamTerminator.init(channel);
        streamTerminator.setLoginCount(ClientBean.getPerformedActionsCount(channel));
        streamTerminator.setErrorCode(errorCode.get());
        
        // log it in case of error
        if (errorCode != ErrorCode.None)
        {
            LOGGER.debug(String.format("Channel [%s] encountered an error: %s", channel.remoteAddress().toString(), errorCode.message()));
        }
        
        channel.writeAndFlush(streamTerminator);
    }
}
