
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P122_WindowItemPricesEnd
    extends GWAction
{

    public short windowType;

    static {
        GameServerActionFactory.registerOutbound(P122_WindowItemPricesEnd.class);
    }

    @Override
    public short getHeader() {
        return  122;
    }

}
