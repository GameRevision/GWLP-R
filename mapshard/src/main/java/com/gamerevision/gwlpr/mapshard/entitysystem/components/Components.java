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
        public volatile String name = "NoName";
    }


    public static class AgentID implements Component {
        public volatile int agentID = 0;
    }


    public static class LocalID implements Component {
        public volatile int localID = 0;
    }


    public static class Position implements Component {
        public volatile GWVector position; // absolute
    }


    public static class Direction implements Component {
        public volatile GWVector direction = new GWVector(1, 1, 0);
        public volatile boolean isRotating = false;
    }


    public static class Movement implements Component {
        public volatile GWVector moveToPoint = new GWVector(1, 1, 0);
        public volatile MovementType moveType = MovementType.Stop;
        public volatile MovementState moveState = MovementState.NotMoving;
        public volatile float speed = 288;
    }


    public static class BoundingBox implements Component {
        public volatile float width = 50; // x
        public volatile float depth = 50; // y
        public volatile float height = 96; // height in game-units
    }


    public static class Appearance implements Component {
        public volatile byte[] appearanceDump;
    }


    public static class View implements Component {
        public volatile List<Entity> agentsICannotSee = new ArrayList<>();
        public volatile List<Entity> agentsICanSee = new ArrayList<>();
        public volatile float viewDistance = 100;
        public volatile boolean isBlind = true; // this will be set to true by the builder or other classes.
    }


    public static class Visibility implements Component {
        public volatile boolean visible = true;
    }


    public static class ChatOptions implements Component {
        public volatile Collection<String> availableCommands = new ArrayList<>();
        public volatile String chatPefix = "";
        public volatile boolean prefixVisible = false;
        public volatile ChatColor chatColor = ChatColor.Yellow_White;
        public volatile boolean enableColor = false;
    }

    
    public static class NPCData implements Component {
        public int fileID = 0;
        public int texture = 0;
        public byte[] modelHash = new byte[] {};
        public int flags = 0;
        public int scale = 0;
    }
}
