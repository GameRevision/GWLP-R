/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard;

import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;


/**
 * This class is used to store all that sutff used globally by all shardlets.
 * 
 * @author _rusty
 */
public class ContextAttachment 
{
    
    private final DatabaseConnectionProvider dbProvider;
    
    
    /**
     * Constructor.
     * 
     * @param       dbProvider              Database provider.
     */
    public ContextAttachment(DatabaseConnectionProvider dbProvider)
    {
        this.dbProvider = dbProvider;
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
}
