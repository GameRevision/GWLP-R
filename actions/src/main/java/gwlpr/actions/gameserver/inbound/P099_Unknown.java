
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P099_Unknown
    extends GenericAction
{

    private long unknown1;
    @IsArray(constant = false, size = 4, prefixLength = 2)
    private long[] unknown2;

    public short getHeader() {
        return  99;
    }

    public long getUnknown1() {
        return unknown1;
    }

    public long[] getUnknown2() {
        return unknown2;
    }

}
