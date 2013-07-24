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
import gwlpr.mapshard.models.ClientLookupTable;
import gwlpr.mapshard.models.GWVector;
import gwlpr.mapshard.models.enums.MovementState;
import gwlpr.mapshard.models.enums.MovementType;
import gwlpr.mapshard.views.EntityMovementView;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.Session;
import java.util.Collection;


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
    private final ClientLookupTable lookupTable;


    /**
     * Constructor.
     *
     * @param       aggregator
     * @param       entityManager
     * @param       lookupTable
     */
    public MovementSystem(
            EventAggregator aggregator, 
            EntityManager entityManager, 
            ClientLookupTable lookupTable)
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
            GWVector dir = entity.get(Direction.class).direction.getUnit();
            Movement move = entity.get(Movement.class);
            
//            // update the position (we simply assume that the client has now reached its future position)
//            pos.position = move.futurePosition;

            // calculate the next future position (the next movement aim)
            // we do not actually change the current position of the agent!
            // formula: posVec + ( dirVec * (speedScal * (0.001 * timeDeltaScal)))
            pos.position = pos.position.add(dir.mul(move.speed * (0.001F * timeDelta))); 
            
            // inform the clients
            for (Session session : lookupTable.getAllSessions())
            {
                EntityMovementView.sendUpdateMovement(session, entity);
            }
        }
    }


    /**
     * Handles movement events.
     * This only concerns entities that just started moving or changed direction.
     *
     * @param moveEvt
     */
    @EventHandler
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
        for (Session session : lookupTable.getAllSessions())
        {
            EntityMovementView.sendChangeDirection(session, et);
            
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
    @EventHandler
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
        move.futurePosition = et.get(Position.class).position;
        move.moveType = MovementType.Stop;
        move.moveState = MovementState.NotMoving;

        // inform the clients
        for (Session session : lookupTable.getAllSessions())
        {
            EntityMovementView.sendRotateAgent(session, et);
            
            EntityMovementView.sendUpdateMovement(session, et);
        }
    }


    /**
     * Event handler.
     * Rotate event, this siganls that an entity changed its direction
     * while not moving.
     *
     * @param rot
     */
    @EventHandler
    public void onRotate(RotateEvent rot)
    {
        // fetch some entity info
        Entity et = rot.getThisEntity();
        
        // and update the direction
        Direction dir = et.get(Direction.class);
        dir.direction = rot.getNewDirection().getUnit();

        // inform the clients
        for (Session session : lookupTable.getAllSessions())
        {
            EntityMovementView.sendRotateAgent(session, et);
        }
    }
}
