/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class handles item related database access and provides independent
 * properties to encapsulate sql related code.
 * 
 * TODO: complete me!
 *
 * @author miracle444, _rusty
 */
public class ItemEntity
{

    private static Logger LOGGER = LoggerFactory.getLogger(ItemEntity.class);

    
    /**
     * Constructor.
     */
    private ItemEntity(ResultSet resultSet)
    {

    }

    
    /**
     * Factory method.
     * Load all items of a certain character's inventory page
     * 
     * @param       connectionProvider
     * @param       characterId
     * @param       inventoryPage
     * @return      The items, or an empty list if none were found.
     */
    private static List<ItemEntity> getItemsByInventory(DatabaseConnectionProvider connectionProvider, int characterId, int inventoryPage)
    {
        final List<ItemEntity> result = new ArrayList<>();
        
        String query = "SELECT * FROM items WHERE CharacterID = ? AND InventoryPage = ?";

        try
        {
            try (Connection connection = connectionProvider.getConnection();
                PreparedStatement preStm = connection.prepareStatement(query))
            {
                preStm.setInt(1, characterId);
                preStm.setInt(2, inventoryPage);
                
                try (ResultSet resultSet = preStm.executeQuery())
                {
                    while (resultSet.next())
                    {
                        result.add(new ItemEntity(resultSet));
                    }
                }
            }
        }
        catch (SQLException ex)
        {
            LOGGER.error("SQL error in getItemsByInventory", ex);
        }
        
        if (result.isEmpty())
        {
            LOGGER.error(String.format("We dont have any items for character [%s] with inventory-page [%s]", characterId, inventoryPage));
        }

        return result;
    }

    
    /**
     * Factory method.
     * Load all items of a certain character's backpack
     * 
     * @param       connectionProvider
     * @param       characterId
     * @return      The items, or an empty list if none were found.
     */
    public static List<ItemEntity> getBackpackItems(DatabaseConnectionProvider connectionProvider, int characterId)
    {
        return getItemsByInventory(connectionProvider, characterId, 0);
    }


    /**
     * Factory method.
     * Load all items of a certain character's equipped-items
     * 
     * @param       connectionProvider
     * @param       characterId
     * @return      The items, of an empty list if none were found.
     */
    public static List<ItemEntity> getEquippedItems(DatabaseConnectionProvider connectionProvider, int characterId)
    {
        return getItemsByInventory(connectionProvider, characterId, 1);
    }
}
