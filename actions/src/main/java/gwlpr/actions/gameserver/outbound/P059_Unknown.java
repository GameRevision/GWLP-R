
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.Vector2;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P059_Unknown
    extends GenericAction
{

    private long unknown1;
    private long unknown2;
    private Vector2 unknown3;
    private int unknown4;

    public short getHeader() {
        return  59;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(long unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(Vector2 unknown3) {
        this.unknown3 = unknown3;
    }

    public void setUnknown4(int unknown4) {
        this.unknown4 = unknown4;
    }

}
