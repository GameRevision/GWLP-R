
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P287_Unknown
    extends GWAction
{

    public String unknown1;

    static {
        GameServerActionFactory.registerOutbound(P287_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  287;
    }

}
