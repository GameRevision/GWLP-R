/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.controllers;

import com.gamerevision.gwlpr.actions.gameserver.ctos.P054_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P055_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P057_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.ctos.P064_UnknownAction;
import com.gamerevision.gwlpr.mapshard.SessionAttachment;
import com.gamerevision.gwlpr.mapshard.events.StartMovingEvent;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementType;
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

        // extract all the necessary info from the action and convert it
        GWVector position = GWVector.fromFloatArray(
                keybMove.getUnknown1(),
                keybMove.getUnknown2());

        GWVector direction = GWVector.fromFloatArray(
                keybMove.getUnknown3(),
                0);

        MovementType moveType = MovementType.fromInt(keybMove.getUnknown4());

        // produce an internal event. this might seem unnecessary, but another
        // module will handle the actual movement. this is just a front controller
        publishEvent(new StartMovingEvent(attach.getEntity(), direction, moveType));

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

        // TODO implement me!
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

        // TODO implement me!
        // new event?
        // what system handles this? we one though, NPCs can rotate as well
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
