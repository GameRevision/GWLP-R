/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard;

import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class describes the attachment of a session inside the login shard.
 * 
 * @author miracle444
 */
public class SessionAttachment
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(SessionAttachment.class);
    private int loginCount;
    private int accountId;
    private String characterName;
    
    
    /**
     * Setter.
     * 
     * @param loginCount
     */
    public void setLoginCount(int loginCount)
    {
        this.loginCount = loginCount;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getLoginCount()
    {
        return loginCount;
    }
    
    
    /**
     * Setter.
     * 
     * @param accountId 
     */
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public int getAccountId()
    {
        return accountId;
    }
    
    
    /**
     * Setter.
     * 
     * @param characterName 
     */
    public void setCharacterName(String characterName)
    {
        this.characterName = characterName;
    }
    
    
    /**
     * Getter.
     * 
     * @return 
     */
    public String getCharacterName()
    {
        return characterName;
    }
    
    
    /**
     * Static getter.
     * 
     * @param session
     * @return 
     */
    public static int getLoginCount(Session session)
    {
        return ((SessionAttachment) session.getAttachment()).getLoginCount();
    }
}