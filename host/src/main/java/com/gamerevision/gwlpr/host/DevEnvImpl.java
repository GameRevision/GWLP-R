/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.host;

import com.gamerevision.gwlpr.loginshard.controllers.Handshake;
import com.gamerevision.gwlpr.loginshard.controllers.Login;
import com.gamerevision.gwlpr.loginshard.controllers.ShardInitializer;
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
 * (This is actually done within its constructor, making it
 * very big and possibly unmaintainable...)
 * 
 * @author _rusty
 */
public class DevEnvImpl implements DevelopmentEnvironment
{
    private List<GameAppFactory> gameFactories;
    private List<ProtocolDataContainer> protContainers;

    
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
        
        
        
        // create the game app factories
        
        // login shard
        GameAppFactoryDevEnv loginshard = new GameAppFactoryDevEnv("LoginShard", 250, true, new HashMap<String, String>());
        
        loginshard.addShardlet(
            new ShardInitializer(), 
            "Shard Initializer Shardlet", 
            new HashMap<String, String>()) // add parameter hashmap here
        .addShardlet(
            new Login(), 
            "Login Shardlet", 
            new HashMap<String, String>())
        .addShardlet(
            new Handshake(), 
            "Handshake Shardlet", 
            new HashMap<String, String>());
        
        gameFactories.add(loginshard);
        
        // create another game app factory...
        
        
        
        // create the protocols
        
        // login protocol
        ArrayList<ProtocolFilter> inFil = new ArrayList<>();
        ArrayList<ProtocolFilter> outFil = new ArrayList<>();
        
        // add the seralisation filter
        SerialisationFilter serialFil = new SerialisationFilter();
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
            "GW Login Protocol", 
            7221, 
            inFil, // this should be a list of initialized 
            outFil);
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

}
