
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P242_Unknown
    extends GWAction
{


    static {
        GameServerActionFactory.registerOutbound(P242_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  242;
    }

}
