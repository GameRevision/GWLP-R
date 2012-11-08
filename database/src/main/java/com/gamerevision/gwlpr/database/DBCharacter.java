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
    private byte skin;
    private byte sex;
    private byte height;
    private byte haircolor;
    private byte face;
    private byte primaryProfession;
    private byte hairstyle;
    private byte campaign;
    
    
    private DBCharacter(ResultSet resultSet)
    {
        try
        {
            this.name = resultSet.getString("Name");
            this.skin = resultSet.getByte("Skin");
            this.sex = resultSet.getByte("Sex");
            this.height = resultSet.getByte("Height");
            this.haircolor = resultSet.getByte("Haircolor");
            this.face = resultSet.getByte("Face");
            this.primaryProfession = resultSet.getByte("PrimaryProfession");
            this.hairstyle = resultSet.getByte("Hairstyle");
            this.campaign = resultSet.getByte("Campaign");
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
        
        
    public byte getPrimaryProfession()
    {
        return primaryProfession;
    }
        
        
    public byte getHairstyle()
    {
        return hairstyle;
    }
        
        
    public byte getCampaign()
    {
        return campaign;
    }
    
    
    public static boolean createNewCharacter(DatabaseConnectionProvider connectionProvider, int accountId, 
            String characterName, byte sex, byte height, byte skin, byte haircolor, byte face, 
            byte primaryProfession, byte hairstyle, byte campaign)
    {
        try
        {
            Connection connection = connectionProvider.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO characters (AccountID,Name,Sex,Height,Skin,Haircolor,Face,PrimaryProfession,Hairstyle,Campaign) VALUES (?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, accountId);
            ps.setString(2, characterName);
            ps.setByte(3, sex);
            ps.setByte(4, height);
            ps.setByte(5, skin);
            ps.setByte(6, haircolor);
            ps.setByte(7, face);
            ps.setByte(8, primaryProfession);
            ps.setByte(9, hairstyle);
            ps.setByte(10, campaign);
            int rows = ps.executeUpdate();
            ps.close();
            connection.close();

            return rows == 1;
        } 
        catch (SQLException ex) 
        {
            LOGGER.error("sql error in create new character");
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
