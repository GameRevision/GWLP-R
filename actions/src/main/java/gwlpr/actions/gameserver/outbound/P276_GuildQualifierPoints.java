
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P276_GuildQualifierPoints
    extends GWAction
{

    public int localID;
    public long amount;

    static {
        GameServerActionFactory.registerOutbound(P276_GuildQualifierPoints.class);
    }

    @Override
    public short getHeader() {
        return  276;
    }

}
