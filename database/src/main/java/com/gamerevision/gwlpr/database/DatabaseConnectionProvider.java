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
        
        
        /**
         * Constructor.
         * 
         * @param       ip
         * @param       port
         * @param       database
         * @param       username
         * @param       password 
         */
	public DatabaseConnectionProvider(
                final String ip, 
                final String port, 
                final String database,
                final String username, 
                final String password)
        {
		
		databaseConnectionInfo = "jdbc:mysql://" + ip + ":" + port + "/" + database +
                                    "?user=" + username + "&password=" + password;
                
                LOGGER.info("Using database: [{}]", databaseConnectionInfo);
                
		try 
                {
                    new Driver();
		} 
                catch (final SQLException ex) 
                {
                    LOGGER.error("Could not connect to the mysql database.", ex);
		}
	}

        
        /**
         * Tries to establish a new database connection.
         * 
         * @return      The db connection if successful, null if not.
         */
	public final Connection getConnection() 
        {
            Connection connection = null;
            
            try 
            {
                 connection = DriverManager.getConnection(databaseConnectionInfo);
            } 
            catch (SQLException ex)
            {
                LOGGER.error("Could not create new mysql connection.", ex);
            }
            
            if (connection == null)
            {
                LOGGER.error("Could not establish a db connection, but no exception caught!");
            }
            
            return connection;
        }
}