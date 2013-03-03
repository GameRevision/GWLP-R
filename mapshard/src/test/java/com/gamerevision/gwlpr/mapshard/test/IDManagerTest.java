/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.test;

import com.gamerevision.gwlpr.mapshard.models.IDManager;
import org.junit.Test;


/**
 * Test the features of the ID man
 * 
 * @author _rusty
 */
public class IDManagerTest 
{
    @Test
    public void testIDMan()
    {
        int a, b;
        
        a = IDManager.reserveAgentID();
        b = IDManager.reserveLocalID();
        
        assert a > 0;
        assert b > 0;
        
        // because both stacks are initialized equally,
        // both ids should start with the same value:
        assert a == b;
        
        IDManager.freeAgentID(a);
        a = IDManager.reserveAgentID();
        
        // after push and pop we should have the same value
        assert a == b;
        
        // after only popping without pushing the value first
        // we should have a different value:
        a = IDManager.reserveAgentID();
        
        assert a != b;
    }
}
