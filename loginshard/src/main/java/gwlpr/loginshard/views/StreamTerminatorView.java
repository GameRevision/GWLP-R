/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P003_StreamTerminator;
import gwlpr.loginshard.ChannelAttachment;
import io.netty.channel.Channel;


/**
 * This is a view fills the StreamTerminator action.
 * 
 * @author miracle444
 */
public class StreamTerminatorView
{

    public static void send(Channel channel, int errorCode)
    {
        P003_StreamTerminator streamTerminator = new P003_StreamTerminator();
        streamTerminator.init(channel);
        streamTerminator.setLoginCount(ChannelAttachment.getLoginCount(channel));
        streamTerminator.setErrorCode(errorCode);
        
        channel.write(streamTerminator);
    }
}
