/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P088_CharacterCreateUpdateProfessionAndCampaignAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P130_CreateNewCharacterAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P132_UnknownAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.ContextAttachment;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.views.CharacterCreationView;
import com.gamerevision.gwlpr.mapshard.views.UpdateAttribPtsView;
import com.gamerevision.gwlpr.mapshard.views.UpdateGenericValueView;
import com.gamerevision.gwlpr.mapshard.views.UpdatePrivateProfessionsView;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.events.GameAppCreatedEvent;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet handles all about character creation.
 *
 * TODO: review, clean up, refactor me!
 *
 * @author miracle444, _rusty
 */
public class CharacterCreation extends GenericShardlet
{

    private static Logger LOGGER = LoggerFactory.getLogger(CharacterCreation.class);

    private DatabaseConnectionProvider db;


    /**
     * Init this shardlet.
     */
    @Override
    protected void init()
    {
        LOGGER.info("MapShard: init CharacterCreation controller.");
    }


    /**
     * Executes startup features, like storing database references etc.
     *
     * @param event
     */
    @EventHandler
    public void onStartUp(GameAppCreatedEvent event)
    {
        // this event indicates that all shardlets have been loaded (including
        // the startup shardlet) so we can safely use the context attachment now.

        db = ((ContextAttachment) getShardletContext().getAttachment()).getDatabaseProvider();
    }


    /**
     * Event handler.
     *
     * @param action
     */
    @EventHandler
    public void onCreateNewCharacter(P130_CreateNewCharacterAction action)
    {
        LOGGER.debug("Got the create new character packet");

//        // extract the session...
//        Session session = action.getSession();
//
//        // and start the char creation process stuff
//        UpdateAttribPtsView.send(session);
//        // TODO: Fix agentID!
//        UpdateGenericValueView.send(session, 50, UpdateGenericValueView.Type.Unknown14, 0);
//        CharacterCreationView.charCreateAck(session);
    }


    /**
     * Event handler.
     *
     * @param action
     */
    @EventHandler
    public void onCharacterCreateUpdateProfessionAndCampaign(P088_CharacterCreateUpdateProfessionAndCampaignAction action)
    {
        LOGGER.debug("Got the character create update profession and campaign packet");

//        // extract the session...
//        Session session = action.getSession();
//
//        UpdatePrivateProfessionsView.send(session);
    }


    /**
     * Event handler.
     *
     * @param action
     */
    @EventHandler
    public void onValidateCreatedCharacter(P132_UnknownAction action)
    {
        LOGGER.debug("Got the validate created character packet");

//        // extract the session and attachment...
//        Session session = action.getSession();
//        SessionAttachment attachment = (SessionAttachment) session.getAttachment();
//
//        // extract the data of the new char
//        String characterName = new String(action.getUnknown1());
//
//        // get character properties
//        byte[] appearance = action.getUnknown2();
//        byte sex = (byte) (appearance[0] & 1);
//        byte height = (byte) ((appearance[0] >> 1) & 0xF);
//        byte skin = (byte) (((appearance[0] >> 5) | (appearance[1] << 3)) & 0x1F);
//        byte haircolor = (byte) ((appearance[1] >> 2) & 0x1F);
//        byte face = (byte) (((appearance[1] >> 7) | (appearance[2] << 1)) & 0x1F);
//        byte hairstyle = (byte) (appearance[3] & 0x1F);
//        byte campaign = (byte) ((appearance[3] >> 6) & 3);
//
//        // extract the professions
//        byte primary = (byte) ((appearance[2] >> 4) & 0xF);
//        byte secondary = 0;
//
//        // perform some unkown action...
//        CharacterCreationView.unkownStep1(session);
//
//        // TODO WTF?
//            // if name is in use ....
//            CharacterCreationView.abort(session);
//
//        // if name is not in use create a new char in the db
//        DBCharacter dbChar = DBCharacter.createNewCharacter(
//                db,
//                attachment.getAccountId(),
//                characterName,
//                sex, height, skin, haircolor, face, hairstyle, campaign,
//                primary,
//                secondary);
//
//        if (dbChar != null)
//        {
//            CharacterCreationView.charCreateFinish(session, dbChar);
//            return;
//        }
//
//        LOGGER.error("MapShard: new character could not be created!");
//
//        // kick the client if the character could not be created
//        session.invalidate();
    }
}