
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P162_Unknown
    extends GWAction
{

    public int unknown1;

    static {
        GameServerActionFactory.registerInbound(P162_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  162;
    }

}
