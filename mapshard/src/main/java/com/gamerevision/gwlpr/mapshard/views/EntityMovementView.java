/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P026_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P030_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P032_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P035_UnknownAction;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.Components.*;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
import com.realityshard.shardlet.Session;


/**
 * This entity-view produces movement packets.
 *
 * @author _rusty
 */
public class EntityMovementView
{

    /**
     * This is send when an entity changes direction or starts moving.
     */
    public static void sendChangeDirection(Session session, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        GWVector dir = entity.get(Direction.class).direction;
        MovementType movT = entity.get(Movement.class).moveType;
        
        // constrcut the message
        P026_UnknownAction moveDir = new P026_UnknownAction();
        moveDir.init(session);
        moveDir.setUnknown1(agentID);
        moveDir.setUnknown2(dir.toFloatArray());
        moveDir.setUnknown3(movT.getVal());

        session.send(moveDir);
    }


    /**
     * This is send when we generally update an agents position, because
     * it moved (but didnt change direction)
     */
    public static void sendUpdateMovement(Session session, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        Movement move = entity.get(Movement.class);
        GWVector moveTo = move.futurePosition;
        MovementType movT = move.moveType;
        
        // send the messages
        P032_UnknownAction speedMod = new P032_UnknownAction();
        speedMod.init(session);
        speedMod.setUnknown1(agentID);
        speedMod.setUnknown2(movT.getSpeedModifier());
        speedMod.setUnknown3(movT.getVal());

        session.send(speedMod);

        // TODO check me: this is probably the place that the player would reach within
        // the next time step.
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
    public static void sendRotateAgent(Session session, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        GWVector dir = entity.get(Direction.class).direction;
        
        P035_UnknownAction rot = new P035_UnknownAction();
        rot.init(session);
        rot.setUnknown1(agentID);
        rot.setUnknown2(dir.toRotation());
        rot.setUnknown3(0x40060A92);

        session.send(rot);
    }
}
