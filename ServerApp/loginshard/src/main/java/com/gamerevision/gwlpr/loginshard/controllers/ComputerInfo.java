/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.controllers;

import com.gamerevision.gwlpr.actions.loginserver.ctos.P001_ComputerUserAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P001_ComputerInfoReplyAction;
import com.realityshard.shardlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles computer information packets.
 * 
 * @author miracle444
 */
public class ComputerInfo extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ComputerInfo.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("computerinfo shardlet initialized!");
        
        // fake always accept verifier
        ShardletActionVerifier verf = new ShardletActionVerifier() {

            @Override
            public boolean check(ShardletAction action) 
            {
                return true;
            }
        };
        
        getShardletContext().addClientVerifier(verf, true);
    }
    
    
    @EventHandler
    public void computerUserHandler(P001_ComputerUserAction action)
    {
        LOGGER.debug("got the computer user packet");
        
        Session session = action.getSession();
        
        
        LOGGER.debug("sending computer info reply");
        
        P001_ComputerInfoReplyAction computerInfoReply = new P001_ComputerInfoReplyAction();
        computerInfoReply.init(session);
        computerInfoReply.setUnknown1(1905605949);
        computerInfoReply.setUnknown2((int) session.getAttribute("SyncCount"));
        computerInfoReply.setUnknown3(0);
        computerInfoReply.setUnknown4(1);
        sendAction(computerInfoReply);
    }
}
