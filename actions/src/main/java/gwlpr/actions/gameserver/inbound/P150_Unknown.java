
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P150_Unknown
    extends GWAction
{

    public int unknown1;

    static {
        GameServerActionFactory.registerInbound(P150_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  150;
    }

}
