
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P334_Unknown
    extends GWAction
{

    public short unknown1;
    public short unknown2;

    static {
        GameServerActionFactory.registerOutbound(P334_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  334;
    }

}
