
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P023_Unknown
    extends GWAction
{

    @IsArray(constant = false, size = 4096, prefixLength = 2)
    public byte[] unknown1;

    static {
        LoginServerActionFactory.registerOutbound(P023_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  23;
    }

}
