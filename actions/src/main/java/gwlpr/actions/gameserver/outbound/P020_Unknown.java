
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P020_Unknown
    extends GWAction
{

    public long unknown1;

    static {
        GameServerActionFactory.registerOutbound(P020_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  20;
    }

}
