
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P059_Unknown
    extends GWAction
{

    public long agentID1;

    static {
        GameServerActionFactory.registerInbound(P059_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  59;
    }

}
