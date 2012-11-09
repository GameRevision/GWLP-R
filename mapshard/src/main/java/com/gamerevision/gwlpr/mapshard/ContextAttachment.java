/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.entitysystem.EntitySystemFacade;


/**
 * This class is used to store all that sutff used globally by all shardlets.
 * 
 * @author _rusty
 */
public class ContextAttachment 
{
    
    private final DatabaseConnectionProvider dbProvider;
    private final EntitySystemFacade esFacade;
    private final ClientLookupTable clientLookup;
    
    
    /**
     * Constructor.
     * 
     * @param       dbProvider              Database provider.
     * @param       esFacade                Entity System
     * @param       clientLookpup           Lookup dict for client/entities
     */
    public ContextAttachment(
            DatabaseConnectionProvider dbProvider,
            EntitySystemFacade esFacade,
            ClientLookupTable clientLookpup)
    {
        this.dbProvider = dbProvider;
        this.esFacade = esFacade;
        this.clientLookup = clientLookpup;
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
}
