
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P185_OpenCollectorWindow
    extends GWAction
{

    public short windowType;
    public String text;

    static {
        GameServerActionFactory.registerOutbound(P185_OpenCollectorWindow.class);
    }

    @Override
    public short getHeader() {
        return  185;
    }

}
