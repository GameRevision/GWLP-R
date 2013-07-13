/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.builders;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.Profession;
import com.gamerevision.gwlpr.mapshard.models.enums.SpawnType;


/**
 * Use this class to create a new player entity.
 *
 * @author _rusty
 */
public final class PlayerBuilder
{

    private final Entity entity;


    /**
     * Constructor.
     */
    private PlayerBuilder(Entity entity)
    {
        this.entity = entity;
    }


    /**
     * Start building process.
     */
    public static PlayerBuilder createFor(EntityManager manager)
    {
        // create the entity, and add some default components
        return new PlayerBuilder(
                new Entity(
                    manager,
                    new Movement(),
                    new ChatOptions()));
    }


    /**
     * Step 1.
     */
    public PlayerBuilder withAgentData(String playerName, int playerAgentID, int playerLocalID)
    {
        Name name = new Name(); name.name = playerName;
        AgentIdentifiers agentIDs = new AgentIdentifiers(); 
        agentIDs.agentID = playerAgentID;
        agentIDs.localID = playerLocalID;

        entity.addAll(name, agentIDs);
        return this;
    }


    /**
     * Step 2.
     */
    public PlayerBuilder withPhysics(
            GWVector playerPosition,
            GWVector playerDirection,
            float boundsRectWidth,
            float boundsRectHeight,
            float boundsPlayerHeight)
    {
        Position position = new Position(); position.position = playerPosition;
        Direction direction = new Direction(); direction.direction = playerDirection;

        BoundingBox bBox = new BoundingBox();
        bBox.width = boundsRectWidth;
        bBox.depth = boundsRectHeight;
        bBox.height = boundsPlayerHeight;

        entity.addAll(position, direction, bBox);
        return this;
    }


    /**
     * Step 3.
     */
    public PlayerBuilder withVisuals(byte[] playerAppearance, float playerViewDistance, boolean isVisible)
    {
        Appearance appearance = new Appearance(); appearance.appearanceDump = playerAppearance;
        View view = new View(); view.viewDistance = playerViewDistance;
        Visibility visibility = new Visibility(); visibility.visible = isVisible;

        entity.addAll(appearance, view, visibility);
        return this;
    }
    
    
    /**
     * Step 4.
     */
    public PlayerBuilder withCharData(Profession primary, Profession secondary, int level, int morale)
    {
        CharData charData = new CharData();
        charData.primary = primary;
        charData.secondary = secondary;
        charData.level = level;
        charData.morale = morale;
        
        entity.addAll(charData);
        return this;
    }
    
    
    /**
     * Step 5.
     */
    public PlayerBuilder withFactionData()
    {
        FactionData fd = new FactionData();
        fd.spawnType = SpawnType.Player;
        fd.factionColor = 0x30;
        
        entity.addAll(fd);
        return this;
    }


    /**
     * Last Step.
     */
    public Entity build()
    {
        // nothing else to do here (currently)
        return entity;
    }
}
