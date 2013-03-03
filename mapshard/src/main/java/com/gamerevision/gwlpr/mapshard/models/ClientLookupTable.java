/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;

import com.gamerevision.gwlpr.mapshard.entitysystem.Entity;
import com.realityshard.shardlet.Session;
import java.util.Collection;
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
     * Remove a client and its entity entry.
     * 
     * @param       session                 The session of the client
     */
    public void removeClient(Session session)
    {
        Entity et = bySession.get(session);
        
        // failcheck
        if (et == null) { return; }
        
        bySession.remove(session);
        
        // failcheck
        if (byEntity.get(et) == null) { return; }
        
        byEntity.remove(et);
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
    
    
    /**
     * Use this to access all sessions available for this game app.
     * 
     * @return      All the sessions (or an empty collection)
     */
    public Collection<Session> getAllSessions()
    {
        return bySession.keySet();
    }
}
