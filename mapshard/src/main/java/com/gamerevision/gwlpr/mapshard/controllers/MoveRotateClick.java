/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P054_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P055_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P057_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P064_UnknownAction;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.Components.*;
import com.gamerevision.gwlpr.mapshard.events.RotateEvent;
import com.gamerevision.gwlpr.mapshard.events.MoveEvent;
import com.gamerevision.gwlpr.mapshard.events.StopMovingEvent;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementState;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
import com.gamerevision.gwlpr.mapshard.models.enums.StandardValue;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.utils.GenericShardlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This shardlet manages incoming movement updates.
 * This also includes rotation and go-to-location
 *
 * @author _rusty
 */
public class MoveRotateClick extends GenericShardlet
{

    private static Logger LOGGER = LoggerFactory.getLogger(MoveRotateClick.class);


    /**
     * Init this shardlet.
     */
    @Override
    protected void init()
    {
        LOGGER.info("MapShard: init Movement controller");
    }


    /**
     * Event handler.
     * Player has pressed a keyboard button to move.
     */
    @EventHandler
    public void onKeyboardMove(P054_UnknownAction keybMove)
    {
        // TODO verify data like mapData.validPosition(pos):boolean

        Session session = keybMove.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        Entity et = attach.getEntity();
        Position pos = et.get(Position.class);
        Movement move = et.get(Movement.class);

        // extract all the necessary info from the action and convert it
        GWVector position = GWVector.fromFloatArray(
                keybMove.getUnknown1(),
                keybMove.getUnknown2());

        GWVector direction = GWVector.fromFloatArray(
                keybMove.getUnknown3(),
                0);

        MovementType moveType = MovementType.fromInt(keybMove.getUnknown4());
        
        // TODO: check of this position calculation is correct...
        // check if the client has a reasonable position (is near our calculated future position)
        // in that case, update the position of the internal client representation
        float dist = position.getDistanceTo(move.futurePosition);
        
        // TODO DEBUG
        if (true || dist <= StandardValue.RangeAdjacent.getVal())
        {
            pos.position = position;
            //move.futurePosition = position;
        }
        
        // TODO DEBUG
        move.moveState = MovementState.MoveChangeDir;
        move.moveType = moveType;

        // produce an internal event. this might seem unnecessary, but another
        // module will handle the actual movement. this is just a front controller
        publishEvent(new MoveEvent(et, direction, moveType));

        // we could also set/update the entities position here...
        // but we need to be sure it is valid (inside the map and no teleport)
    }


    /**
     * Event handler.
     * Player has stopped moving around.
     *
     * @param stopMove
     */
    @EventHandler
    public void onKeyboardStopMoving(P064_UnknownAction stopMove)
    {
        Session session = stopMove.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();
        Entity et = attach.getEntity();
        Position pos = et.get(Position.class);
        Movement move = et.get(Movement.class);

        // extract info
        GWVector position = GWVector.fromFloatArray(
                stopMove.getUnknown1(),
                stopMove.getUnknown2());
        
        // TODO: check of this position calculation is correct...
        // check if the client has a reasonable position (is near our calculated future position)
        // in that case, update the position of the internal client representation
        float dist = position.getDistanceTo(move.futurePosition);
        
        // TODO DEBUG
        if (true || dist <= StandardValue.RangeAdjacent.getVal())
        {
            // set the position directly, as the char will not be moving anymore anyway
            pos.position = position;
        }
        
        // also update the movement state, it can be done right now with no harm
        move.moveState = MovementState.NotMoving;

        // the internal event:
        publishEvent(new StopMovingEvent(attach.getEntity()));
    }


    /**
     * Event handler.
     * Player has pressed a keyboard button to move.
     */
    @EventHandler
    public void onKeyboardRotate(P057_UnknownAction keybRot)
    {
        Session session = keybRot.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();

        // extract info
        GWVector direction = GWVector.fromRotation(keybRot.getUnknown1(), 1);

        // send internal event
        publishEvent(new RotateEvent(attach.getEntity(), direction));
    }


    /**
     * Event handler.
     * Player has clicked somewhere and we need to do the pathing...
     * (Or not, depending on the availability of any pathing system)
     *
     * @param clickLoc
     */
    @EventHandler
    public void onClickLocation(P055_UnknownAction clickLoc)
    {
        Session session = clickLoc.getSession();
        SessionAttachment attach = (SessionAttachment) session.getAttachment();

        // TODO implement me!
        // we can also choose to ignore this, if the pathing stuff is too complex.
    }
}
