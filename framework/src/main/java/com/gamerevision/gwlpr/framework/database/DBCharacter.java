/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class handles character related database access and provides independent
 * properties to encapsulate sql related code.
 * 
 * @author miracle444
 */
public class DBCharacter
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBCharacter.class);
    
    private String name;
    private byte[] appearance;
    
    
    private DBCharacter(ResultSet resultSet)
    {
        try
        {
            this.name = resultSet.getString("Name");
            this.appearance = resultSet.getBytes("Appearance");
        }
        catch (SQLException ex)
        {
            LOGGER.error("sql error in constructor");
        }
    }
    
    
    public String getName()
    {
        return name;
    }
    
    
    public byte[] getAppearance()
    {
        return appearance;
    }
    
    
    public static boolean createNewCharacter(DatabaseConnectionProvider connectionProvider, int accountId, String name, byte[] appearance)
    {
        try
        {
            Connection connection = connectionProvider.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO characters ( AccountID, Name, Appearance )  VALUES (?,?,?)");
            ps.setInt(1, accountId);
            ps.setString(2, name);
            ps.setBytes(3, appearance);
            int rows = ps.executeUpdate();
            ps.close();
            connection.close();

            return rows == 1;
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in getByEMail");
        }
        
        return false;
    }
    
    private static String bytesToHexString(byte[] bytes) {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
      
        Formatter formatter = new Formatter(sb);  
        for (byte b : bytes) {  
            formatter.format("%02x", b);  
        }  
      
        return sb.toString();  
    }  

    
    public static List<DBCharacter> getAllCharacters(DatabaseConnectionProvider connectionProvider, int accountId)
    {
        final List<DBCharacter> result = new ArrayList<>();
        
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM characters WHERE AccountID="+accountId+";");

            while (resultSet.next())
            {
                result.add(new DBCharacter(resultSet));
            }

            resultSet.close();
            stmt.close();
            connection.close();
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in getByEMail");
        }

        return result;
    }
}
