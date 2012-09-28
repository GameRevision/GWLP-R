/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.realityshard.shardlet.ShardletContext;
import com.realityshard.shardlet.utils.GenericTriggerableAction;


/**
 * This view provides all classes inside the mapshard with the ability to 
 * communicate with the LoginShard.
 * 
 * @author miracle444
 */
public class LoginShardView
{
    /**
     * The parent ShardletContext.
     * this field has to be set by the ShardInitializer
     * to send event actions to the parent context.
     */
    private static ShardletContext loginShardContext;
    
    
    /**
     * Sends an action to the LoginShard. 
     * 
     * @param   action  the action to be sent to the loginShardContext
     * @return  true on success else false.
     */
    public static boolean sendAction(GenericTriggerableAction action)
    {
        // check if the LoginShardContext was set
        if (loginShardContext == null)
        {
            return false;
        }
        
        // if the Context exists propagate the message.
        loginShardContext.sendRemoteEventAction(action);
        
        return true;
    }

    
    /**
     * Used to set the working context of this class.
     * 
     * @param   context     the context this class will work on.
     */
    public static void SetLoginShardContext(ShardletContext context)
    {
        loginShardContext = context;
    }
}
