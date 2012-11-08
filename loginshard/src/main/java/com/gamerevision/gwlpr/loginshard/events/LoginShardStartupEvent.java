/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.events;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.realityshard.shardlet.Event;


/**
 * This event is used to supply interested shardlets with the connection
 * provider object.
 * 
 * @author miracle444, _rusty
 */
public class LoginShardStartupEvent implements Event
{
    
    private DatabaseConnectionProvider connectionProvider;
    
    
    /**
     * Constructor.
     * 
     * @param       connectionProvider      The data-base connection provider used
     *                                      by the classes that are reading/writing from the db.
     * 
     */
    public LoginShardStartupEvent(DatabaseConnectionProvider connectionProvider)
    {
        this.connectionProvider = connectionProvider;
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
}