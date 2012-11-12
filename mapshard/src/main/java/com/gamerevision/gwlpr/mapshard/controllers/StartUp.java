/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.entitysystem.EntitySystemFacade;
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
        int mapId =Integer.parseInt(this.getShardletContext().getInitParameter("MapId"));

        LOGGER.debug("MapShard: init Startup controller [mapid = {}]", mapId);
        
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
        EntitySystemFacade es = new EntitySystemFacade();
        
        // create the client lookup table
        ClientLookupTable lt = new ClientLookupTable();
        
        // finally, create the context attachment
        getShardletContext().setAttachment(
                new ContextAttachment(ls, db, es, lt, mapId));
        
        // we'r finished...
        LOGGER.debug("MapShard: finished loading initial data");
    }
}