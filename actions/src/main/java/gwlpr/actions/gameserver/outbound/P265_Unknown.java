
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P265_Unknown
    extends GWAction
{

    public long unknown1;
    public String unknown2;

    static {
        GameServerActionFactory.registerOutbound(P265_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  265;
    }

}
