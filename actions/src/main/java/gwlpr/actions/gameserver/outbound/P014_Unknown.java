
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P014_Unknown
    extends GenericAction
{

    private int unknown1;
    private short unknown2;
    @IsArray(constant = false, size = 64, prefixLength = 2)
    private P014_Unknown.NestedUnknown3 [] unknown3;

    public short getHeader() {
        return  14;
    }

    public void setUnknown1(int unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(short unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(P014_Unknown.NestedUnknown3 [] unknown3) {
        this.unknown3 = unknown3;
    }

    public final class NestedUnknown3
        implements NestedMarker
    {

        private long unknown1;

    }

}
