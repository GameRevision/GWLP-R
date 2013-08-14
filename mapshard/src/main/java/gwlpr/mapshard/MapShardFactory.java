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
import gwlpr.mapshard.events.HeartBeatEvent;
import gwlpr.mapshard.events.SystemsUpdateEvent;
import gwlpr.mapshard.models.HandleRegistryNotificationDecorator;
import gwlpr.mapshard.models.Pacemaker;
import gwlpr.mapshard.models.WorldBean;
import gwlpr.protocol.handshake.EncryptionOptions;
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
    
    private static final int UPDATE_INTERVAL = 30; // ms
    private static final int HEARTBEAT_INTERVAL = 250; // ms

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
        bootstrap.childHandler(new MapShardChannelInitializer(EncryptionOptions.Enable));
        
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
        boolean             isOutpost   = Boolean.parseBoolean(                             additionalParams.get("IsOutpost"));
        int                 instanceNum = Integer.parseInt(                                 additionalParams.get("InstanceNumber"));
        DistrictRegion      region      = DistrictRegion.valueOf(                           additionalParams.get("DistrictRegion"));
        DistrictLanguage    language    = DistrictLanguage.valueOf(                         additionalParams.get("DistrictLanguage"));
        
        // failcheck
        if (mapEntity == null) { return false; }
        
        WorldBean world = new WorldBean(mapEntity, instanceNum, region, language, isPvP, isOutpost);
        
        // create all the objects needed by controllers and systems
        EventAggregator eventAgg = thisContext.get().getEventAggregator();
        HandleRegistry<ClientBean> clientRegistry = new HandleRegistryNotificationDecorator<>(eventAgg);
        EntityManager entityManager = new EntityManager();
        
        // now lets do the server ticks:
        // there is two of them, one that updates the systems of the CES
        // and one thats used to pump out the state of the server in
        // discrete time intervalls
        
        Pacemaker<SystemsUpdateEvent> sysUpdate = new Pacemaker<>(eventAgg, new SystemsUpdateEvent(), UPDATE_INTERVAL);
        Pacemaker<HeartBeatEvent> heartbeat = new Pacemaker<>(eventAgg, new HeartBeatEvent(), HEARTBEAT_INTERVAL);
        
        // register the controllers
        thisContext.get().getEventAggregator()
                // register for container related events
                .register(new ClientConnect(thisContext, parentContext, clientRegistry, world))
                .register(new ClientDisconnect(thisContext, parentContext, entityManager))
                .register(new ShutDown(thisContext, parentContext, sysUpdate, heartbeat))
                
                // register for gw-protocol related events
                .register(new LatencyAndSynchonization(clientRegistry))
                .register(new CharacterCreation())
                .register(new Chat(eventAgg))
                .register(new InstanceLoad(world, entityManager))
                .register(new MoveRotateClick(eventAgg, clientRegistry, entityManager))
                
        
        // register the CE systems
                .register(new AgentVisibilitySystem(eventAgg, entityManager))
                .register(new ChatSystem(eventAgg, clientRegistry))
                .register(new CommandSystem(eventAgg, entityManager, clientRegistry))
                .register(new MovementSystem(eventAgg, entityManager, clientRegistry))
                .register(new SchedulingSystem(eventAgg))
                .register(new SpawningSystem(eventAgg, clientRegistry));
        
        
        // start the pacemakers
        sysUpdate.start();
        heartbeat.start();
        
        return true;
    }
}
