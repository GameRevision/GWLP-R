/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.components;

import com.gamerevision.gwlpr.mapshard.entitysystem.Component;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.ChatColor;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementState;
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
        public String name = "NoName";
    }


    public static class AgentID implements Component {
        public int agentID = 0;
    }


    public static class LocalID implements Component {
        public int localID = 0;
    }


    public static class Position implements Component {
        public GWVector position; // absolute
    }


    public static class Direction implements Component {
        public GWVector direction = new GWVector(1, 1, 0);
        public boolean isRotating = false;
    }


    public static class Movement implements Component {
        public GWVector moveToPoint = new GWVector(1, 1, 0);
        public MovementType moveType = MovementType.Stop;
        public MovementState moveState = MovementState.NotMoving;
        public float speed = 288;
    }


    public static class BoundingBox implements Component {
        public float width = 50; // x
        public float depth = 50; // y
        public float height = 96; // height in game-units
    }


    public static class Appearance implements Component {
        public byte[] appearanceDump;
    }


    public static class View implements Component {
        public List<Entity> agentsICannotSee = new ArrayList<>();
        public List<Entity> agentsICanSee = new ArrayList<>();
        public float viewDistance = 100;
        public boolean isBlind = true; // this will be set to true by the builder or other classes.
    }


    public static class Visibility implements Component {
        public boolean visible = true;
    }


    public static class ChatOptions implements Component {
        public Collection<String> availableCommands = new ArrayList<>();
        public String chatPefix = "";
        public boolean prefixVisible = false;
        public ChatColor chatColor = ChatColor.Yellow_White;
        public boolean enableColor = false;
    }

    
    public static class NPCData implements Component {
        public int fileID = 0;
        public int texture = 0;
        public byte[] modelHash = new byte[] {};
        public int flags = 0;
        public int scale = 0;
    }
}
