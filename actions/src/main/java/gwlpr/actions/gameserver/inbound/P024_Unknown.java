
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P024_Unknown
    extends GWAction
{

    public short unknown1;

    static {
        GameServerActionFactory.registerInbound(P024_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  24;
    }

}
