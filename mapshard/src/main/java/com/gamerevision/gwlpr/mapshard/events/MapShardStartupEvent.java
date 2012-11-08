/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.events;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.realityshard.entitysystem.EntitySystemFacade;
import com.realityshard.shardlet.Event;


/**
 * This event is used to supply interested shardlets with the globally
 * shared references, e.g. of the database provider or EntitySystem
 * 
 * @author miracle444, _rusty
 */
public final class MapShardStartupEvent implements Event
{
    
    private DatabaseConnectionProvider connectionProvider;
    private final EntitySystemFacade entitySystem;
    private final ClientLookupTable clientTable;
    
    
    /**
     * Constructor.
     * 
     * @param       connectionProvider      The data-base connection provider used
     *                                      by the classes that are reading/writing from the db.
     * 
     */
    public MapShardStartupEvent(DatabaseConnectionProvider connectionProvider, EntitySystemFacade entitySystem, ClientLookupTable clientTable)
    {
        this.connectionProvider = connectionProvider;
        this.entitySystem = entitySystem;
        this.clientTable = clientTable;
    }
    
    
    /**
     * Getter.
     * 
     * @return      The database connection provider object that this game app uses
     */
    public DatabaseConnectionProvider getConnectionProvider()
    {
        return this.connectionProvider;
    }

    
    /**
     * Getter.
     * 
     * @return      The entity system object that this game app uses
     */
    public EntitySystemFacade getEntitySystem() 
    {
        return entitySystem;
    }

    
    /**
     * Getter.
     * 
     * @return      The client lookup table that this game app uses 
     */
    public ClientLookupTable getClientTable() 
    {
        return clientTable;
    }
}