
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.utils.Vector2;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P055_Unknown
    extends GenericAction
{

    private Vector2 unknown1;
    private long unknown2;

    public short getHeader() {
        return  55;
    }

    public Vector2 getUnknown1() {
        return unknown1;
    }

    public long getUnknown2() {
        return unknown2;
    }

}
