
package gwlpr.actions.loginserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P018_Unknown
    extends GWAction
{

    public long unknown1;
    public long unknown2;
    @IsArray(constant = true, size = 20, prefixLength = -1)
    public byte[] unknown3;
    public String unknown4;
    public String unknown5;
    public String unknown6;
    public String unknown7;

    static {
        LoginServerActionFactory.registerInbound(P018_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  18;
    }

}
