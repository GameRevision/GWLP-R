
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P019_Unknown
    extends GWAction
{

    public long unknown1;

    static {
        LoginServerActionFactory.registerOutbound(P019_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  19;
    }

}
