/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.model.database;

import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class handles account related database access and provides independent
 * properties to encapsulate sql related code.
 * 
 * @author miracle444
 */
public class Account
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(Account.class);
    
    private String password;
    
    /*
     * Returns all login information (null if none exist).
     */
    public Account(ResultSet resultSet)
    {
        try
        {
            this.password = resultSet.getString("Password");
        }
        catch (SQLException ex)
        {
            LOGGER.error("sql error in constructor");
        }
    }
    
    
    public String getPassword()
    {
        return password;
    }
    
    
    public static Account getByEMail(DatabaseConnectionProvider connectionProvider, String eMail)
    {
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM accounts WHERE EMail='"+eMail+"';");
            if (resultSet.next())
            {
                return new Account(resultSet);
            }
            resultSet.close();
            stmt.close();
            connection.close();
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in getByEMail");
        }
        
        return null;
    }
}
