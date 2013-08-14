/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P026_AgentMoveDirection;
import gwlpr.protocol.gameserver.outbound.P030_AgentMoveToPoint;
import gwlpr.protocol.gameserver.outbound.P032_SpeedModifier;
import gwlpr.protocol.gameserver.outbound.P035_AgentRotate;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.MovementType;
import gwlpr.protocol.util.Vector2;
import io.netty.channel.Channel;


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
    public static void sendChangeDirection(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        Vector2 dir = entity.get(Direction.class).direction;
        MovementType movT = entity.get(Movement.class).moveType;
        
        // construct the message
        P026_AgentMoveDirection moveDir = new P026_AgentMoveDirection();
        moveDir.init(channel);
        moveDir.setAgentID(agentID);
        moveDir.setDirection(dir);
        moveDir.setMovementType(movT.getVal());

        channel.writeAndFlush(moveDir);
    }


    /**
     * This is send when we generally update an agents position, because
     * it moved (but didnt change direction)
     */
    public static void sendUpdateMovement(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        Movement move = entity.get(Movement.class);
        WorldPosition curPos = entity.get(Position.class).position;
        WorldPosition moveTo = move.moveAim;
        MovementType movT = move.moveType;
        
        // send the messages
        P032_SpeedModifier speedMod = new P032_SpeedModifier();
        speedMod.init(channel);
        speedMod.setAgentID(agentID);
        speedMod.setModifier(movT.getSpeedModifier());
        speedMod.setMovementType(movT.getVal());

        channel.writeAndFlush(speedMod);

        // TODO check me: this is probably the place that the player would reach within
        // the next time step.
        P030_AgentMoveToPoint moveToPoint = new P030_AgentMoveToPoint();
        moveToPoint.init(channel);
        moveToPoint.setAgentID(agentID);
        moveToPoint.setMoveAim(moveTo);
        moveToPoint.setCurrentPlane(curPos.getZPlane());
        moveToPoint.setNextPlane(moveTo.getZPlane());

        channel.writeAndFlush(moveToPoint);
    }


    /**
     * Update an agents rotation
     * (The rotation is the direction actually, there might be differences but who cares
     * though this could be a possible BUG)
     */
    public static void sendRotateAgent(Channel channel, Entity entity, float cos, float sin)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        
        P035_AgentRotate rot = new P035_AgentRotate();
        rot.init(channel);
        rot.setAgent(agentID);
        rot.setRotation1(Float.floatToRawIntBits(cos));
        rot.setRotation2(Float.floatToRawIntBits(sin));//0x40060A92);

        channel.writeAndFlush(rot);
    }
}
