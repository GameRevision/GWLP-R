/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard;

import gwlpr.database.jpa.MapJpaController;
import gwlpr.mapshard.models.ClientBean;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import gwlpr.database.entities.Map;
import gwlpr.mapshard.controllers.CharacterCreation;
import gwlpr.mapshard.controllers.Chat;
import gwlpr.mapshard.controllers.Disconnect;
import gwlpr.mapshard.controllers.Handshake;
import gwlpr.mapshard.controllers.HeartBeat;
import gwlpr.mapshard.controllers.InstanceLoad;
import gwlpr.mapshard.controllers.MoveRotateClick;
import gwlpr.mapshard.controllers.NewClient;
import gwlpr.mapshard.controllers.Ping;
import gwlpr.mapshard.controllers.ShutDown;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.systems.AgentVisibilitySystem;
import gwlpr.mapshard.entitysystem.systems.ChatSystem;
import gwlpr.mapshard.entitysystem.systems.CommandSystem;
import gwlpr.mapshard.entitysystem.systems.MovementSystem;
import gwlpr.mapshard.entitysystem.systems.SchedulingSystem;
import gwlpr.mapshard.entitysystem.systems.SpawningSystem;
import realityshard.container.events.EventAggregator;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.gameapp.GameAppFactory;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * Produces map shards.
 * 
 * @author _rusty
 */
public class MapShardFactory implements GameAppFactory
{

    @Override
    public String getName() 
    {
        return "MapShard";
    }

    
    @Override
    public boolean isStartup() 
    {
        return false;
    }

    
    @Override
    public Channel getServerChannel(ServerBootstrap bootstrap) throws InterruptedException 
    {
        // set the attributes for new channels
        bootstrap.childAttr(ClientBean.HANDLE_KEY, null);
        
        // create the pipeline-factory
        bootstrap.childHandler(new MapShardChannelInitializer());
        
        // finally, bind and sync
        return bootstrap.bind(9112).sync().channel();
    }

    
    @Override
    public boolean initGameApp(Handle<GameAppContext> thisContext, Handle<GameAppContext> parentContext, java.util.Map<String, String> additionalParams) 
    {
        // failcheck
        if(parentContext == null || additionalParams.isEmpty()) { return false; }

        // TODO: create db stuff
        
        // parse the parameters
        Map mapEntity = MapJpaController.get().findMap(Integer.parseInt(additionalParams.get("MapId")));
        
        // failcheck
        if (mapEntity == null) { return false; }
        
        // create all the objects needed by controllers and systems
        HandleRegistry<ClientBean> clientRegistry = new HandleRegistry<>();
//      GameAppContext.Remote loginShard;
        EntityManager es = new EntityManager();
//      MapData mapData;
        
        EventAggregator agg = thisContext.get().getEventAggregator();
        
        // register the controllers
        thisContext.get().getEventAggregator()
                // register for container related events
                .register(new NewClient(thisContext, parentContext, clientRegistry, (mapEntity.getId() == 0)))
                
                // register for gw-protocol related events
                .register(new Handshake())
                .register(new HeartBeat())
                .register(new CharacterCreation())
                .register(new Chat())
                .register(new Disconnect())
                .register(new InstanceLoad())
                .register(new MoveRotateClick())
                .register(new Ping())
                .register(new ShutDown())
        
        // register the CE systems
                .register(new AgentVisibilitySystem(agg, es))
                .register(new ChatSystem(agg, clientRegistry))
                .register(new CommandSystem(agg, es, clientRegistry))
                .register(new MovementSystem(agg, es, clientRegistry))
                .register(new SchedulingSystem(agg))
                .register(new SpawningSystem(agg, clientRegistry));
        
        return true;
    }
}
