/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.mapshard.views.LoginShardView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.intershardcom.ParentContextEventAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet is used to initialize the MapShard.
 * 
 * @author miracle444
 */
public class ShardInitializer extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(ShardInitializer.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("shard initializer shardlet initialized!");
    }
    
    
    /**
     * This handler is invoked by the api when the context is created
     * to be able to send actions to the parent context.
     * 
     * @param   action  the action carrying the context information.
     */
    @EventHandler
    public void parentContextEventActionHandler(ParentContextEventAction action)
    {
        LOGGER.debug("setting the parent context");
        LoginShardView.SetLoginShardContext(action.getParent());
    }
}