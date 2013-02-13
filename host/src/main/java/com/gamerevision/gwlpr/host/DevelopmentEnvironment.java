/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.gamerevision.gwlpr.loginshard.controllers.Login;
import com.gamerevision.gwlpr.loginshard.controllers.MapShardDispatch;
import com.gamerevision.gwlpr.mapshard.controllers.CharacterCreation;
import com.gamerevision.gwlpr.mapshard.controllers.InstanceLoad;
import com.gamerevision.gwlpr.mapshard.entitysystem.systems.AgentVisibility;
import com.gamerevision.gwlpr.protocol.SerialisationFilter;
import com.realityshard.container.gameapp.GenericGameAppFactory;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.environment.Environment;
import com.realityshard.shardlet.environment.GameAppFactory;
import com.realityshard.shardlet.environment.ProtocolFactory;
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
public class DevelopmentEnvironment implements Environment
{
    // The factories...
    private List<GameAppFactory> gameFactories;
    private List<ProtocolFactory> protContainers;
    // The standard parameters
    private Map<String, String> dbParams = new HashMap<>();

    
    /**
     * Constructor.
     * (This is the place where you should add your own stuff)
     * @throws Exception 
     */
    public DevelopmentEnvironment()
    {
        // init the lists so we can add the protocols and game apps
        gameFactories = new ArrayList<>();
        protContainers = new ArrayList<>(); 
        
        // also init some common parameters, like db
        dbParams.put("dbip", "localhost");
        dbParams.put("dbport", "3306");
        dbParams.put("dbdatabase", "gwlpr");
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
    public List<GameAppFactory> getGameAppFactories() 
    {
        return gameFactories;
    }

    
    /**
     * Getter. (No need to change this)
     * 
     * @return 
     */
    @Override
    public List<ProtocolFactory> getProtocolFactories() 
    {
        return protContainers;
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private GameAppFactory produceLoginShard()
    {
        GameAppFactory loginshard = new GenericGameAppFactory(
                "LoginShard",
                "192.168.178.25",
                250, 
                true, 
                new HashMap<String, String>());
        
        Map<String, String> dummy = new HashMap<>();
        
        loginshard
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.StartUp(), 
            dbParams) // startup has its own parameters: the db stuff
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.Handshake(), 
            dummy)
        .addShardlet(
            new Login(), 
            dummy)
        .addShardlet(
            new MapShardDispatch(), 
            dummy)
        .addShardlet(
            new com.gamerevision.gwlpr.loginshard.controllers.StaticReply(), 
            dummy);
        
        return loginshard;
    }

    
    /**
     * Helper.
     * @return 
     */
    private GameAppFactory produceMapShard()
    {
        GameAppFactory mapshard = new GenericGameAppFactory(
                "MapShard",
                "192.168.178.25",
                250, 
                false, 
                new HashMap<String, String>());
        
        Map<String, String> dummy = new HashMap<>();
        
        mapshard
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.StartUp(), 
            dbParams) // startup has its own parameters: the db stuff
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.Handshake(), 
            dummy)
        .addShardlet(
            new CharacterCreation(), 
            dummy)
        .addShardlet(
            new InstanceLoad(), 
            dummy)
        .addShardlet(
            new com.gamerevision.gwlpr.mapshard.controllers.StaticReply(),
            dummy);
        
        return mapshard;
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private ProtocolFactory produceLoginProtocol()
    {
        ArrayList<ProtocolFilter> inFil = new ArrayList<>();
        ArrayList<ProtocolFilter> outFil = new ArrayList<>();
        
        // add the seralisation filter
        ProtocolFilter serialFil = new SerialisationFilter();
        // init its params
        Map<String, String> params = new HashMap<>();
        params.put("ServerType", "LoginServer");
        
        serialFil.init(params);
        
        inFil.add(serialFil);
        outFil.add(0, serialFil);
        
        // add some other filter...
        
        // finally put the protocol factory together
        ProtocolFactory loginprot = new ProtocolFactory(
            "GWLoginServerProtocol", 
            8112, 
            inFil,
            outFil);
        
        return loginprot;
    }
    
    
    /**
     * Helper.
     * @return 
     */
    private ProtocolFactory produceGameProtocol()
    {
        ArrayList<ProtocolFilter> inFil = new ArrayList<>();
        ArrayList<ProtocolFilter> outFil = new ArrayList<>();
        
        // add the seralisation filter
        ProtocolFilter serialFil = new SerialisationFilter();
        // init its params
        Map<String, String> params = new HashMap<>();
        params.put("ServerType", "GameServer");
        
        serialFil.init(params);
        
        inFil.add(serialFil);
        outFil.add(0, serialFil);
        
        // add some other filter...
        
        // finally put the protocol dataholder together
        ProtocolFactory gameprot = new ProtocolFactory(
            "GWGameServerProtocol", 
            9112, 
            inFil,
            outFil);
        
        return gameprot;
    }
}
