/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P088_CharacterCreateUpdateProfessionAndCampaignAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P130_CreateNewCharacterAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P132_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P141_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P378_UnknownAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.MapShardStartupEvent;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreateAcknowledgeView;
import com.gamerevision.gwlpr.mapshard.views.UpdateAttribPtsView;
import com.gamerevision.gwlpr.mapshard.views.UpdateGenericValue;
import com.gamerevision.gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericShardlet;
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
        // TODO: Fix agentID!
        sendAction(UpdateGenericValue.create(session, 50, UpdateGenericValue.Type.Unknown14, 0));
        
        
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
        
        String characterName = new String(action.getUnknown1());
        
        // if name is in use ....
        /*P381_UnknownAction mAction = new P381_UnknownAction();
        mAction.init(session);
        mAction.setUnknown1(29);
        sendAction(mAction);*/
        
        // if name is not in use ....
        
        // get character properties
        byte[] appearance = action.getUnknown2();
        byte sex = (byte) (appearance[0] & 1);
        byte height = (byte) ((appearance[0] >> 1) & 0xF);
        byte skin = (byte) (((appearance[0] >> 5) | (appearance[1] << 3)) & 0x1F);
        byte haircolor = (byte) ((appearance[1] >> 2) & 0x1F);
        byte face = (byte) (((appearance[1] >> 7) | (appearance[2] << 1)) & 0x1F);
        byte primary = (byte) ((appearance[2] >> 4) & 0xF);
        byte hairstyle = (byte) (appearance[3] & 0x1F);
        byte campaign = (byte) ((appearance[3] >> 6) & 3);
        
        DBCharacter.createNewCharacter(connectionProvider, attachment.getAccountId(), characterName,
                sex, height, skin, haircolor, face, primary, hairstyle, campaign);

        
        P378_UnknownAction sAction = new P378_UnknownAction();
        sAction.init(session);
        sAction.setUnknown1(new byte[16]);
        sAction.setUnknown2(action.getUnknown1());
        sAction.setUnknown3((short) 81);
        
        ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 6);
        buffer.putShort((short) 248);
        buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});
        
        buffer.put((byte) ((skin << 5) | (height << 1) | sex));
        buffer.put((byte) ((face << 7) | (skin >> 3)));
        buffer.put((byte) ((primary << 4) | (face >> 1)));
        buffer.put((byte) ((campaign << 6) | hairstyle));
        
        buffer.put(new byte[16]);

        byte level = 0; // TODO: replace this dummy variable
        buffer.put((byte) ((level << 4) | campaign));                                                   
        
        buffer.put(new byte[] {-1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
        byte[] a = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(a);
        sAction.setUnknown4(a);

        sendAction(sAction);
    }
    
    
    @EventHandler
    public void mapShardStartupEventHandler(MapShardStartupEvent event)
    {
        this.connectionProvider = event.getConnectionProvider();
    }
}