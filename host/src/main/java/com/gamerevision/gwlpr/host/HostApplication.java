/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.realityshard.container.ContainerFacade;
import com.realityshard.network.NetworkFacade;
import com.realityshard.shardlet.GlobalExecutor;
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
     */
    public static void main(String[] args) 
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
        // NOTE: possible BUG here: FORCING IPv4
        
        System.setProperty("java.net.preferIPv4Stack", "true");
        NetworkFacade netMan = new NetworkFacade("192.168.178.25");
        
        // we've done anything we wanted to, so lets start the container!
        // create the container
        // Note: we are using the dev environment here!
        ContainerFacade container = new ContainerFacade(netMan, new DevelopmentEnvironment());

        while (true) {} // TODO: do we need to process stdio input?
    }
}
