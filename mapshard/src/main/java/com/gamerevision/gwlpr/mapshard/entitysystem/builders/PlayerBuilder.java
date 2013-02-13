/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.builders;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWRectangle;
import com.gamerevision.gwlpr.mapshard.models.GWVector;


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
                    new Movement()));
    }
    
    
    /**
     * Step 1.
     */
    public PlayerBuilder withAgentData(String playerName, int playerAgentID, int playerLocalID) 
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
    public PlayerBuilder withPhysics(
            GWVector playerPosition, 
            float playerRotation, 
            GWVector playerOrientation,
            float boundsRectWidth,
            float boundsRectHeight,
            float boundsPlayerHeight)
    {
        Position position = new Position(); position.position = playerPosition;
        Rotation rotation = new Rotation(); rotation.rotation = playerRotation;
        Orientation orientation = new Orientation(); orientation.orientation = playerOrientation;
        
        BoundingBox bBox = new BoundingBox();
        bBox.bounds = GWRectangle.createWithCenter(playerPosition, boundsRectWidth, boundsRectHeight);
        bBox.rotation = playerRotation;
        bBox.height = boundsPlayerHeight;
        
        entity.addAll(position, rotation, orientation, bBox);
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
     * Last Step.
     */
    public Entity build()
    {
        // nothing else to do here (currently)
        return entity;
    }
}
