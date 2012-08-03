/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P088_CharacterCreateUpdateProfessionAndCampaignAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P130_CreateNewCharacterAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P132_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P141_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P378_UnknownAction;
import com.gamerevision.gwlpr.framework.database.DBCharacter;
import com.gamerevision.gwlpr.framework.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.MapShardStartupEvent;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreateAcknowledgeView;
import com.gamerevision.gwlpr.mapshard.views.UpdateAttribPtsView;
import com.gamerevision.gwlpr.mapshard.views.UpdateGenericValueIntView;
import com.gamerevision.gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GenericShardlet;
import com.realityshard.shardlet.Session;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    private DatabaseConnectionProvider connectionProvider;
    
    
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
        sendAction(UpdateGenericValueIntView.create(session, 64, 0));
        
        
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
    
    
    @EventHandler
    public void validateCreatedCharacterActionHandler(P132_UnknownAction action)
    {
        LOGGER.debug("got the validate created character packet");
        Session session = action.getSession();
        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
        
        P141_UnknownAction dAction = new P141_UnknownAction();
        dAction.init(session);
        dAction.setUnknown1((short) 248);
        sendAction(dAction);
        
        /*P381_UnknownAction mAction = new P381_UnknownAction();
        mAction.init(session);
        mAction.setUnknown1(29);
        sendAction(mAction);*/
        
        String characterName = new String(action.getUnknown1());
        byte[] appearance = action.getUnknown2();
        DBCharacter.createNewCharacter(connectionProvider, attachment.getAccountId(), characterName, appearance);

        
        P378_UnknownAction sAction = new P378_UnknownAction();
        sAction.init(session);
        sAction.setUnknown1(new byte[16]);
        sAction.setUnknown2(action.getUnknown1());
        sAction.setUnknown3((short) 81);
        
        ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 6);
        buffer.putShort((short) 248);
        buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});
        buffer.put(action.getUnknown2());
        buffer.put(new byte[16]);
        buffer.put(new byte[] {0, -1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
        byte[] a = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(a);
        sAction.setUnknown4(a);

        sendAction(sAction);
    }
    
    
    @EventHandler
    public void databaseConnectionProviderHandler(MapShardStartupEvent event)
    {
        this.connectionProvider = event.getConnectionProvider();
    }
}