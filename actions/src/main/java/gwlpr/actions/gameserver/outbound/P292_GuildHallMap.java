
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P292_GuildHallMap
    extends GWAction
{

    public int mapID;

    static {
        GameServerActionFactory.registerOutbound(P292_GuildHallMap.class);
    }

    @Override
    public short getHeader() {
        return  292;
    }

}
