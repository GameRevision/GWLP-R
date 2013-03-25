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
 * @author miracle444, _rusty
 */
public class CharacterEntity
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(CharacterEntity.class);

    private String name;
    private int id;
    
    private byte skin;
    private byte sex;
    private byte height;
    private byte haircolor;
    private byte face;
    private byte hairstyle;
    private byte campaign;
    
    private byte primary;
    private byte secondary;
    
    
    /**
     * Constructor. 
     */
    private CharacterEntity(ResultSet resultSet)
    {
        try
        {
            this.name = resultSet.getString("Name");
            this.id = resultSet.getInt("ID");
            
            this.skin = resultSet.getByte("Skin");
            this.sex = resultSet.getByte("Sex");
            this.height = resultSet.getByte("Height");
            this.haircolor = resultSet.getByte("Haircolor");
            this.face = resultSet.getByte("Face");
            this.hairstyle = resultSet.getByte("Hairstyle");
            this.campaign = resultSet.getByte("Campaign");
            
            this.primary = resultSet.getByte("PrimaryProfession");
            this.secondary = resultSet.getByte("SecondaryProfession");
        }
        catch (SQLException ex)
        {
            LOGGER.error("SQL error in constructor", ex);
        }
    }
    
    
    /**
     * Creates a new character and stores it in the database.
     * 
     * @param       connectionProvider
     * @param       accountId
     * @param       characterName
     * @param       sex
     * @param       height
     * @param       skin
     * @param       haircolor
     * @param       face
     * @param       hairstyle
     * @param       campaign
     * @param       primary
     * @param       secondary
     * @return      The newly created character, or null if none was created.
     */
    public static CharacterEntity createNewCharacter(
            DatabaseConnectionProvider connectionProvider, 
            int accountId, 
            String characterName, 
            byte sex, 
            byte height, 
            byte skin, 
            byte haircolor, 
            byte face,
            byte hairstyle, 
            byte campaign,
            byte primary,
            byte secondary)
    {
        String query = "INSERT INTO characters (AccountID,Name,Sex,Height,Skin,Haircolor,Face,Hairstyle,Campaign,Primary,Secondary) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        
        int rows = 0;
            
        try (Connection connection = connectionProvider.getConnection(); 
             PreparedStatement ps = connection.prepareStatement(query))
        {
            ps.setInt(1, accountId);
            ps.setString(2, characterName);
            ps.setByte(3, sex);
            ps.setByte(4, height);
            ps.setByte(5, skin);
            ps.setByte(6, haircolor);
            ps.setByte(7, face);
            ps.setByte(8, hairstyle);
            ps.setByte(9, campaign);
            ps.setByte(10, primary);
            ps.setByte(11, secondary);
            rows = ps.executeUpdate();
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in create new character", ex);
        }
        
        if (rows != 1)
        {
            LOGGER.error("The character could not be created within the database!");
        }
        
        return getCharacter(connectionProvider, characterName);
    }
    
    
    /**
     * Factory method.
     * Returns all characters of a certain account.
     * 
     * @param       connectionProvider
     * @param       accountId
     * @return      The characters or an empty list.
     */
    public static List<CharacterEntity> getAllCharacters(DatabaseConnectionProvider connectionProvider, int accountId)
    {
        final List<CharacterEntity> result = new ArrayList<>();
        
        String query = "SELECT * FROM characters WHERE AccountID = ?";
        
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement preStm = connection.prepareStatement(query)) 
        {
            preStm.setInt(1, accountId);

            try (ResultSet resultSet = preStm.executeQuery())
            {
                while (resultSet.next())
                {
                    result.add(new CharacterEntity(resultSet));
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getAllCharacters", ex);
        }
        
        if (result.isEmpty())
        {
            LOGGER.error("No characters found for account with id: [{}]", accountId);
        }

        return result;
    }
    
    
    /**
     * Factory method.
     * Loads a character identified by its name.
     * 
     * @param       connectionProvider
     * @param       characterName
     * @return      The character, or null if none was found.
     */
    public static CharacterEntity getCharacter(DatabaseConnectionProvider connectionProvider, String characterName)
    {
        CharacterEntity result = null;
        
        String query = "SELECT * FROM characters WHERE Name = ?";
        

        try (Connection connection = connectionProvider.getConnection();
            PreparedStatement preStm = connection.prepareStatement(query))
        {
            preStm.setString(1, characterName);
            
            try (ResultSet resultSet = preStm.executeQuery())
            {
                if (resultSet.next())
                {
                    result = new CharacterEntity(resultSet);
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getCharacter", ex);
        }

        if (result == null)
        {
            LOGGER.error("We dont have any characters with name: [{}]", characterName);
        }
        
        return result;
    }
    
    
    /**
     * Factory method.
     * Loads a character identified by its ID.
     * 
     * @param       connectionProvider
     * @param       characterId
     * @return      The character or null if none was found.
     */
    public static CharacterEntity getCharacter(DatabaseConnectionProvider connectionProvider, int characterId)
    {
        CharacterEntity result = null;
        
        String query = "SELECT * FROM characters WHERE ID = ?";
        

        try (Connection connection = connectionProvider.getConnection();
            PreparedStatement preStm = connection.prepareStatement(query))
        {
            preStm.setInt(1, characterId);
            
            try (ResultSet resultSet = preStm.executeQuery())
            {
                if (resultSet.next())
                {
                    result = new CharacterEntity(resultSet);
                }
            }
        }
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in getCharacter", ex);
        }

        if (result == null)
        {
            LOGGER.error("We dont have any characters with ID: [{}]", characterId);
        }
        
        return result;
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
    public int getId()
    {
        return id;
    }
    

    /**
     * Getter.
     * 
     * @return 
     */
    public byte getSkin()
    {
        return skin;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getSex()
    {
        return sex;
    }
        
       
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getHeight()
    {
        return height;
    }
        
        
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getHaircolor()
    {
        return haircolor;
    }
        
        
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getFace()
    {
        return face;
    }
        
        
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getHairstyle()
    {
        return hairstyle;
    }
        
        
    /**
     * Getter.
     * 
     * @return 
     */
    public byte getCampaign()
    {
        return campaign;
    }
    

    /**
     * Getter.
     * 
     * @return 
     */
    public byte getPrimary() 
    {
        return primary;
    }


    /**
     * Getter.
     * 
     * @return 
     */
    public byte getSecondary() 
    {
        return secondary;
    }
    
    
    /**
     * Helper.
     */
    private static String bytesToHexString(byte[] bytes) 
    {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
      
        Formatter formatter = new Formatter(sb);  
        for (byte b : bytes) {  
            formatter.format("%02x", b);  
        }  
      
        return sb.toString();  
    }  

    
    
}
