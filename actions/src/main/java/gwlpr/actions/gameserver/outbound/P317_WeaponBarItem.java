
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P317_WeaponBarItem
    extends GWAction
{

    public int itemStreamID;
    public short slot;
    public long leadHand;
    public long offHand;

    static {
        GameServerActionFactory.registerOutbound(P317_WeaponBarItem.class);
    }

    @Override
    public short getHeader() {
        return  317;
    }

}
