
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P409_Unknown
    extends GWAction
{

    public short unknown1;
    public long unknown2;

    static {
        GameServerActionFactory.registerOutbound(P409_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  409;
    }

}
