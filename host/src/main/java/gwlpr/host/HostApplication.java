/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.host;

import gwlpr.loginshard.LoginShardFactory;
import gwlpr.mapshard.MapShardFactory;
import java.util.ArrayList;
import java.util.List;
import realityshard.container.ContainerFacade;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.GlobalExecutor;
import realityshard.container.gameapp.GameAppFactory;


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
        
        
        // create a list of our game-app factories:
        List<GameAppFactory> factories = new ArrayList<>();
        factories.add(new LoginShardFactory());
        factories.add(new MapShardFactory());
        
        
        // finall create the container and hope it initializes itself without errors...
        final ContainerFacade container;
        
        try 
        {
            container = new ContainerFacade(factories);
        } 
        catch (Exception ex) 
        {
            logger.error("Container failed to start up.");
            return;
        }
        
        // what happens when the server is shut down?
        Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    container.shutdown();
                }
            });
    }
}
