
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P269_GuildAllyRemove
    extends GWAction
{

    @IsArray(constant = true, size = 16, prefixLength = -1)
    public byte[] guildHallData;

    static {
        GameServerActionFactory.registerOutbound(P269_GuildAllyRemove.class);
    }

    @Override
    public short getHeader() {
        return  269;
    }

}
