/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.realityshard.container.ContainerFacade;
import com.realityshard.network.NetworkLayer;
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
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 3);
        
        // ok, we can now define the executor as global...
        // this can actually be done within the API
        GlobalExecutor.init(executor);
        
        
        // we need a new network manager here, net layer must use IPv4, due to GW
        System.setProperty("java.net.preferIPv4Stack", "true");
        final NetworkLayer netLayer = NetworkLayer.Factory.createUsingMina();
        
        // we've done anything we wanted to, so lets start the container!
        // Note: we are using the dev environment here!
        final ContainerFacade container = new ContainerFacade(netLayer, new DevelopmentEnvironment());
        
        // what happens when the server is shut down?
        Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    container.shutdown();
                    netLayer.shutdown();
                }
            });
    }
}
