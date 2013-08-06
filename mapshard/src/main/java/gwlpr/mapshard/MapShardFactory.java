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
import gwlpr.mapshard.controllers.Ping;
import gwlpr.mapshard.controllers.ShutDown;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.systems.AgentVisibilitySystem;
import gwlpr.mapshard.entitysystem.systems.ChatSystem;
import gwlpr.mapshard.entitysystem.systems.CommandSystem;
import gwlpr.mapshard.entitysystem.systems.MovementSystem;
import gwlpr.mapshard.entitysystem.systems.SchedulingSystem;
import gwlpr.mapshard.entitysystem.systems.SpawningSystem;
import java.util.UUID;
import realityshard.container.events.EventAggregator;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.gameapp.GameAppFactory;
import realityshard.container.gameapp.GameAppManager;
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
        bootstrap.attr(ClientBean.HANDLE_KEY, null);
        
        // create the pipeline-factory
        bootstrap.handler(new MapShardChannelInitializer());
        
        // finally, bind and sync
        return bootstrap.bind(9112).sync().channel();
    }

    
    @Override
    public GameAppContext.Remote produceGameApp(GameAppManager manager, GameAppContext.Remote parent, java.util.Map<String, String> additionalParams) 
    {
        // failcheck
        if(parent == null || additionalParams.isEmpty()) { return null; }
        
        // create the actual context
        GameAppContext.Default context = new GameAppContext.Default(getName(), manager, parent);
        
        // TODO: create db stuff
        
        // parse the parameters
        Map mapEntity = MapJpaController.get().findMap(Integer.parseInt(additionalParams.get("MapId")));
        UUID thisUid = UUID.fromString(additionalParams.get("UniqueId"));
        
        // failcheck
        if (mapEntity == null || thisUid == null) { return null; }
        
        // create all the objects needed by controllers and systems
        HandleRegistry<ClientBean> clientRegistry = new HandleRegistry<>();
//      GameAppContext.Remote loginShard;
        EntityManager es = new EntityManager();
//      MapData mapData;
        
        EventAggregator agg = context.getEventAggregator();
        
        // register the controllers
        context.getEventAggregator()
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
                .register(new SpawningSystem(agg, lt));
        
        return context;
    }
}
