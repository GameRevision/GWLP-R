/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.controllers;

import gwlpr.loginshard.models.ClientBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.NetworkClientConnectedEvent;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.network.GameAppContextKey;


/**
 * Initializes incoming client connections so they are connected to this game app.
 * 
 * @author _rusty
 */
public class NewClient
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(NewClient.class);
    
    private final GameAppContext context;
    
    
    /**
     * Constructor.
     * 
     * @param       context
     */
    public NewClient(GameAppContext context)
    {
        this.context = context;
    }
    
    
    /**
     * Event handler.
     * 
     * @param event 
     */
    @Event.Handler
    public void onNewClient(NetworkClientConnectedEvent event)
    {
        LOGGER.debug("We got a new client.");
        
        // we take all the clients!
        event.getChannel().attr(GameAppContextKey.KEY).set(context);
        event.getChannel().attr(GameAppContextKey.IS_SET).set(true);
        
        // dont forget to set the performed actions counter here.
        // this is a.k.a login count
        event.getChannel().attr(ClientBean.LOGIN_COUNT).set(0);
    }
}