
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P083_Unknown
    extends GWAction
{

    public long unknown1;

    static {
        GameServerActionFactory.registerInbound(P083_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  83;
    }

}
