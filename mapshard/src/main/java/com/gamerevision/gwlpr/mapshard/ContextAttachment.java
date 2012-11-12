/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.entitysystem.EntitySystemFacade;
import com.realityshard.shardlet.RemoteShardletContext;


/**
 * This class is used to store all that sutff used globally by all shardlets.
 * 
 * @author _rusty
 */
public class ContextAttachment 
{
    
    private final RemoteShardletContext loginShard;
    private final DatabaseConnectionProvider dbProvider;
    private final EntitySystemFacade esFacade;
    private final ClientLookupTable clientLookup;
    private final int mapId;
    
    
    /**
     * Constructor.
     * 
     * @param loginShard 
     * @param       dbProvider              Database provider.
     * @param       esFacade                Entity System
     * @param       clientLookpup           Lookup dict for client/entities
     * @param mapId  
     */
    public ContextAttachment(
            RemoteShardletContext loginShard,
            DatabaseConnectionProvider dbProvider,
            EntitySystemFacade esFacade,
            ClientLookupTable clientLookpup,
            int mapId)
    {
        this.loginShard = loginShard;
        this.dbProvider = dbProvider;
        this.esFacade = esFacade;
        this.clientLookup = clientLookpup;
        this.mapId = mapId;
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
    public EntitySystemFacade getEntitySystem() 
    {
        return esFacade;
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
     * @return      The MapID of this mapshard, as used in the DB
     */
    public int getMapId() 
    {
        return mapId;
    }
}
