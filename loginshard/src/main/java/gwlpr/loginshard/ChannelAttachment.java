/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class is used to store account related data for a single session.
 * 
 * @author miracle444, _rusty
 */
public final class ChannelAttachment
{
    
    public static final AttributeKey<ChannelAttachment> KEY = new io.netty.util.AttributeKey<>(ChannelAttachment.class.getName());
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAttachment.class);
    private long loginCount;
    private int accountId;
    private int characterId;
    
    
    /**
     * Setter.
     *
     * @param loginCount
     */
    public void setLoginCount(long loginCount)
    {
        this.loginCount = loginCount;
    }
    
    
    /**
     * Getter.
     *
     * @return
     */
    public long getLoginCount()
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
     * Convenience method: static getter.
     * 
     * @param channel
     * @return 
     */
    public static long getLoginCount(Channel channel)
    {
        return channel.attr(KEY).get().getLoginCount();
    }
}