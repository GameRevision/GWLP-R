
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P344_Unknown
    extends GenericAction
{

    private long unknown1;
    private long unknown2;
    private short unknown3;
    private short unknown4;
    private int unknown5;
    private int unknown6;
    private short unknown7;
    private long unknown8;
    private long unknown9;
    private long unknown10;
    private long unknown11;
    private String unknown12;
    @IsArray(constant = false, size = 64, prefixLength = 2)
    private P344_Unknown.NestedUnknown13 [] unknown13;

    public short getHeader() {
        return  344;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(long unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(short unknown3) {
        this.unknown3 = unknown3;
    }

    public void setUnknown4(short unknown4) {
        this.unknown4 = unknown4;
    }

    public void setUnknown5(int unknown5) {
        this.unknown5 = unknown5;
    }

    public void setUnknown6(int unknown6) {
        this.unknown6 = unknown6;
    }

    public void setUnknown7(short unknown7) {
        this.unknown7 = unknown7;
    }

    public void setUnknown8(long unknown8) {
        this.unknown8 = unknown8;
    }

    public void setUnknown9(long unknown9) {
        this.unknown9 = unknown9;
    }

    public void setUnknown10(long unknown10) {
        this.unknown10 = unknown10;
    }

    public void setUnknown11(long unknown11) {
        this.unknown11 = unknown11;
    }

    public void setUnknown12(String unknown12) {
        this.unknown12 = unknown12;
    }

    public void setUnknown13(P344_Unknown.NestedUnknown13 [] unknown13) {
        this.unknown13 = unknown13;
    }

    public final class NestedUnknown13
        implements NestedMarker
    {

        private long unknown1;

    }

}
