/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.entityfactories;

import gwlpr.database.entities.Character;
import gwlpr.database.entities.Command;
import gwlpr.database.entities.Usergroup;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.models.IDManager;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.ChatColor;
import gwlpr.mapshard.models.enums.Profession;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Use this to create a new character entity.
 *
 * TODO: COMPLETE ME!
 *
 * @author _rusty
 */
public class CharacterFactory
{

    /**
     * Factory method.
     * 
     * @param       identifier 
     * @param       dBChar 
     * @param       mapSpawn
     * @param       manager 
     * @return      A new character entity 
     */
    public static Entity createCharacter(UUID identifier, Character dBChar, WorldPosition mapSpawn, EntityManager manager)
    {
        Entity result = new Entity(identifier, manager);
        
        // general identifiers
        Name name = new Name(); 
        name.name = dBChar.getName();
        
        AgentIdentifiers agentIDs = new AgentIdentifiers(); 
        agentIDs.agentID = IDManager.reserveAgentID();
        agentIDs.localID = IDManager.reserveLocalID();
        
        // physics
        Position position = new Position(); 
        position.position = mapSpawn.clone();
        
        Direction direction = new Direction();
        
        Movement move = new Movement();

        BoundingBox bBox = new BoundingBox();
        
        
        
        // chat
        ChatOptions chat = new ChatOptions();
        Usergroup group = dBChar.getAccountID().getUserGroup();
        if (group != null)
        {
            chat.chatPefix = group.getPrefix();
            chat.prefixVisible = true;
            
            chat.chatColor = ChatColor.values()[group.getChatColor()];
            chat.enableColor = true;

            List<String> availCommands = new ArrayList<>();
            for (Command command : group.getCommandCollection()) 
            {
                availCommands.add(command.getName());
            }

            chat.availableCommands = availCommands;
        }
        
        // appearance and view visuals
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) ((dBChar.getSkin().byteValue() << 5) | (dBChar.getHeight().byteValue() << 1) | dBChar.getSex().byteValue()));
        buffer.put((byte) ((dBChar.getFace().byteValue() << 7) | (dBChar.getHaircolor().byteValue() << 2) | (dBChar.getSkin().byteValue() >> 3)));
        buffer.put((byte) ((dBChar.getPrimaryProfession().getId().byteValue() << 4) | (dBChar.getFace().byteValue() >> 1)));
        buffer.put((byte) ((dBChar.getCampaign().byteValue() << 6) | dBChar.getHairstyle().byteValue()));

        PlayerAppearance appearance = new PlayerAppearance(); 
        appearance.appearanceDump = buffer.array();
        
        View view = new View();
        
        Visibility visibility = new Visibility();
        
        // load some char data
        CharData charData = new CharData();
        charData.primary = Profession.values()[dBChar.getPrimaryProfession().getId()];
        int secondary = dBChar.getSecondaryProfession() == null ? 0 : dBChar.getSecondaryProfession().getId();
        charData.secondary = Profession.values()[secondary];
        // TODO: fix the level!!!
        charData.level = 1;//dBChar.getLevel().getLevel();
        // TODO: load the attribute stuff here
        
        SpawnData spawnData = new SpawnData();
        
        FactionData factionData = new FactionData();
        // TODO: load the faction stuff here
        
        Skills skills = new Skills();
        // TODO: load the skills stuff here
        
        // build the entity
        result.addAll(name, agentIDs, position, direction, move, bBox, chat, appearance, view, visibility, charData, spawnData, factionData, skills);
        
        return result;
    }
}
