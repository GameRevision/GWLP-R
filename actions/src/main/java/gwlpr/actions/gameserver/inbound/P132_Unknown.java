
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P132_Unknown
    extends GenericAction
{

    private String unknown1;
    @IsArray(constant = true, size = 8, prefixLength = 1)
    private short[] unknown2;

    public short getHeader() {
        return  132;
    }

    public String getUnknown1() {
        return unknown1;
    }

    public short[] getUnknown2() {
        return unknown2;
    }

}
