
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P453_AlertBox
    extends GWAction
{

    public String text;

    static {
        GameServerActionFactory.registerOutbound(P453_AlertBox.class);
    }

    @Override
    public short getHeader() {
        return  453;
    }

}
