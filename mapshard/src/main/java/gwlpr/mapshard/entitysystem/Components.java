/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem;

import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.Attribute;
import gwlpr.mapshard.models.enums.ChatColor;
import gwlpr.mapshard.models.enums.Faction;
import gwlpr.mapshard.models.enums.MovementType;
import gwlpr.mapshard.models.enums.MovementState;
import gwlpr.mapshard.models.enums.Profession;
import gwlpr.mapshard.models.enums.SpawnType;
import gwlpr.mapshard.models.enums.StandardValue;
import gwlpr.protocol.util.Vector2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


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
        public volatile String name = "";
    }


    public static class AgentIdentifiers implements Component {
        public volatile int agentID = 0;
        public volatile int localID = 0;
    }
    

    public static class Position implements Component {
        public volatile WorldPosition position; // absolute
    }


    public static class Direction implements Component {
        public volatile Vector2 direction = new Vector2(1, 1);
        public volatile float rotation = 0;
        public volatile boolean isRotating = false;
    }


    public static class Movement implements Component {
        public volatile WorldPosition moveAim = new WorldPosition(1, 1, 0);
        public volatile MovementType moveType = MovementType.Stop;
        public volatile MovementState moveState = MovementState.NotMoving;
        public volatile float speed = StandardValue.PlayerSpeed.getVal();
    }


    public static class BoundingBox implements Component {
        public volatile float width = 50; // x
        public volatile float depth = 50; // y
        public volatile float height = 96; // height in game-units
    }


    public static class PlayerAppearance implements Component {
        public volatile byte[] appearanceDump;
    }


    public static class View implements Component {
        public volatile Collection<Entity> visibleAgents = new HashSet<>();
        public volatile float viewDistance = 1000;
        public volatile boolean isBlind = true; // this will be set to false by the builder or other classes.
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
        public volatile int fileID = 0;
        public volatile int texture = 0;
        public volatile String hashedName = ""; 
        public volatile int[] modelHashes = new int[] {};
        public volatile int flags = 0;
        public volatile int scale = 0;
    }
    
    
    public static class SpawnData implements Component {
        public volatile SpawnType spawnType = SpawnType.Player;
        public volatile byte factionColor = 0x30; // TODO: enum pls. and find the colors. //npc=0x20, some other player=0x30
    }
    
    
    public static class CharData implements Component {
        public volatile Profession primary = Profession.Mesmer;
        public volatile Profession secondary = Profession.None;
        public volatile int level = 1;
        public volatile int morale = 100;
        public volatile int experience = 0;
        public volatile int attributeFreePts = 0;
        public volatile int attributeMaxPts = 0;
        public volatile Map<Attribute, Integer> attributePtsSpentOn = new HashMap<>();
    }
    
    
    public static class FactionData implements Component {
        public Map<Faction, Integer[]> factionPoints = new HashMap<>();
    }
    
    
    public static class Skills implements Component {
        public volatile List<Short> availableSkills = new ArrayList<>();
        public volatile List<Integer> skillbar = new ArrayList<>();
        public volatile List<Integer> pvpmask = new ArrayList<>();
    }
}
