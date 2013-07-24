/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used to store account related data for a single session.
 * 
 * @author miracle444, _rusty
 */
public class SessionAttachment
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(SessionAttachment.class);
    private int loginCount;
    private int accountId;
    private int characterId;
    
    
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
     * @param accountId 
     */
    public void setAccountId(int accountId) 
    {
        this.accountId = accountId;
    }
    
    
    /**
     * Setter.
     *
     * @param characterId
     */
    public void setCharacterId(int characterId)
    {
        this.characterId = characterId;
    }
    
    
    /**
     * Getter.
     *
     * @return
     */
    public int getCharacterId()
    {
        return characterId;
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