/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P026_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P030_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P032_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P035_UnknownAction;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
import com.realityshard.shardlet.Session;


/**
 * This view produces movement packets.
 *
 * @author _rusty
 */
public class MovementView
{

    /**
     * This is send when an entity changes direction or starts moving.
     */
    public static void sendChangeDirection(Session session, int agentID, GWVector dir, MovementType type)
    {
        P026_UnknownAction moveDir = new P026_UnknownAction();
        moveDir.init(session);
        moveDir.setUnknown1(agentID);
        moveDir.setUnknown2(dir.toFloatArray());
        moveDir.setUnknown3(type.getVal());

        session.send(moveDir);
    }


    /**
     * This is send when we generally update an agents position, because
     * it moved (but didnt change direction)
     */
    public static void sendUpdateMovement(Session session, int agentID, GWVector moveTo, MovementType type)
    {
        P032_UnknownAction speedMod = new P032_UnknownAction();
        speedMod.init(session);
        speedMod.setUnknown1(agentID);
        speedMod.setUnknown2(type.getSpeedModifier());
        speedMod.setUnknown3(type.getVal());

        session.send(speedMod);

        // TODO check me: We assume that this is the orientation of the player
        // while moving, i.e. When a player is blocked by a wall, it faces the wall
        // but moves sideways, this would
        // Another theory is that this is actually the direction of movement,
        // while the MovementDirection packet sets the player's orientation
        // this might especially be true because we additionally transmit the plane
        // with this packet. (which is not needed for orientation)
        P030_UnknownAction moveToPoint = new P030_UnknownAction();
        moveToPoint.init(session);
        moveToPoint.setUnknown1(agentID);
        moveToPoint.setUnknown2(moveTo.toFloatArray());
        moveToPoint.setUnknown3(moveTo.getZPlane());
        moveToPoint.setUnknown4((short)0);

        session.send(moveToPoint);
    }


    /**
     * Update an agents rotation
     * (The rotation is the direction actually, there might be differences but who cares
     * though this could be a possible BUG)
     */
    public static void sendRotateAgent(Session session, int agentID, float rotation)
    {
        P035_UnknownAction rot = new P035_UnknownAction();
        rot.init(session);
        rot.setUnknown1(agentID);
        rot.setUnknown2(rotation);
        rot.setUnknown3(0x40060A92);

        session.send(rot);
    }
}
