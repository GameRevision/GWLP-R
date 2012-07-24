/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P088_CharacterCreateUpdateProfessionAndCampaignAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P130_CreateNewCharacterAction;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreateAcknowledgeView;
import com.gamerevision.gwlpr.mapshard.views.UpdateAttribPtsView;
import com.gamerevision.gwlpr.mapshard.views.UpdateGenericValueIntView;
import com.gamerevision.gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles all about character creation.
 * 
 * @author miracle444
 */
public class CharacterCreation extends GenericShardlet
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(CharacterCreation.class);
    
    
    @Override
    protected void init() 
    {
        LOGGER.debug("character creation shardlet initialized!");
    }
    
    
    @EventHandler
    public void createNewCharacterHandler(P130_CreateNewCharacterAction action)
    {
        LOGGER.debug("got the create new character packet");
        Session session = action.getSession();
        
        
        LOGGER.debug("sending update attribute points");
        sendAction(UpdateAttribPtsView.create(session));
        
        
        LOGGER.debug("sending update generic value integer");
        sendAction(UpdateGenericValueIntView.create(session));
        
        
        LOGGER.debug("sending create character acknowledgement");
        sendAction(CharacterCreateAcknowledgeView.create(session));
    }
    
    
    @EventHandler
    public void characterCreateUpdateProfessionAndCampaignActionHandler(P088_CharacterCreateUpdateProfessionAndCampaignAction action)
    {
        LOGGER.debug("got the character create update profession and campaign packet");
        Session session = action.getSession();
        
        
        LOGGER.debug("sending update private professions");
        sendAction(UpdatePrivateProfessionsView.create(session));
    }
}