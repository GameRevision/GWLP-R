
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P293_GuildAnnouncement
    extends GWAction
{

    public String announcement;
    public String characterName;

    static {
        GameServerActionFactory.registerOutbound(P293_GuildAnnouncement.class);
    }

    @Override
    public short getHeader() {
        return  293;
    }

}
