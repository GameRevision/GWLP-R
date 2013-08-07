/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.database.MapEntity;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import gwlpr.mapshard.ContextAttachment;
import gwlpr.mapshard.models.ClientLookupTable;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.systems.AgentVisibilitySystem;
import gwlpr.mapshard.entitysystem.systems.ChatSystem;
import gwlpr.mapshard.entitysystem.systems.CommandSystem;
import gwlpr.mapshard.entitysystem.systems.MovementSystem;
import gwlpr.mapshard.entitysystem.systems.SchedulingSystem;
import gwlpr.mapshard.entitysystem.systems.SpawningSystem;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.MapData;
import realityshard.shardlet.EventAggregator;
import realityshard.shardlet.RemoteShardletContext;
import realityshard.shardlet.utils.GenericShardlet;
import java.util.ArrayList;
import java.util.Collection;
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
        int tick = this.getShardletContext().getHeartBeatInterval();

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
        MapData md = loadMapData(db, mapId);

        // finally, create the context attachment
        getShardletContext().setAttachment(
                new ContextAttachment(ls, db, es, lt, md));

        // we'r almost finished...
        // load up the systems
        EventAggregator agg = getShardletContext().getAggregator();
        agg.register(new AgentVisibilitySystem(agg, es));
        agg.register(new ChatSystem(agg, lt));
        agg.register(new CommandSystem(agg, es, lt));
        agg.register(new MovementSystem(agg, es, lt));
        agg.register(new SchedulingSystem(agg));
        agg.register(new SpawningSystem(agg, lt));

        LOGGER.debug("Finished loading initial data");
    }


    /**
     * Helper. Generates the map data we need for this mapshard.
     *
     * @param       dbMapID                 The map ID as used for the db records.
     * @return      The completed map data.
     */
    private MapData loadMapData(DatabaseConnectionProvider db, int dbMapID)
    {
        MapEntity dbMap = MapEntity.getByDbId(db, dbMapID);
        Collection<WorldPosition> convertedSpawns = new ArrayList<>();

        for (MapEntity.DBSpawn dbSpawn : dbMap.getSpawns()) 
        {
            convertedSpawns.add(new WorldPosition(dbSpawn.posX, dbSpawn.posY, dbSpawn.planeZ));
        }

        return new MapData(dbMap.getId(), dbMap.getGameId(), dbMap.getHash(), convertedSpawns);
    }
}