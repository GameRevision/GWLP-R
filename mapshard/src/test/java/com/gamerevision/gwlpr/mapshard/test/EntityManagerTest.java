/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.test;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.gamerevision.gwlpr.mapshard.entitysystem.EntityManager;
import com.gamerevision.gwlpr.mapshard.entitysystem.components.Components.*;
import java.util.Collection;
import org.junit.Test;


/**
 * Testing the functionality of the entity manager
 *
 * - Create different components and link them to entities
 * - Retrieve entities with specific component-types
 * - Retrieve components of specific entities
 *
 * @author _rusty
 */
public class EntityManagerTest
{
    @Test
    public void testEntityManager()
    {
        // create the entity manager
        EntityManager eMan = new EntityManager();
        
        // before adding any entities, try to retrieve some...
        Collection<Entity> ents = eMan.getEntitiesWith(Name.class, AgentIdentifiers.class, Position.class, Direction.class);
        assert ents.isEmpty();
        
        // now lets add some entities
        Name n1 = new Name();
        Name n2 = new Name();
        Name n3 = new Name();
        Name n4 = new Name();

        AgentIdentifiers a1 = new AgentIdentifiers();
        AgentIdentifiers a2 = new AgentIdentifiers();
        AgentIdentifiers a3 = new AgentIdentifiers();

        Position p1 = new Position();
        Position p2 = new Position();
        
        Direction d1 = new Direction();

        // register the entities
        Entity e1 = new Entity(eMan, n1, a1, p1, d1);
        Entity e2 = new Entity(eMan, n2, a2, p2);
        Entity e3 = new Entity(eMan, n3, a3);
        Entity e4 = new Entity(eMan, n4);
        Entity e5 = new Entity(eMan);

        // retrieve entities with specific components
        ents = eMan.getEntitiesWith(Name.class, AgentIdentifiers.class, Position.class, Direction.class);
        assert ents.size() == 1;
        assert ents.contains(e1);

        ents = eMan.getEntitiesWith(Name.class, AgentIdentifiers.class, Position.class);
        assert ents.size() == 2;
        assert ents.contains(e1);
        assert ents.contains(e2);

        ents = eMan.getEntitiesWith(Name.class, AgentIdentifiers.class);
        assert ents.size() == 3;
        assert ents.contains(e1);
        assert ents.contains(e2);
        assert ents.contains(e3);

        ents = eMan.getEntitiesWith(Name.class);
        assert ents.size() == 4;
        assert ents.contains(e1);
        assert ents.contains(e2);
        assert ents.contains(e3);
        assert ents.contains(e4);

        ents = eMan.getEntitiesWith();
        assert ents.isEmpty();

        // retrieve components of specific entities
        Name n = e1.get(Name.class); // e1 has a Name
        assert n == n1;

        AgentIdentifiers a = e2.get(AgentIdentifiers.class); // e2 has an AgentID
        assert a == a2;

        Position p = e3.get(Position.class); // e3 has no LocalID
        assert p == null;

        Direction d = e5.get(Direction.class); // e5 has no components
        assert d == null;
    }
}
