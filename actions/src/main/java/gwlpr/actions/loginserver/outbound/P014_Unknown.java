
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P014_Unknown
    extends GenericAction
{

    private long unknown1;
    private long unknown2;
    @IsArray(constant = false, size = 32, prefixLength = 1)
    private short[] unknown3;

    public short getHeader() {
        return  14;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(long unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(short[] unknown3) {
        this.unknown3 = unknown3;
    }

}
