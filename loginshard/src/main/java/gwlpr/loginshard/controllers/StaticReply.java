/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.protocol.loginserver.inbound.P001_ComputerUser;
import gwlpr.loginshard.views.StaticReplyView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;


/**
 * This handles all the actions of which we don't really process the data.
 * TODO: Check in case of strange errors, check if this is the cause.
 * 
 * @author miracle444, _rusty
 */
public class StaticReply
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StaticReply.class);
    
    
    /**
     * Event handler.
     * 
     * @param action 
     */
    @Event.Handler
    public void onComputerUser(P001_ComputerUser action)
    {
        LOGGER.debug("Got a computer user packet");
        
        StaticReplyView.computerInfoReply(action.getChannel());
    }
}