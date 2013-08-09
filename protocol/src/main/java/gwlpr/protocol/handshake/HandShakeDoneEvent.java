/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.handshake;

import io.netty.channel.Channel;
import realityshard.container.events.Event;


/**
 * Triggered by the handshake handler once the handshake is done.
 * 
 * Note that this is triggered regardless of verify client was actually
 * received or not. (Meaning it might be null - e.g. on login shards)
 * 
 * @author _rusty
 */
public class HandShakeDoneEvent implements Event
{
    private final Channel channel;
    
    private final IN1_VerifyClient verifyClient;
    
    
    public HandShakeDoneEvent(Channel channel, IN1_VerifyClient verifyClient)
    {
        this.channel = channel;
        this.verifyClient = verifyClient;
    }

    
    public Channel getChannel() 
    {
        return channel;
    }

    
    public IN1_VerifyClient getVerifyClient() 
    {
        return verifyClient;
    }    
}
