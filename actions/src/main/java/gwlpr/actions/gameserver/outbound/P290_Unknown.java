
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P290_Unknown
    extends GWAction
{

    public long unknown1;
    public long unknown2;

    static {
        GameServerActionFactory.registerOutbound(P290_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  290;
    }

}
