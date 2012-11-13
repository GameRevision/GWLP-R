/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.realityshard.container.ContainerFacade;
import com.realityshard.network.ConcurrentNetworkManager;
import com.realityshard.shardlet.GlobalExecutor;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class loads the R:S development environment for GWLPR
 * 
 * @author _rusty
 */
public final class HostApplication 
{
    /**
     * Runs the application.
     * 
     * @param       args
     * @throws      MalformedURLException   Should never happen.
     */
    public static void main(String[] args) 
            throws MalformedURLException
    {
        // temporary logger
        Logger logger = LoggerFactory.getLogger(HostApplication.class);        
        
        
        // output some smart info
        logger.info("Host application starting up...");
        
        
        // create the executor
        // TODO: let the parameter be defined by command line args
        // TODO: check if the implementation is appropriate
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(12);
        
        // ok, we can now define the executor as global...
        // this can actually be done within the API
        GlobalExecutor.init(executor);
        
        // we need a new concurrent network manager here
        // note that this has to be a concrete implementation atm
        ConcurrentNetworkManager netMan = null;
        try 
        {
            // TODO: BUG! using a CPU - load reducing looping here atm
            // (will have to switch to some more sophisticated implementation of NetMan in the future)
            netMan = new ConcurrentNetworkManager(512, 1000);
        } 
        catch (IOException ex) 
        {
            logger.error("Network manager failed to start up.", ex);
            System.exit(1);
        }
        
        // we've done anything we wanted to, so lets start the container!
        try 
        {
            // create the container
            // Note: we are using the dev environment here!
            ContainerFacade container = new ContainerFacade(netMan, new DevEnvImpl());
        } 
        catch (Exception ex) 
        {
            logger.error("Container failed to start up.", ex);
            System.exit(1);
        }
    }
}
