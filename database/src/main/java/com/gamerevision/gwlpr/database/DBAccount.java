/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
public class DBAccount
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBAccount.class);
    
    private int id;
    private String password;
    
    
    /*
     * Returns all login information (null if none exist).
     */
    private DBAccount(ResultSet resultSet)
    {
        try
        {
            this.id = resultSet.getInt("ID");
            this.password = resultSet.getString("Password");
        }
        catch (SQLException ex)
        {
            LOGGER.error("SQL error in constructor", ex);
        }
    }
    
    
    public int getId()
    {
        return id;
    }
    
    
    public String getPassword()
    {
        return password;
    }
    
    
    public static DBAccount getByEMail(DatabaseConnectionProvider connectionProvider, String eMail)
    {
        DBAccount result = null;
        
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM accounts WHERE EMail='"+eMail+"';");
            if (resultSet.next())
            {
                result = new DBAccount(resultSet);
            }
            resultSet.close();
            stmt.close();
            connection.close();
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getByEMail", ex);
        }
        
        return result;
    }
}
