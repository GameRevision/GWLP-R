
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P382_Unknown
    extends GWAction
{

    public short unknown1;

    static {
        GameServerActionFactory.registerOutbound(P382_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  382;
    }

}
