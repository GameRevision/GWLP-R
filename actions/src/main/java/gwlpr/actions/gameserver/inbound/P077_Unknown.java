
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P077_Unknown
    extends GWAction
{

    public long unknown1;

    static {
        GameServerActionFactory.registerInbound(P077_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  77;
    }

}
