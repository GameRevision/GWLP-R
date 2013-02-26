/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.entitysystem.systems;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.GenericSystem;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import com.gamerevision.gwlpr.mapshard.events.RotateEvent;
import com.gamerevision.gwlpr.mapshard.events.StartMovingEvent;
import com.gamerevision.gwlpr.mapshard.events.StopMovingEvent;
import com.gamerevision.gwlpr.mapshard.models.ClientLookupTable;
import com.gamerevision.gwlpr.mapshard.models.GWVector;
import com.gamerevision.gwlpr.mapshard.models.enums.MovementState;
import com.gamerevision.gwlpr.mapshard.views.MovementView;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import java.util.Collection;


/**
 * This system handles movement of entities, without collision.
 * Collision handling might be done in a separate system, though it could
 * be reasonable to merge both of these systems...
 *
 * @author _rusty
 */
public class MovementSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = -1; // use standard server tick interval

    private final EntityManager entityManager;
    private final ClientLookupTable lookupTable;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       entityManager
     */
    public MovementSystem(EventAggregator aggregator, EntityManager entityManager, ClientLookupTable lookupTable)
    {
        super(aggregator, UPDATEINTERVAL);

        this.entityManager = entityManager;
        this.lookupTable = lookupTable;
    }


    /**
     * This is invoked periodically. (Or never, depending on the interval)
     *
     * @param timeDelta
     */
    @Override
    protected void update(int timeDelta)
    {
        // get all moving entities
        Collection<Entity> entities = entityManager.getEntitiesWith(
                Position.class,
                Movement.class);

        for (Entity entity : entities)
        {
            // check if entity is not moving
            if (entity.get(Movement.class).moveState == MovementState.NotMoving)
            {
                continue;
            }
            
            GWVector pos = entity.get(Position.class).position;
            GWVector dir = entity.get(Direction.class).direction;

            // else we need to calculate the next position
            // (and actually test if it collides with any walls)
            // and update the entities position, (or trigger a collision event)
            
            // TODO check this:
            // do a quick calculation of the future position of the agent
            entity.get(Position.class).position = pos.add(dir.mul(0.001F * timeDelta));
        }
    }


    /**
     * Handles movement events.
     * This only concerns entities that just started moving or changed direction.
     *
     * @param startMove
     */
    @EventHandler
    public void onStartMoving(StartMovingEvent startMove)
    {
        // we need to inform the connected clients and
        // set the movement state to moving (if that has not yet happened)

        Entity et = startMove.getThisEntity();
        int agentID = et.get(AgentID.class).agentID;
        GWVector pos = et.get(Position.class).position;
        GWVector dir = startMove.getDirection();

        // update the entities values
        et.get(Direction.class).direction = dir;
        Movement move = et.get(Movement.class);
        move.moveType = startMove.getType();
        move.moveState = MovementState.Moving;

        // do a quick calculation of the future position of the agent
        GWVector futurePos = pos.add(dir.mul(0.001F * 250)); // some float multip with the server tick

        for (Session session : lookupTable.getAllSessions())
        {
            MovementView.sendChangeDirection(
                session,
                agentID,
                dir,
                move.moveType);

            // and send a move-update packet
            // note that this (and the future pos calc) should usually be done by the update loop.
            // this means that this
            MovementView.sendUpdateMovement(
                    session,
                    agentID,
                    futurePos,
                    move.moveType);
        }
    }


    /**
     * Handles entities that stopped moving.
     * Tells our clients.
     *
     * @param stopMove
     */
    public void onStopMoving(StopMovingEvent stopMove)
    {
        // we need to inform the connected clients and
        // set the movement state to notmoving

        // fetch some entity info
        Entity et = stopMove.getThisEntity();
        int agentID = et.get(AgentID.class).agentID;
        GWVector pos = et.get(Position.class).position;
        Movement move = et.get(Movement.class);
        move.moveState = MovementState.NotMoving;

        for (Session session : lookupTable.getAllSessions())
        {
            MovementView.sendUpdateMovement(
                    session,
                    agentID,
                    pos,
                    move.moveType);
        }
    }


    /**
     * Event handler.
     * Rotate event, this siganls that an entity changed its direction
     * while not moving.
     *
     * @param rot
     */
    public void onRotate(RotateEvent rot)
    {
        // fetch some entity info
        Entity et = rot.getThisEntity();
        int agentID = et.get(AgentID.class).agentID;
        
        // and update the direction
        Direction dir = et.get(Direction.class);
        dir.direction = rot.getNewDirection();
        float rotation = dir.direction.toRotation();

        for (Session session : lookupTable.getAllSessions())
        {
            MovementView.sendRotateAgent(
                    session,
                    agentID,
                    rotation);
        }
    }
}
