
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P035_Unknown
    extends GWAction
{

    public long unknown1;
    public long unknown2;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    public byte[] unknown3;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    public byte[] unknown4;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    public byte[] unknown5;
    public String unknown6;

    static {
        LoginServerActionFactory.registerOutbound(P035_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  35;
    }

}
