/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.models.MapData;
import com.realityshard.shardlet.RemoteShardletContext;


/**
 * This class is used to store all that stuff used globally by all shardlets.
 * 
 * @author _rusty
 */
public class ContextAttachment 
{
    
    private final RemoteShardletContext loginShard;
    private final DatabaseConnectionProvider dbProvider;
    private final EntityManager entityManager;
    private final ClientLookupTable clientLookup;
    private final MapData mapData;
    
    
    /**
     * Constructor.
     * 
     * @param       loginShard 
     * @param       dbProvider              Database provider.
     * @param       entityManager           Entity System
     * @param       clientLookpup           Lookup dict for client/entities
     * @param       mapData                 The general map info/data
     */
    public ContextAttachment(
            RemoteShardletContext loginShard,
            DatabaseConnectionProvider dbProvider,
            EntityManager entityManager,
            ClientLookupTable clientLookpup,
            MapData mapData)
    {
        this.loginShard = loginShard;
        this.dbProvider = dbProvider;
        this.entityManager = entityManager;
        this.clientLookup = clientLookpup;
        this.mapData = mapData;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public RemoteShardletContext getLoginShard() 
    {
        return loginShard;
    }

    
    /**
     * Getter.
     * 
     * @return      The globally used database connection provider. This object is
     *              needed to access the db.
     */
    public DatabaseConnectionProvider getDatabaseProvider() 
    {
        return dbProvider;
    }

    
    /**
     * Getter.
     * 
     * @return      The entity system facade, used to access this shards entity system.
     */
    public EntityManager getEntitySystem() 
    {
        return entityManager;
    }

    
    /**
     * Getter.
     * 
     * @return      The client lookup table, used to connect clients with their
     *              entities.
     */
    public ClientLookupTable getClientLookup() 
    {
        return clientLookup;
    }

    
    /**
     * Getter.
     * 
     * @return      The general map data of this mapshard
     */
    public MapData getMapData() 
    {
        return mapData;
    }
}
