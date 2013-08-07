/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.entityfactories;

import gwlpr.database.entities.Npc;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.models.IDManager;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.Profession;
import gwlpr.mapshard.models.enums.SpawnType;
import java.util.UUID;


/**
 * Use this to create a new npc entity.
 *
 * TODO: COMPLETE ME!
 *
 * @author _rusty
 */
public class NPCFactory 
{
    
    /**
     * Factory method.
     * 
     * @param dbNpc
     * @param mapSpawn
     * @param manager
     * @return 
     */
    public static Entity createNpc(Npc dbNpc, WorldPosition mapSpawn, EntityManager manager)
    {
        return createNpc(dbNpc.getName(), dbNpc.getFileID(), new int[] { dbNpc.getModel() }, dbNpc.getHashedName(), mapSpawn, manager);
    }
    
    
    /**
     * Factory method.
     *  
     * @param       npcName 
     * @param       fileId 
     * @param       modelHashes 
     * @param       hashedName 
     * @param       mapSpawn
     * @param       manager 
     * @return      A new character entity 
     */
    public static Entity createNpc(String npcName, int fileId, int[] modelHashes, String hashedName, WorldPosition mapSpawn, EntityManager manager)
    {
        Entity result = new Entity(UUID.randomUUID(), manager);
        
        // general identifiers
        Name name = new Name(); 
        name.name = npcName;
        
        AgentIdentifiers agentIDs = new AgentIdentifiers(); 
        agentIDs.agentID = IDManager.reserveAgentID();
        agentIDs.localID = IDManager.reserveLocalID();
        
        // physics
        Position position = new Position(); 
        position.position = mapSpawn.clone();
        
        Direction direction = new Direction();
        
        Movement move = new Movement();

        BoundingBox bBox = new BoundingBox();
        
        
        // appearance and view visuals       
        View view = new View();
        view.isBlind = false;
        
        Visibility visibility = new Visibility();
        
        // load some char data
        // TODO static data!
        CharData charData = new CharData();
        charData.primary = Profession.Mesmer;
        charData.secondary = Profession.None;
        charData.level = 1;
        
        FactionData faction = new FactionData();
        faction.spawnType = SpawnType.NPC;
        faction.factionColor = 0x20;
        
        // finally NPC specific stuff
        // TODO static data!
        NPCData npcData = new NPCData();
        npcData.fileID = fileId;
        npcData.modelHashes = modelHashes;
        npcData.flags = 4;
        npcData.scale = 100;
        npcData.hashedName = hashedName;
        
        // build the entity
        result.addAll(name, agentIDs, position, direction, move, bBox, view, visibility, charData, faction, npcData);
        
        return result;
    }
    
    
    public static Entity mockNpc(WorldPosition mapSpawn, EntityManager manager)
    {
        return createNpc("Blubb", 116228, new int[] { 0x026c4a }, String.copyValueOf(new char[] {0x8102, 0x5299, 0xc20f, 0xb939, 0x0463}), mapSpawn, manager);
    }
}
