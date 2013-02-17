/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.models.enums;

/**
 * GW Movement States, as taken from GWLPR-C#
 *
 * @author _rusty
 */
public enum MovementType
{
    Forward         (1, 1),
    DiagFwLeft      (2, 1),
    DiagFwRight     (3, 1),
    Backward        (4, 0.66F),
    DiagBwLeft      (5, 0.66F),
    DiagBwRight     (6, 0.66F),
    SideLeft        (7, 0.75F),
    SideRight       (8, 0.75F),
    Stop            (9, 1),

    /**
     * This is not a gw-internal value:
     */
    Collision       (10, 1);

    private final int gwVal;
    private final float speedModifier;

    MovementType(int gwVal, float speedModifier)
    {
        this.gwVal = gwVal;
        this.speedModifier = speedModifier;
    }

    public static MovementType fromInt(int type)
    {
        // failcheck
        if (type > (MovementType.values().length + 1)) { return null; }

        // note that the first type starts with 1, not 0
        // thats why we will have to decrement that...
        return MovementType.values()[type - 1];
    }

    public byte getVal() { return (byte)gwVal; }
    public float getSpeedModifier() { return speedModifier; }
}
