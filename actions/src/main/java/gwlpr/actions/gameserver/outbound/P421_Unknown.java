
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P421_Unknown
    extends GWAction
{


    static {
        GameServerActionFactory.registerOutbound(P421_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  421;
    }

}
