/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import gwlpr.loginshard.models.ClientBean;
import gwlpr.loginshard.controllers.*;
import gwlpr.protocol.handshake.EncryptionOptions;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import java.util.Map;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.gameapp.GameAppFactory;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * Produces login shards.
 * 
 * @author _rusty
 */
public class LoginShardFactory implements GameAppFactory
{

    @Override
    public String getName() 
    {
        return "LoginShard";
    }

    
    @Override
    public boolean isStartup() 
    {
        return true;
    }

    
    @Override
    public Channel getServerChannel(ServerBootstrap bootstrap) throws InterruptedException 
    {
        // set the attributes for new channels
        bootstrap.childAttr(ClientBean.HANDLE_KEY, null);
        
        // create the pipeline-factory
        bootstrap.childHandler(new LoginShardChannelInitializer(EncryptionOptions.Enable));
        
        // finally, bind and sync
        return bootstrap.bind(8112).sync().channel();
    }

    
    @Override
    public boolean initGameApp(Handle<GameAppContext> thisContext, Handle<GameAppContext> parentContext, Map<String, String> additionalParams) 
    {
        // TODO: create db stuff
        
        // create a new client-handle registry, so we can identify our clients...
        HandleRegistry<ClientBean> clientRegistry = new HandleRegistry<>();
        
        // register the controllers
        thisContext.get().getEventAggregator()
                // register for container related events
                .register(new NewClient(thisContext.get()))
                
                // register for gw-protocol related events
                .register(new Login(clientRegistry))
                .register(new MapDispatch(thisContext, clientRegistry))
                .register(new StaticReply()); 
        
        return true;
    }
}
