
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P004_Unknown
    extends GWAction
{


    static {
        GameServerActionFactory.registerInbound(P004_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  4;
    }

}
