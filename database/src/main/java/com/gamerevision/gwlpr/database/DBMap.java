/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class handles map related database access and provides independent
 * properties to encapsulate sql related code.
 * 
 * @author _rusty
 */
public class DBMap 
{
    
    /**
     * Dataholder.
     */
    public static class DBSpawn
    {
        public float posX; // can't use GWVector, to avoid strange dependencies...
        public float posY;
        public int planeZ;
        
        public int radius;
        
        public DBSpawn(ResultSet resultSet)
        {
            try
            {
                this.posX= resultSet.getFloat("X");
                this.posY = resultSet.getFloat("Y");
                this.planeZ = resultSet.getInt("Plane");
                this.radius = resultSet.getInt("Radius");
            }
            catch (SQLException ex)
            {
                LOGGER.error("SQL error in constructor", ex);
            }
        }
    }
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBMap.class);
     
    private int id;
    private int gameId;
    private int hash;
    private String name;

    private boolean isPvP;
    
    private Collection<DBSpawn> spawns;
    
    
    /**
     * Constrcutor.
     */
    private DBMap(ResultSet resultSet, Collection<DBSpawn> spawns)
    {
        try
        {
            // retrieve the map info from the db resultset
            this.id = resultSet.getInt("ID");
            this.gameId= resultSet.getInt("GameID");
            this.hash = resultSet.getInt("Hash");
            this.name = resultSet.getString("Name");
            this.isPvP = resultSet.getBoolean("PvP");
            
            // set the spawnpoints
            this.spawns = spawns;
        }
        catch (SQLException ex)
        {
            LOGGER.error("SQL error in constructor", ex);
        }
    }
    
    
    /**
     * Factory method. Loads mapdata from the database into a new dbmap object.
     * 
     * @param       connectionProvider      The database connection prov.
     * @param       id                      The ID (database internal) of the map.
     * @return      The map with all needed data, or null if none was found.
     */
    public DBMap getByDbId(DatabaseConnectionProvider connectionProvider, int id)
    {
        DBMap result = null;
        Collection<DBSpawn> spawns = getSpawnsById(connectionProvider, id);
       
        String queryMap = "SELECT * FROM maps WHERE ID = ?";
        
        try (Connection connection = connectionProvider.getConnection(); 
             PreparedStatement preStm = connection.prepareStatement(queryMap))
        {
            preStm.setInt(1, id);

            try (ResultSet resultSet = preStm.executeQuery())
            {
                if (resultSet.next())
                {
                    result = new DBMap(resultSet, spawns);
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getByEMail", ex);
        }
        
        if (result == null)
        {
            LOGGER.error("We dont have any maps with id: [{}]", id);
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
    public int getGameId() 
    {
        return gameId;
    }

    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getHash() 
    {
        return hash;
    }
    

    /**
     * Getter.
     * 
     * @return 
     */
    public String getName() 
    {
        return name;
    }


    /**
     * Getter.
     * 
     * @return 
     */
    public boolean isIsPvP() 
    {
        return isPvP;
    }


    /**
     * Getter.
     * 
     * @return 
     */
    public Collection<DBSpawn> getSpawns() 
    {
        return Collections.unmodifiableCollection(spawns);
    }
    
    
    /**
     * Helper.
     */
    private Collection<DBSpawn> getSpawnsById(DatabaseConnectionProvider connectionProvider, int id)
    {
        Collection<DBSpawn> result = new ArrayList<>();
        
        String query = "SELECT * FROM spawns WHERE MapID = ?";
        
        try (Connection connection = connectionProvider.getConnection(); 
             PreparedStatement preStmSpawns = connection.prepareStatement(query)) 
        {
            preStmSpawns.setInt(1, id);

            try (ResultSet resultSet = preStmSpawns.executeQuery())
            {
                while (resultSet.next())
                {
                    spawns.add(new DBSpawn(resultSet));
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getSpawnsById", ex);
        }
        
        if (result.isEmpty())
        {
            LOGGER.error("We dont have any spawns for map with id: [{}]", id);
        }
        
        return result;
    }
}
