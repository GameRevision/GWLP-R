/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.builders;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWVector;


/**
 * Use this class to create a new non-player-character entity.
 *
 * TODO: inherit from the player builder?
 *
 * @author _rusty
 */
public final class NPCBuilder
{

    private final Entity entity;


    /**
     * Constructor.
     */
    private NPCBuilder(Entity entity)
    {
        this.entity = entity;
    }


    /**
     * Start building process.
     */
    public static NPCBuilder createFor(EntityManager manager)
    {
        // create the entity, and add some default components
        return new NPCBuilder(
                new Entity(
                    manager,
                    new Movement()));
    }


    /**
     * Step 1.
     */
    public NPCBuilder withAgentData(String playerName, int playerAgentID, int playerLocalID)
    {
        Name name = new Name(); name.name = playerName;
        AgentID agentID = new AgentID(); agentID.agentID = playerAgentID;
        LocalID localID = new LocalID(); localID.localID = playerLocalID;

        entity.addAll(name, agentID, localID);
        return this;
    }


    /**
     * Step 2.
     */
    public NPCBuilder withPhysics(
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
    public NPCBuilder withVisuals(byte[] playerAppearance, float playerViewDistance, boolean isVisible)
    {
        Appearance appearance = new Appearance(); appearance.appearanceDump = playerAppearance;
        View view = new View(); view.viewDistance = playerViewDistance;
        Visibility visibility = new Visibility(); visibility.visible = isVisible;

        entity.addAll(appearance, view, visibility);
        return this;
    }


    /**
     * Step 4.
     *
     * TODO: change this method once this is supported by database features.
     */
    public NPCBuilder withChatOptions()
    {
        ChatOptions chatOpts = new ChatOptions();

        entity.addAll(chatOpts);
        return this;
    }


    /**
     * Step 5.
     */
    public NPCBuilder withNPCData(int fileID, byte[] modelHash, int flags, int scale)
    {
        NPCData npcData = new NPCData();
        npcData.fileID = fileID;
        npcData.modelHash = modelHash;
        npcData.flags = flags;
        npcData.scale = scale;

        entity.addAll(npcData);
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
