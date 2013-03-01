/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models.enums;

/**
 * This enum holds the different spawning faction types,
 * that define the player, allies and mobs.
 * 
 * @author _rusty
 */
public enum SpawningFaction 
{
    Player              (1886151033), // "play"
    NPC                 (1852796515), // "nonc"
    Ally                (1634495609), // "ally"
    Mob                 (1836016128); // "mob" + 0x00
    
    private final int intString;

    private SpawningFaction(int intString) 
    {    
        this.intString = intString;
    }

    public int getIntString() { return intString; }
}
