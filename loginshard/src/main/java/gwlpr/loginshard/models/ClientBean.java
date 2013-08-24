/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.models;

import gwlpr.database.entities.Account;
import gwlpr.database.entities.Character;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import realityshard.container.gameapp.GameAppContext;
import realityshard.container.util.Handle;


/**
 * This class is used to store account related data for a single client.
 * 
 * @author miracle444, _rusty
 */
public final class ClientBean
{
    /**
     * Used to access the client-handle attribute of a login session
     */
    public static final AttributeKey<Handle<ClientBean>> HANDLE_KEY = new AttributeKey<>(ClientBean.class.getName());

    /**
     * Needs to be extra so we can keep it attached to the channel even
     * if the client logs out
     */
    public static final AttributeKey<Integer> LOGIN_COUNT = new AttributeKey<>(ClientBean.class.getName()+"_performed_actions");
    
    private final Channel channel;
    private final Account account;
    
    private Character character;
    
    // the game server id is set if the client is connected to a game server.
    private Handle<GameAppContext> mapShardHandle;

    
    public ClientBean(Channel channel, Account acc, Character chara)
    {
        this.channel = channel;
        this.account = acc;
        this.character = chara;
    }
    
    
    /**
     * Convenience method. Use with a login channel only!
     * 
     * @param       channel
     * @return      The bean attached to it, or null.
     */
    public static ClientBean get(Channel channel)
    {
        return getHandle(channel).get();
    }
    
    
    /**
     * Convenience method. Use with a login channel only!
     * 
     * @param       channel
     * @return      The clientbean handle, or null.
     */
    public static Handle<ClientBean> getHandle(Channel channel)
    {
        return channel.attr(ClientBean.HANDLE_KEY).get();
    }
    
    
    /**
     * Convenience method. Set the channel's bean-handle.
     * 
     * @param       channel
     * @param       handle 
     */
    public static void set(Channel channel, Handle<ClientBean> handle)
    {
        channel.attr(ClientBean.HANDLE_KEY).set(handle);
    }
    
    
    public Channel getChannel() 
    {
        return channel;
    }
    
    
    public Account getAccount() 
    {
        return account;
    }
    
    
    public long getLoginCount()
    {
        return channel.attr(ClientBean.LOGIN_COUNT).get();
    }
    
    
    public static long getLoginCount(Channel channel)
    {
        return channel.attr(ClientBean.LOGIN_COUNT).get();
    }
    
    
    public void setLoginCount(long loginCount) 
    {
        channel.attr(ClientBean.LOGIN_COUNT).set((int)loginCount);
    }
    
    
    public static void setLoginCount(Channel channel, long loginCount) 
    {
        channel.attr(ClientBean.LOGIN_COUNT).set((int)loginCount);
    }
    

    public Character getCharacter() 
    {
        return character;
    }
    

    public void setCharacter(Character character) 
    {
        this.character = character;
    }
    
    
    public Handle<GameAppContext> getMapShardHandle() 
    {
        return mapShardHandle;
    }

    
    public void setMapShardHandle(Handle<GameAppContext> mapShardHandle) 
    {
        this.mapShardHandle = mapShardHandle;
    }
}