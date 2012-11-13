/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.gamerevision.gwlpr.loginshard.controllers.Login;
import com.gamerevision.gwlpr.loginshard.controllers.MapShardDispatch;
import com.gamerevision.gwlpr.mapshard.controllers.CharacterCreation;
import com.gamerevision.gwlpr.mapshard.controllers.InstanceLoad;
import com.gamerevision.gwlpr.protocol.SerialisationFilter;
import com.realityshard.container.DevelopmentEnvironment;
import com.realityshard.container.gameapp.GameAppFactory;
import com.realityshard.container.gameapp.GameAppFactoryDevEnv;
import com.realityshard.container.utils.ConfigFactory;
import com.realityshard.shardlet.ProtocolFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class loads all the game apps and protocols
 * (See the various helper methods...)
 * 
 * @author _rusty
 */
public class DevEnvImpl implements DevelopmentEnvironment
{
    // The factories...
    private List<GameAppFactory> gameFactories;
    private List<ProtocolDataContainer> protContainers;
    // The standard parameters
    private Map<String, String> dbParams = new HashMap<>();

    
    /**
     * Constructor.
     * (This is the place where you should add your own stuff)
     */
    public DevEnvImpl()
            throws Exception
    {
        // init the lists so we can add the protocols and game apps
        gameFactories = new ArrayList<>();
        protContainers = new ArrayList<>(); 
        
        // also init some common parameters, like db
        dbParams.put("dbip", "localhost");
        dbParams.put("dbport", "3306");
        dbParams.put("dbdatabase", "gwlp-r");
        dbParams.put("dbusername", "root");
        dbParams.put("dbpassword", "");
        
        // Step 1:
        // create the game app factories
        gameFactories.add(produceLoginShard());
        gameFactories.add(produceMapShard());
        
        // Step 2:
        // create the protocols
        protContainers.add(produceLoginProtocol());
        protContainers.add(produceGameProtocol());
    }
    
    
    /**
     * Getter. (No need to change this)
     * 
     * @return 
     */
    @Override
    public GameAppFactory[] getGameAppFactories() 
    {
        return (GameAppFactory[]) gameFactories.toArray();
    }

    
    /**
     * Getter. (No need to change this)
     * 
     * @return 
     */
    @Override
    public ProtocolDataContainer[] getProtocolDataContainers() 
    {
        return (ProtocolDataContainer[]) protContainers.toArray();
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private GameAppFactoryDevEnv produceLoginShard()
    {
        GameAppFactoryDevEnv loginshard = new GameAppFactoryDevEnv(
                "LoginShard",
                "127.0.0.1",
                250, 
                true, 
                new HashMap<String, String>());
        
        loginshard
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.StartUp(), 
            "StartUp Shardlet", 
            dbParams) // startup has its own parameters: the db stuff
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.Handshake(), 
            "Handshake Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new Login(), 
            "Login Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new MapShardDispatch(), 
            "MapShardDispatch Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.StaticReply(), 
            "StaticReply Shardlet", 
            new HashMap<String, String>());
        
        return loginshard;
    }

    
    /**
     * Helper.
     * @return 
     */
    private GameAppFactoryDevEnv produceMapShard()
    {
        GameAppFactoryDevEnv mapshard = new GameAppFactoryDevEnv(
                "MapShard",
                "127.0.0.1",
                250, 
                false, 
                new HashMap<String, String>());
        
        mapshard
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.StartUp(), 
            "StartUp Shardlet", 
            dbParams) // startup has its own parameters: the db stuff
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.Handshake(), 
            "Handshake Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new CharacterCreation(), 
            "CharacterCreation Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new InstanceLoad(), 
            "InstanceLoad Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.StaticReply(), 
            "StaticReply Shardlet", 
            new HashMap<String, String>());
        
        return mapshard;
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private ProtocolDataContainer produceLoginProtocol()
            throws Exception
    {
        ArrayList<ProtocolFilter> inFil = new ArrayList<>();
        ArrayList<ProtocolFilter> outFil = new ArrayList<>();
        
        // add the seralisation filter
        SerialisationFilter serialFil = new SerialisationFilter();
        // init its params
        Map<String, String> params = new HashMap<>();
        params.put("ServerType", "LoginServer");
        
        serialFil.init(ConfigFactory.produceConfigProtocolFilter(
                "Serialization Filter",
                params));
        
        inFil.add(0, serialFil);
        outFil.add(outFil.size(), serialFil); // is this size-1?
        
        // add some other filter...
        
        // finally put the protocol dataholder together
        ProtocolDataContainer loginprot = new ProtocolDataContainer(
            "GWLoginServerProtocol", 
            8221, 
            inFil, // this should be a list of initialized 
            outFil);
        
        return loginprot;
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private ProtocolDataContainer produceGameProtocol()
            throws Exception
    {
        ArrayList<ProtocolFilter> inFil = new ArrayList<>();
        ArrayList<ProtocolFilter> outFil = new ArrayList<>();
        
        // add the seralisation filter
        SerialisationFilter serialFil = new SerialisationFilter();
        // init its params
        Map<String, String> params = new HashMap<>();
        params.put("ServerType", "GameServer");
        
        serialFil.init(ConfigFactory.produceConfigProtocolFilter(
                "Serialization Filter",
                params));
        
        inFil.add(0, serialFil);
        outFil.add(outFil.size(), serialFil); // is this size-1?
        
        // add some other filter...
        
        // finally put the protocol dataholder together
        ProtocolDataContainer gameprot = new ProtocolDataContainer(
            "GWGameServerProtocol", 
            9221, 
            inFil, // this should be a list of initialized 
            outFil);
        
        return gameprot;
    }
}
