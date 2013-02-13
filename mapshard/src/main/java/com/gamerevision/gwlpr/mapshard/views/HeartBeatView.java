/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P019_UnknownAction;
import com.realityshard.shardlet.Session;


/**
 * This view's purpose is to construct heartbeat packets
 * (A.k.a server tick or keep-alive or whatever)
 * 
 * @author _rusty
 */
public class HeartBeatView 
{
    public static void heartBeat(Session session, int timeInterval)
    {
        P019_UnknownAction heartBeat = new P019_UnknownAction();
        heartBeat.init(session);
        heartBeat.setUnknown1(timeInterval);
        
        session.send(heartBeat);
    }
}
