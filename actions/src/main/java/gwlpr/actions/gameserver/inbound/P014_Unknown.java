
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P014_Unknown
    extends GWAction
{

    public long agentID1;
    public long agentID2;

    static {
        GameServerActionFactory.registerInbound(P014_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  14;
    }

}
