
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P175_AddMissionObjective
    extends GWAction
{

    public long section;
    public long type;
    public String objective;

    static {
        GameServerActionFactory.registerOutbound(P175_AddMissionObjective.class);
    }

    @Override
    public short getHeader() {
        return  175;
    }

}
