/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.realityshard.shardlet.Session;
import java.util.HashMap;
import java.util.Map;


/**
 * This class manages the associations between sessions an their entities.
 * Nothing more, nothing less :D
 * 
 * @author _rusty
 */
public class ClientLookupTable 
{
    private final Map<Entity, Session> byEntity;
    private final Map<Session, Entity> bySession;
    
    
    /**
     * Constructor.
     */
    public ClientLookupTable()
    {
        byEntity = new HashMap<>();
        bySession = new HashMap<>();
    }

    
    /**
     * Add a new client (by its session and entity) to this lookup table
     * 
     * @param       session                 The session connected with this client.
     * @param       entity                  The entity connected with this client.
     */
    public void addClient(Session session, Entity entity)
    {
        byEntity.put(entity, session);
        bySession.put(session, entity);
    }
    
    
    /**
     * Getter.
     * 
     * @return      A session by its corresponding entity
     */
    public Session getByEntity(Entity entity)
    {
        return byEntity.get(entity);
    }
    
    
    /**
     * Getter.
     * 
     * @return      An entity by its corresponding session
     */
    public Entity getBySession(Session session)
    {
        return bySession.get(session);
    }
}
