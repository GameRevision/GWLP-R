/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P001_PingRequest;
import gwlpr.protocol.gameserver.outbound.P002_PingReply;
import gwlpr.protocol.gameserver.outbound.P019_HeartBeat;
import io.netty.channel.Channel;


/**
 * This view's purpose is to provide the client with latency and
 * synchronization packets
 * 
 * @author _rusty
 */
public class TimeDeltaView 
{
    
    /**
     * Construct a heartbeat packet
     * (A.k.a server tick or keep-alive or whatever)
     * 
     * Should be happening once for each server tick.
     * 
     * @param channel
     * @param timeInterval 
     */
    public static void heartBeat(Channel channel, int timeInterval)
    {
        P019_HeartBeat heartBeat = new P019_HeartBeat();
        heartBeat.init(channel);
        heartBeat.setTimeDelta(timeInterval);
        
        channel.write(heartBeat);
    }
    
    
    /**
     * Try to request a ping reply. This is used to determine the latency of a client.
     * 
     * @param channel 
     */
    public static void pingRequest(Channel channel)
    {
        P001_PingRequest ping = new P001_PingRequest();
        ping.init(channel);
        
        channel.write(ping);
    }
    
    
    /**
     * This is the answer to a client's ping reply. (Or request)
     * 
     * @param   channel
     * @param   latency 
     */
    public static void pingReply(Channel channel, int latency)
    {
        P002_PingReply ping = new P002_PingReply();
        ping.init(channel);
        ping.setLatency(latency);
        
        channel.write(ping);
    }
}
