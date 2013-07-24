/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.actions.gameserver.stoc.P001_UnknownAction;
import gwlpr.actions.gameserver.stoc.P002_UnknownAction;
import gwlpr.actions.gameserver.stoc.P019_UnknownAction;
import com.realityshard.shardlet.Session;


/**
 * This view's purpose is to 
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
     * @param session
     * @param timeInterval 
     */
    public static void heartBeat(Session session, int timeInterval)
    {
        P019_UnknownAction heartBeat = new P019_UnknownAction();
        heartBeat.init(session);
        heartBeat.setUnknown1(timeInterval);
        
        session.send(heartBeat);
    }
    
    
    /**
     * Try to request a ping reply. This is used to determine the latency of a client.
     * 
     * @param session 
     */
    public static void pingRequest(Session session)
    {
        P001_UnknownAction ping = new P001_UnknownAction();
        ping.init(session);
        
        session.send(ping);
    }
    
    
    /**
     * This is the answer to a client's ping reply. (Or request)
     * 
     * @param   session
     * @param   latency 
     */
    public static void pingReply(Session session, int latency)
    {
        P002_UnknownAction ping = new P002_UnknownAction();
        ping.init(session);
        ping.setUnknown1(latency);
        
        session.send(ping);
    }
}
