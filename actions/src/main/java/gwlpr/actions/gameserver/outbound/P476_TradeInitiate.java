
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P476_TradeInitiate
    extends GWAction
{

    public long charLocalID;

    static {
        GameServerActionFactory.registerOutbound(P476_TradeInitiate.class);
    }

    @Override
    public short getHeader() {
        return  476;
    }

}
