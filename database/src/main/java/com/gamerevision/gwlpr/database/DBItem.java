/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
public class DBItem
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBItem.class);
    
    private DBItem(ResultSet resultSet)
    {

    }    
    
    private static List<DBItem> getItemsByInventory(DatabaseConnectionProvider connectionProvider, int characterId, int inventoryPage)
    {
        final List<DBItem> result = new ArrayList<>();
        
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM items WHERE CharacterID="+characterId+" AND InventoryPage="+inventoryPage+";");

            while (resultSet.next())
            {
                result.add(new DBItem(resultSet));
            }

            resultSet.close();
            stmt.close();
            connection.close();
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in getItemsByInventory");
        }

        return result;
    }
    
    public static List<DBItem> getBackpackItems(DatabaseConnectionProvider connectionProvider, int characterId)
    {
        return getItemsByInventory(connectionProvider, characterId, 0);
    }
    
    
    public static List<DBItem> getEquippedItems(DatabaseConnectionProvider connectionProvider, int characterId) 
    {
        return getItemsByInventory(connectionProvider, characterId, 1);
    }

}
