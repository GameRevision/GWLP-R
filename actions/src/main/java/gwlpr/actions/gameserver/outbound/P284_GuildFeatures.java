
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P284_GuildFeatures
    extends GWAction
{

    public int localID;
    public short features;

    static {
        GameServerActionFactory.registerOutbound(P284_GuildFeatures.class);
    }

    @Override
    public short getHeader() {
        return  284;
    }

}
