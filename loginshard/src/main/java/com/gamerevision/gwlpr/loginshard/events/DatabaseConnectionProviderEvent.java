/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.events;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.realityshard.shardlet.Event;


/**
 * This event is used to supply interested shardlets with the connection
 * provider object.
 * 
 * @author miracle444
 */
public class DatabaseConnectionProviderEvent implements Event
{
    
    
    private DatabaseConnectionProvider connectionProvider;
    
    public DatabaseConnectionProviderEvent(DatabaseConnectionProvider connectionProvider)
    {
        this.connectionProvider = connectionProvider;
    }
    
    public DatabaseConnectionProvider getConnectionProvider()
    {
        return this.connectionProvider;
    }
}