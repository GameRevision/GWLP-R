/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.controllers;

import gwlpr.protocol.gameserver.inbound.P054_MovementUpdate;
import gwlpr.protocol.gameserver.inbound.P055_GotoPostion;
import gwlpr.protocol.gameserver.inbound.P057_RotateAgent;
import gwlpr.protocol.gameserver.inbound.P064_MovementStop;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.events.RotateEvent;
import gwlpr.mapshard.events.MoveEvent;
import gwlpr.mapshard.events.StopMovingEvent;
import gwlpr.mapshard.models.WorldPosition;
import gwlpr.mapshard.models.enums.MovementState;
import gwlpr.mapshard.models.enums.MovementType;
import gwlpr.protocol.util.Vector2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;


/**
 * This manages incoming movement updates.
 * This also includes rotation and go-to-location
 *
 * @author _rusty
 */
public class MoveRotateClick
{

    private static Logger LOGGER = LoggerFactory.getLogger(MoveRotateClick.class);
    
    private final EventAggregator aggregator;
    
    
    /**
     * Constructor.
     * 
     * @param aggregator
     */
    public MoveRotateClick(EventAggregator aggregator)
    {
        this.aggregator = aggregator;
    }
    

    /**
     * Event handler.
     * Player has pressed a keyboard button to move.
     * 
     * @param       action 
     */
    @Event.Handler
    public void onKeyboardMove(P054_MovementUpdate action)
    {
        // TODO verify data like mapData.validPosition(pos):boolean

        ClientBean client = ClientBean.get(action.getChannel());
        
        Entity et = client.getEntity();
        Position pos = et.get(Position.class);
        Direction dir = et.get(Direction.class);
        Movement move = et.get(Movement.class);

        // extract info
        WorldPosition position = new WorldPosition(
                action.getPositionVector(),
                (int) action.getPositionPlane());

        Vector2 direction = action.getMoveDirection();

        MovementType moveType = MovementType.fromInt((int)action.getMovementType());
        
        // this should not be done directly:
        pos.position = position;
        dir.direction = direction;
        
        // TODO check if the client is still in sync!
        // else do nothing but send a position update packet
        
        // TODO DEBUG
        move.moveState = MovementState.MoveChangeDir;
        move.moveType = moveType;

        // produce an internal event. this might seem unnecessary, but another
        // module will handle the actual movement. this is just a front controller
        aggregator.triggerEvent(new MoveEvent(et, direction, moveType));

        // we could also set/update the entities position here...
        // but we need to be sure it is valid (inside the map and no teleport)
    }


    /**
     * Event handler.
     * Player has stopped moving around.
     *
     * @param   action
     */
    @Event.Handler
    public void onKeyboardStopMoving(P064_MovementStop action)
    {
        ClientBean client = ClientBean.get(action.getChannel());
        
        Entity et = client.getEntity();
        Position pos = et.get(Position.class);
        Movement move = et.get(Movement.class);

        // extract info
        WorldPosition position = new WorldPosition(
                action.getPositionVector(),
                (int) action.getPositionPlane());
        
        // this should not be done directly:
        pos.position = position;
        
        // TODO check if the client is still in sync!
        // else do nothing but send a position update packet
        
        // also update the movement state, it can be done right now with no harm
        move.moveState = MovementState.NotMoving;

        // the internal event:
        aggregator.triggerEvent(new StopMovingEvent(client.getEntity()));
    }


    /**
     * Event handler.
     * Player has pressed a keyboard button to move.
     * 
     * @param       action 
     */
    @Event.Handler
    public void onKeyboardRotate(P057_RotateAgent action)
    {
        ClientBean client = ClientBean.get(action.getChannel());
        
        float rot1 = Float.intBitsToFloat((int)action.getRotation1());
        float rot2 = Float.intBitsToFloat((int)action.getRotation2());

        // send internal event
        aggregator.triggerEvent(new RotateEvent(client.getEntity(), rot1, rot2));
    }


    /**
     * Event handler.
     * Player has clicked somewhere and we need to do the pathing...
     * (Or not, depending on the availability of any pathing system)
     *
     * @param       action
     */
    @Event.Handler
    public void onClickLocation(P055_GotoPostion action)
    {
        ClientBean client = ClientBean.get(action.getChannel());

        // TODO implement me!
        // we can also choose to ignore this, if the pathing stuff is too complex.
    }
}
