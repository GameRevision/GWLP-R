/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.components;

import com.gamerevision.gwlpr.mapshard.entitysystem.Component;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementState;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;


/**
 * Until we need them in another way, this class will contain 
 * all components as static subclasses.
 * 
 * NOTE THAT THIS CLASS DOES NOT FOLLOW THE GENERAL CODE-STYLE,
 * FOR READABILITY REASONS!
 * 
 * @author _rusty
 */
public class Components 
{
    public static class Name implements Component {
        public String name;
    }

    
    public static class AgentID implements Component {
        public int agentID;
    }
    
    
    public static class LocalID implements Component {
        public int localID;
    }

    
    public static class Position implements Component {
        public float x;
        public float y;
    }
    
    
    public static class Rotation implements Component {
        public float rotation;
        public boolean isRotating;
    }
    
    
    public static class Orientation implements Component {
        public float vecX;
        public float vecY;
    }
    
    
    public static class Movement implements Component {
        public MovementState moveState;
        public MovementType moveType;
    }
    
    
    public static class BoundingBox implements Component {
        public float x;
        public float y;
        public float width;
        public float depth;
        public float height;
    }
    
    
    public static class Appearance implements Component {
        public byte[] appearanceDump; 
    }
    
    
    public static class View implements Component {
        public int[] agentsICannotSee;
        public int[] agentsICanSee;
        public float viewDistance;
    }
    
    
    public static class Visibility implements Component {
        public boolean visible;
    }
}
