/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.systems.AgentVisibility;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.MapData;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.RemoteShardletContext;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the MapShard.
 * 
 * Note that this class is only active when the game app is beaing loaded.
 * 
 * @author miracle444, _rusty
 */
public class StartUp extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StartUp.class);
    
    
    /**
     * Initialize this shardlet.
     * We will be running the whole startup process right now, right here.
     * (There is no need for event handlers and the like)
     */
    @Override
    protected void init()
    {
        // we will need the mapId to determine which map to load later on.
        // this parameter is not set by default! this has to be done by the
        // parent context
        int mapId =Integer.parseInt(this.getShardletContext().getInitParameter("MapId"));

        LOGGER.info("MapShard: init Startup controller. [mapid = {}]", mapId);
        
        // get the parent of this mapshard (as that is the login server)
        // TODO: maybe this should be an extra action coming from the LS?
        RemoteShardletContext ls = getShardletContext().getParentContext();
        
        // create the database stuff
        DatabaseConnectionProvider db = new DatabaseConnectionProvider(
                this.getInitParameter("dbip"),
                this.getInitParameter("dbport"),
                this.getInitParameter("dbdatabase"),
                this.getInitParameter("dbusername"),
                this.getInitParameter("dbpassword"));

        // create the entity system
        EntityManager es = new EntityManager();
        
        // create the client lookup table
        ClientLookupTable lt = new ClientLookupTable();
        
        // create the map data
        MapData md = loadMapData(mapId);
        
        // finally, create the context attachment
        getShardletContext().setAttachment(
                new ContextAttachment(ls, db, es, lt, md));
        
        // we'r almost finished...
        // load up the systems
        EventAggregator agg = getShardletContext().getAggregator();
        agg.register(new AgentVisibility(agg, es, lt));
        
        LOGGER.debug("Finished loading initial data");
    }
    
    
    /**
     * Helper. Generates the map data we need for this mapshard.
     *  
     * @param       dbMapID                 The map ID as used for the db records.
     * @return      The completed map data.
     */
    private MapData loadMapData(int dbMapID)
    {
        // TODO make this non-static!
        
        // sample data for GTB 
        int mapID = 0;
        int mapFileHash = 165811;
        float spawnX = -6558;
        float spawnY = -6010;
        int spawnPlane = 0;
        
        return new MapData(mapID, mapFileHash, new GWVector(spawnX, spawnY, spawnPlane));
    }
}