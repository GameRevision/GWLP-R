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
 * TODO: good god! please refactor this crap!
 * 
 * @author miracle444
 */
public class DBCharacter
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DBCharacter.class);

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
    
    
    private DBCharacter(ResultSet resultSet)
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
    
    
    public String getName()
    {
        return name;
    }
    
    
    public int getId()
    {
        return id;
    }
    

    public byte getSkin()
    {
        return skin;
    }
    
    
    public byte getSex()
    {
        return sex;
    }
        
        
    public byte getHeight()
    {
        return height;
    }
        
        
    public byte getHaircolor()
    {
        return haircolor;
    }
        
        
    public byte getFace()
    {
        return face;
    }
        
        
    public byte getHairstyle()
    {
        return hairstyle;
    }
        
        
    public byte getCampaign()
    {
        return campaign;
    }
    

    public byte getPrimary() 
    {
        return primary;
    }


    public byte getSecondary() 
    {
        return secondary;
    }
    
    
    public static DBCharacter createNewCharacter(
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
        try
        {
            Connection connection = connectionProvider.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO characters (AccountID,Name,Sex,Height,Skin,Haircolor,Face,Hairstyle,Campaign,Primary,Secondary) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            
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
            
            int rows = ps.executeUpdate();
            ps.close();
            connection.close();

            if (rows == 1)
            {
                return getCharacter(connectionProvider, characterName);
            }
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("SQL error in create new character", ex);
        }
        
        return null;
    }
    
    
    private static String bytesToHexString(byte[] bytes) 
    {  
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
            LOGGER.error("SQL error in getAllCharacters", ex);
        }

        return result;
    }
    
    
    public static DBCharacter getCharacter(DatabaseConnectionProvider connectionProvider, String characterName)
    {
        DBCharacter result = null;
        
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM characters WHERE Name='"+characterName+"';");

            if (resultSet.next())
            {
                result = new DBCharacter(resultSet);
            }

            resultSet.close();
            stmt.close();
            connection.close();
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
    
    public static DBCharacter getCharacter(DatabaseConnectionProvider connectionProvider, int characterId)
    {
        DBCharacter result = null;
        
        try
        {
            Connection connection = connectionProvider.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM characters WHERE ID="+characterId+";");

            if (resultSet.next())
            {
                result = new DBCharacter(resultSet);
            }

            resultSet.close();
            stmt.close();
            connection.close();
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in getCharacter", ex);
        }

        if (result == null)
        {
            LOGGER.error("We dont have any characters with ID: [{}]", characterId);
        }
        
        return result;
    }
}
