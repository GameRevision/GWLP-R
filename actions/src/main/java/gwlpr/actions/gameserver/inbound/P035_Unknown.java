
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P035_Unknown
    extends GWAction
{

    public long unknown1;

    static {
        GameServerActionFactory.registerInbound(P035_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  35;
    }

}
