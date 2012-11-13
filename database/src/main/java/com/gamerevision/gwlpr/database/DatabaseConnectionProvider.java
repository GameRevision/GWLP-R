/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides access to gameapp specific database connections.
 */
public final class DatabaseConnectionProvider
{
    
	private final String databaseConnectionInfo;
        private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionProvider.class);
        
        
	public DatabaseConnectionProvider(final String ip, final String port, final String database,
                                final String username, final String password)
        {
		
		databaseConnectionInfo = "jdbc:mysql://" + ip + ":" + port + "/" + database +
                                    "?user=" + username + "&password=" + password;
                
		try 
                {
                    new Driver();
		} 
                catch (final SQLException e) 
                {
                    LOGGER.error("could not connect to the mysql database.");
		}
	}

        
	public final Connection getConnection() 
        {
            Connection connection = null;
            
            try 
            {
                 connection = DriverManager.getConnection(databaseConnectionInfo);
            } 
            catch (SQLException ex)
            {
                LOGGER.error("could not create new mysql connection.");
            }
            
            return connection;
        }
}