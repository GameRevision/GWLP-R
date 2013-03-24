/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class handles account related database access and provides independent
 * properties to encapsulate sql related code.
 * 
 * @author miracle444, _rusty
 */
public class DBAccount
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBAccount.class);
    
    private int id;
    private String password;
    
    
    /**
     * Constructor.
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
    
    
    /**
     * Factory method.
     * Loads an account from the db and creates a new dbaccount object.
     * 
     * @param       connectionProvider
     * @param       eMail
     * @return      The new DBAccount, or null if none was found.
     */
    public static DBAccount getByEMail(DatabaseConnectionProvider connectionProvider, String eMail)
    {
        DBAccount result = null;
        
        String query = "SELECT * FROM accounts WHERE EMail = ?";        

        try (Connection connection = connectionProvider.getConnection(); 
             PreparedStatement preStm = connection.prepareStatement(query)) 
        {
            preStm.setString(1, eMail);
            
            try (ResultSet resultSet = preStm.executeQuery())
            {
                if (resultSet.next())
                {
                    result = new DBAccount(resultSet);
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getByEMail", ex);
        }
        
        if (result == null)
        {
            LOGGER.error("We dont have any accounts with email: [{}]", eMail);
        }
        
        return result;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getId()
    {
        return id;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public String getPassword()
    {
        return password;
    }
}
