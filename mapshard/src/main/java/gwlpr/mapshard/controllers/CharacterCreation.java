/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.database.entities.Character;
import gwlpr.database.jpa.CharacterJpaController;
import gwlpr.database.jpa.ProfessionJpaController;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.HandleRegistryNotificationDecorator;
import gwlpr.mapshard.models.enums.ErrorCode;
import gwlpr.mapshard.models.enums.GenericValue;
import gwlpr.mapshard.models.enums.PlayerState;
import gwlpr.mapshard.views.CharacterCreationView;
import gwlpr.mapshard.views.EntityUpdateAttributesView;
import gwlpr.mapshard.views.UpdateGenericValueView;
import gwlpr.mapshard.views.EntityUpdatePrivateProfessionsView;
import gwlpr.protocol.gameserver.inbound.P088_CharacterCreateUpdateProfessionAndCampaign;
import gwlpr.protocol.gameserver.inbound.P130_CreateNewCharacter;
import gwlpr.protocol.gameserver.inbound.P132_ValidateCreatedCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;


/**
 * This handles all about character creation.
 *
 * TODO: review, clean up, refactor me! - this just needs to be able to somehow save the data it gets :P
 *
 * @author miracle444, _rusty
 */
public class CharacterCreation
{

    private static Logger LOGGER = LoggerFactory.getLogger(CharacterCreation.class);
    
    
    /**
     * Event handler.
     * Some other component registered a client with the local client registry
     * 
     * This means we can start the character process now, if the
     * character is actually in the character creation state.
     * 
     * @param event 
     */
    @Event.Handler
    public void onClientRegistered(HandleRegistryNotificationDecorator.Registered<ClientBean> event)
    {
        if (event.getHandle().get().getPlayerState() == PlayerState.CreatingCharacter)
        {
            LOGGER.debug("Starting character creation");
            CharacterCreationView.charCreateHead(event.getHandle().get().getChannel());
        }
    }
    

    /**
     * Event handler.
     *
     * @param action
     */
    @Event.Handler
    public void onCreateNewCharacter(P130_CreateNewCharacter action)
    {
        LOGGER.debug("Got the create new character packet");

        // and start the char creation process stuff
        // TODO: Fix agentID if necessary
        EntityUpdateAttributesView.sendInitial(action.getChannel(), 1, 0, 0);
        UpdateGenericValueView.send(action.getChannel(), 50, GenericValue.Unknown3, 0);
        CharacterCreationView.charCreateAck(action.getChannel());
    }


    /**
     * Event handler.
     *
     * @param action
     */
    @Event.Handler
    public void onCharacterCreateUpdateProfessionAndCampaign(P088_CharacterCreateUpdateProfessionAndCampaign action)
    {
        LOGGER.debug("Got the character create update profession and campaign packet");
        
        EntityUpdatePrivateProfessionsView.send(action.getChannel(), 1, action.getProfession(), 0, false);
    }


    /**
     * Event handler.
     *
     * @param action
     */
    @Event.Handler
    public void onValidateCreatedCharacter(P132_ValidateCreatedCharacter action)
    {
        LOGGER.debug("Got the validate created character packet");

        // extract the session and attachment...
        ClientBean client = ClientBean.get(action.getChannel());

        // extract the data of the new char
        String characterName = action.getCharName();

        // get character properties
        byte[] appearance = action.getAppearanceAndProfession();
        
        byte sex = (byte) (appearance[0] & 1);
        byte height = (byte) ((appearance[0] >> 1) & 0xF);
        byte skin = (byte) (((appearance[0] >> 5) | (appearance[1] << 3)) & 0x1F);
        byte haircolor = (byte) ((appearance[1] >> 2) & 0x1F);
        byte face = (byte) (((appearance[1] >> 7) | (appearance[2] << 1)) & 0x1F);
        byte hairstyle = (byte) (appearance[3] & 0x1F);
        byte campaign = (byte) ((appearance[3] >> 6) & 3);

        // extract the professions
        byte primary = (byte) ((appearance[2] >> 4) & 0xF);
        byte secondary = 0;

        // perform some unkown action...
        CharacterCreationView.unkownStep1(action.getChannel());

        Character chara = CharacterJpaController.get().findByName(characterName);
        
        // if name is in use ....
        if (chara != null)
        {
            CharacterCreationView.error(action.getChannel(), ErrorCode.CCNameInUse);
        }

        // if name is not in use create a new char in the db
        Character dbChar = new Character();
        dbChar.setName(characterName);
        dbChar.setAccountID(client.getAccount());
        dbChar.setSex((short)sex);
        dbChar.setHeight((short)height);
        dbChar.setHeight((short)skin);
        dbChar.setFace((short)face);
        dbChar.setHaircolor((short)haircolor);
        dbChar.setHairstyle((short)hairstyle);
        dbChar.setCampaign((short)campaign);
        dbChar.setPrimaryProfession(ProfessionJpaController.get().findProfession((int)primary));
        
        // create it in the db
        CharacterJpaController.get().create(chara);
        
        // get the new object with all the up-to-date info
        dbChar = CharacterJpaController.get().findByName(characterName);
        
        if (dbChar != null)
        {
            CharacterCreationView.charCreateFinish(action.getChannel(), dbChar);
            return;
        }

        LOGGER.error("Char creation failed!");

        // kick the client if the character could not be created
        action.getChannel().close();
    }
}