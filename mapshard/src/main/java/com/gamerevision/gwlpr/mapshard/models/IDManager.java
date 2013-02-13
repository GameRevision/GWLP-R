/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models;

import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Manages Agent and Local IDs
 * 
 * TODO: Make this threadsafe...
 * 
 * @author _rusty
 */
public final class IDManager 
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(IDManager.class);
    
    private final static int MIN = 10;
    private final static int MAX = 1000;
    
    private final static Stack<Integer> freeAgentIDs = new Stack<>();
    private final static Stack<Integer> freeLocalIDs = new Stack<>();
    
    
    /**
     * Static constructor.
     */
    static
    {
        reset();
    }
    
    
    /**
     * Reset the IDManager.
     * CAUTION! IDs that were previously reserved will now be available again!
     */
    public static void reset()
    {        
        freeAgentIDs.clear();
        freeLocalIDs.clear();
        
        // init the agentIDs from 1000-10
        for (int i = MAX; i < MIN; i--) { freeAgentIDs.push(i); }
        
        // init the localIDs from 1000-10
        for (int i = MAX; i < MIN; i--) { freeLocalIDs.push(i); }
    }
    
    
    /**
     * Reserve an agentID.
     * 
     * @return      The reserved ID or 0, if none was free. 
     */
    public static int reserveAgentID()
    {
        // failcheck
        if (freeAgentIDs.empty())
        {
            LOGGER.debug("Cannot reserver agent ID. No free IDs available.");
            return 0;
        }
        
        return freeAgentIDs.pop();
    }
    
    
    /**
     * Free a reserved agentID.
     * 
     * @param       id 
     */
    public static void freeAgentID(int id)
    {
        if (freeAgentIDs.contains(id) || !inRange(id)) { return; }
        
        freeAgentIDs.push(id);
    }
    
    
    /**
     * Reserve a localID.
     * 
     * @return      The reserved ID or 0, if none was free. 
     */
    public static int reserveLocalID()
    {
        // failcheck
        if (freeLocalIDs.empty())
        {
            LOGGER.debug("Cannot reserver local ID. No free IDs available.");
            return 0;
        }
        
        return freeLocalIDs.pop();
    }
    
    
    /**
     * Free a reserved localID.
     * 
     * @param       id 
     */
    public static void freeLocalID(int id)
    {
        if (freeLocalIDs.contains(id) || !inRange(id)) { return; }
        
        freeLocalIDs.push(id);
    }
    
    
    /**
     * Helper.
     */
    private static boolean inRange(int id)
    {
        return (id >= MIN) && (id <= MAX);
    }
}
