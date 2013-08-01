
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P321_MoveItem
    extends GWAction
{

    public int itemStreamID;
    public long itemLocalID;
    public int pageID;
    public short slot;

    static {
        GameServerActionFactory.registerOutbound(P321_MoveItem.class);
    }

    @Override
    public short getHeader() {
        return  321;
    }

}
