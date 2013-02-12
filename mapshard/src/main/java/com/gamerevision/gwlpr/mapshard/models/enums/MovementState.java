/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models.enums;

/**
 * GW Movement States, as taken from GWLPR-C#
 * 
 * @author _rusty
 */
public enum MovementState 
{
    Forward         (1),
    DiagFwLeft      (2),
    DiagFwRight     (3),
    Backward        (4),
    DiagBwLeft      (5),
    DiagBwRight     (6),
    SideLeft        (7),
    SideRight       (8),
    Stop            (9),
    
    /**
     * This is not a gw-internal value:
     */
    Collision       (10);
    
    private final int gwVal;
    
    MovementState(int gwVal)
    {
        this.gwVal = gwVal;
    }
}
