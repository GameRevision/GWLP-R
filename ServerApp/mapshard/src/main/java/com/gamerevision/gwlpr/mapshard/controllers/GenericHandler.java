/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.realityshard.shardlet.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles the handshake process for GW clients.
 * It establishes an encrypted session.
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
