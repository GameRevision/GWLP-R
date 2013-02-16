/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.components;

import com.gamerevision.gwlpr.mapshard.entitysystem.Component;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.models.GWRectangle;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.ChatColor;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementState;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
        public GWVector position; // absolute
    }


    public static class Rotation implements Component {
        public float rotation;
        public boolean isRotating;
    }


    public static class Orientation implements Component {
        public GWVector orientation;
    }


    public static class Movement implements Component {
        public MovementState moveState = MovementState.Stop;
        public MovementType moveType = MovementType.NotMoving;
        public float speed = 255;
    }


    public static class BoundingBox implements Component {
        public GWRectangle bounds;
        public float rotation; // if the rect needs to be rotated
        public float height; // height (idk if this is equivalent to Z-Planes)
    }


    public static class Appearance implements Component {
        public byte[] appearanceDump;
    }


    public static class View implements Component {
        public List<Entity> agentsICannotSee = new ArrayList<>();
        public List<Entity> agentsICanSee = new ArrayList<>();
        public float viewDistance;
    }


    public static class Visibility implements Component {
        public boolean visible = true;
    }


    public static class ChatOptions implements Component {
        public Collection<String> availableCommands;
        public String chatPefix;
        public boolean prefixVisible;
        public ChatColor chatColor;
        public boolean enableColor;
    }
}
