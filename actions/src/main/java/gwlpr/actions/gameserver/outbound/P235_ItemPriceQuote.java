
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P235_ItemPriceQuote
    extends GWAction
{

    public long itemLocalID;
    public long price;

    static {
        GameServerActionFactory.registerOutbound(P235_ItemPriceQuote.class);
    }

    @Override
    public short getHeader() {
        return  235;
    }

}
