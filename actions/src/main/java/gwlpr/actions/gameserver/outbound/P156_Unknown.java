
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.Vector2;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P156_Unknown
    extends GenericAction
{

    private Vector2 unknown1;
    private int unknown2;
    private long unknown3;

    public short getHeader() {
        return  156;
    }

    public void setUnknown1(Vector2 unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(int unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(long unknown3) {
        this.unknown3 = unknown3;
    }

}
