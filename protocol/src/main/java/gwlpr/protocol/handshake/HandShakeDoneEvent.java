/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.handshake;

import gwlpr.protocol.handshake.messages.P000_VerifyClient;
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

    private final P000_VerifyClient.Payload verifyClient;


    public HandShakeDoneEvent(Channel channel, P000_VerifyClient.Payload verifyClient)
    {
        this.channel = channel;
        this.verifyClient = verifyClient;
    }


    public Channel getChannel()
    {
        return channel;
    }


    public P000_VerifyClient.Payload getVerifyClient()
    {
        return verifyClient;
    }
}
