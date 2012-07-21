/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.views;

import com.gamerevision.gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
import com.realityshard.shardlet.Session;


/**
 * This is a view fills the ServerSeed action.
 * 
 * @author miracle444
 */
public class ServerSeedView
{

    public static P5633_ServerSeedAction create(Session session)
    {
        P5633_ServerSeedAction serverSeed = new P5633_ServerSeedAction();
        serverSeed.init(session);
        serverSeed.setServerSeed(new byte[20]);
        return serverSeed;
    }
}
