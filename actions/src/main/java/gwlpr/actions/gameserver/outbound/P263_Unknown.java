
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P263_Unknown
    extends GWAction
{

    public int unknown1;
    public long unknown2;
    public long unknown3;

    static {
        GameServerActionFactory.registerOutbound(P263_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  263;
    }

}
