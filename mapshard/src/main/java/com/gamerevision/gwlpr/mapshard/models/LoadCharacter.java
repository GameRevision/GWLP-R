/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;

import com.gamerevision.gwlpr.database.DBCharacter;
import com.gamerevision.gwlpr.database.DatabaseConnectionProvider;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.builders.PlayerBuilder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * Use this model to load up all the character data, and create the player
 * entity with it.
 *
 * TODO: COMPLETE ME!
 *
 * @author _rusty
 */
public class LoadCharacter
{

    private String charName;
    private int agentID;
    private int localID;

    private GWVector pos;
    private GWVector direction;
    private float boundsRectWidth;
    private float boundsRectHeight;
    private float playerHeight;

    private byte[] appearance;
    private float viewDistance;
    private boolean visible;


    /**
     * Constructor.
     *
     * @param       db
     * @param       charID
     * @param       mapSpawn
     */
    public LoadCharacter(DatabaseConnectionProvider db, int charID, GWVector mapSpawn)
    {
        // load the database representation of the character
        DBCharacter dBChar = DBCharacter.getCharacter(db, charID);

        // load the basic character identification data
        charName = dBChar.getName();
        agentID = IDManager.reserveAgentID();
        localID = IDManager.reserveLocalID();

        pos = mapSpawn;
        direction = new GWVector(1, 1, mapSpawn.getZPlane()); // change me!

        // no idea if these are OK:
        boundsRectWidth = 5;
        boundsRectHeight = 5;
        playerHeight = 7;

        // load appearance
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) ((dBChar.getSkin() << 5) | (dBChar.getHeight() << 1) | dBChar.getSex()));
        buffer.put((byte) ((dBChar.getFace() << 7) | (dBChar.getHaircolor() << 2) | (dBChar.getSkin() >> 3)));
        buffer.put((byte) ((dBChar.getPrimary() << 4) | (dBChar.getFace() >> 1)));
        buffer.put((byte) ((dBChar.getCampaign() << 6) | dBChar.getHairstyle()));
        appearance = buffer.array();

        // set some defaults
        viewDistance = 1000;
        visible = true;
    }


    /**
     * Runs the player-builder with the data we loaded when creating this object.
     *
     * @param       manager                 The entity manager.
     * @return      The created entity.
     */
    public Entity createPlayerEntityFor(EntityManager manager)
    {
        return PlayerBuilder
                .createFor(manager)
                .withAgentData(charName, agentID, localID)
                .withPhysics(pos, direction, boundsRectWidth, boundsRectHeight, playerHeight)
                .withVisuals(appearance, viewDistance, visible)
                .build();
    }


    /**
     * Getter.
     *
     * @return
     */
    public String getCharName()
    {
        return charName;
    }


    /**
     * Getter.
     *
     * @return
     */
    public int getAgentID()
    {
        return agentID;
    }


    /**
     * Getter.
     *
     * @return
     */
    public int getLocalID()
    {
        return localID;
    }
}
