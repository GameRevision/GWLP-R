/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.entitysystem.systems;

import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.entitysystem.EntityManager;
import gwlpr.mapshard.entitysystem.GenericSystem;
import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.events.RotateEvent;
import gwlpr.mapshard.events.MoveEvent;
import gwlpr.mapshard.events.StopMovingEvent;
import gwlpr.mapshard.models.ClientBean;
import gwlpr.mapshard.models.enums.MovementState;
import gwlpr.mapshard.models.enums.MovementType;
import gwlpr.mapshard.views.EntityMovementView;
import gwlpr.protocol.util.Vector2;
import java.util.Collection;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This system handles movement of entities, without collision.
 * Collision handling might be done in a separate system, though it could
 * be reasonable to merge both of these systems...
 * 
 * Hint: Make sure to use a unit-vector as the direction of the player
 * before calculating anything.
 *
 * @author _rusty
 */
public class MovementSystem extends GenericSystem
{

    private final static int UPDATEINTERVAL = -1; // use standard server tick interval

    private final EntityManager entityManager;
    private final HandleRegistry<ClientBean> clientRegistry;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       entityManager
     * @param       clientRegistry
     */
    public MovementSystem(
            EventAggregator aggregator, 
            EntityManager entityManager, 
            HandleRegistry<ClientBean> clientRegistry)
    {
        super(aggregator, UPDATEINTERVAL);

        this.entityManager = entityManager;
        this.clientRegistry = clientRegistry;
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
                Direction.class,
                Movement.class);

        for (Entity entity : entities)
        {
            // check if entity is not moving
            if (entity.get(Movement.class).moveState != MovementState.MoveKeepDir)
            {
                continue;
            }
            
            // retrieve the components we need
            Position pos = entity.get(Position.class);
            Vector2 dir = entity.get(Direction.class).direction.getUnit();
            Movement move = entity.get(Movement.class);
            
//            // update the position (we simply assume that the client has now reached its future position)
//            pos.position = move.futurePosition;

            // calculate the next future position (the next movement aim)
            // we do not actually change the current position of the agent!
            // formula: posVec + ( dirVec * (speedScal * (0.001 * timeDeltaScal)))
            pos.position = pos.position.add(dir.mul(move.speed * (0.001F * timeDelta))); 
            
            // inform the clients
            for (Handle<ClientBean> client : clientRegistry.getAllHandles())
            {
                EntityMovementView.sendUpdateMovement(client.get().getChannel(), entity);
            }
        }
    }


    /**
     * Handles movement events.
     * This only concerns entities that just started moving or changed direction.
     *
     * @param moveEvt
     */
    @Event.Handler
    public void onMove(MoveEvent moveEvt)
    {
        // we need to inform the connected clients and
        // set the movement state to moving (if that has not yet happened)
        
        Entity et = moveEvt.getThisEntity();

        // update the entities values
//        et.get(Direction.class).direction = moveEvt.getDirection().getUnit();
        Movement move = et.get(Movement.class);
//        move.moveType = moveEvt.getType(); // use the new movement type here
//        move.moveState = MovementState.Moving;

        // inform the clients
        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles())
        {
            EntityMovementView.sendChangeDirection(clientHandle.get().getChannel(), et);
            
            // anything else will be send on server tick. no need to repeat the
            // calculations here
        }
        
        move.moveState = MovementState.MoveKeepDir;
    }


    /**
     * Handles entities that stopped moving.
     * Tells our clients.
     *
     * @param stopMove
     */
    @Event.Handler
    public void onStopMoving(StopMovingEvent stopMove)
    {
        // we need to inform the connected clients and
        // set the movement state to notmoving

        Entity et = stopMove.getThisEntity();
        
        // update the movement component of the entity
        // note that as the entity is not moving anymore, 
        // its future position is the position it has already reached!
        // this is necessary because the update movement view will send the future position
        Movement move = et.get(Movement.class);
        move.moveAim = et.get(Position.class).position;
        move.moveType = MovementType.Stop;
        move.moveState = MovementState.NotMoving;

        // inform the clients
        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles())
        {
            EntityMovementView.sendRotateAgent(clientHandle.get().getChannel(), et);
            
            EntityMovementView.sendUpdateMovement(clientHandle.get().getChannel(), et);
        }
    }


    /**
     * Event handler.
     * Rotate event, this signals that an entity changed its direction
     * while not moving.
     *
     * @param rot
     */
    @Event.Handler
    public void onRotate(RotateEvent rot)
    {
        // fetch some entity info
        Entity et = rot.getThisEntity();
        
        // and update the direction
        Direction dir = et.get(Direction.class);
        dir.direction = rot.getNewDirection().getUnit();

        // inform the clients
        for (Handle<ClientBean> clientHandle : clientRegistry.getAllHandles())
        {
            EntityMovementView.sendRotateAgent(clientHandle.get().getChannel(), et);
        }
    }
}
