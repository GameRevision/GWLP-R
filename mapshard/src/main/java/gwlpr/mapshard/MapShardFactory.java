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
import gwlpr.mapshard.controllers.ClientDisconnect;
import gwlpr.mapshard.controllers.LatencyAndSynchonization;
import gwlpr.mapshard.controllers.InstanceLoad;
import gwlpr.mapshard.controllers.MoveRotateClick;
import gwlpr.mapshard.controllers.ClientConnect;
import gwlpr.mapshard.controllers.ShutDown;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.systems.AgentVisibilitySystem;
import gwlpr.mapshard.entitysystem.systems.ChatSystem;
import gwlpr.mapshard.entitysystem.systems.CommandSystem;
import gwlpr.mapshard.entitysystem.systems.MovementSystem;
import gwlpr.mapshard.entitysystem.systems.SchedulingSystem;
import gwlpr.mapshard.entitysystem.systems.SpawningSystem;
import gwlpr.mapshard.models.HandleRegistryNotificationDecorator;
import gwlpr.mapshard.models.WorldBean;
import gwlpr.protocol.intershard.utils.DistrictLanguage;
import gwlpr.protocol.intershard.utils.DistrictRegion;
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
        
        // parse the parameters for the worldbean
        Map                 mapEntity   = MapJpaController.get().findMap(Integer.parseInt(  additionalParams.get("MapId")));
        boolean             isPvP       = Boolean.parseBoolean(                             additionalParams.get("IsPvP"));
        int                 instanceNum = Integer.parseInt(                                 additionalParams.get("InstanceNumber"));
        DistrictRegion      region      = DistrictRegion.valueOf(                           additionalParams.get("DistrictRegion"));
        DistrictLanguage    language    = DistrictLanguage.valueOf(                         additionalParams.get("DistrictLanguage"));
        
        // failcheck
        if (mapEntity == null) { return false; }
        
        WorldBean world = new WorldBean(mapEntity, instanceNum, region, language, isPvP);
        
        // create all the objects needed by controllers and systems
        EventAggregator eventAgg = thisContext.get().getEventAggregator();
        HandleRegistry<ClientBean> clientRegistry = new HandleRegistryNotificationDecorator<>(eventAgg);
        EntityManager entityManager = new EntityManager();
        
        // register the controllers
        thisContext.get().getEventAggregator()
                // register for container related events
                .register(new ClientConnect(thisContext, parentContext, clientRegistry, world, entityManager))
                .register(new ClientDisconnect(thisContext, parentContext, entityManager))
                .register(new ShutDown(thisContext, parentContext))
                
                // register for gw-protocol related events
                .register(new LatencyAndSynchonization(clientRegistry))
                .register(new CharacterCreation())
                .register(new Chat(eventAgg))
                .register(new InstanceLoad(world, entityManager))
                .register(new MoveRotateClick(eventAgg))
                
        
        // register the CE systems
                .register(new AgentVisibilitySystem(eventAgg, entityManager))
                .register(new ChatSystem(eventAgg, clientRegistry))
                .register(new CommandSystem(eventAgg, entityManager, clientRegistry))
                .register(new MovementSystem(eventAgg, entityManager, clientRegistry))
                .register(new SchedulingSystem(eventAgg))
                .register(new SpawningSystem(eventAgg, clientRegistry));
        
        return true;
    }
}
