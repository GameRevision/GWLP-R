
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P097_UpdateAgentWeapons
    extends GWAction
{

    public long agentID;
    public long leadhand;
    public long offhand;

    static {
        GameServerActionFactory.registerOutbound(P097_UpdateAgentWeapons.class);
    }

    @Override
    public short getHeader() {
        return  97;
    }

}
