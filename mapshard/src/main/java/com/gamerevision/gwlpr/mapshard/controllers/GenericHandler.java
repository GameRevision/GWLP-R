/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A generic handler should handle all actions that have to be handled but
 * we don't really process the data.
 * 
 * @author _rusty
 */
public class GenericHandler extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(GenericHandler.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("generic handler shardlet initialized!");
    }
}
