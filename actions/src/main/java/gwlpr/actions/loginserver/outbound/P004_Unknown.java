
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P004_Unknown
    extends GWAction
{

    public long unknown1;
    public String unknown2;

    static {
        LoginServerActionFactory.registerOutbound(P004_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  4;
    }

}
