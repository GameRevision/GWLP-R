
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P150_UpdateGenericModifier
    extends GWAction
{

    public long valueID;
    public long agentID;
    public long value;

    static {
        GameServerActionFactory.registerOutbound(P150_UpdateGenericModifier.class);
    }

    @Override
    public short getHeader() {
        return  150;
    }

}
