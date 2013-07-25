/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles all the actions of which we don't really process the data.
 * TODO: Check in case of strange errors, check if this shardlet is the cause.
 * 
 * @author miracle444, _rusty
 */
public class StaticReply extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(StaticReply.class);
    
    
    /**
     * Init this shardlet.
     */
    @Override
    protected void init() 
    {
        LOGGER.info("MapShard: init StaticReply controller");
    }
}
