
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P413_Unknown
    extends GWAction
{

    public int unknown1;
    public long unknown2;
    public long unknown3;
    public String unknown4;

    static {
        GameServerActionFactory.registerOutbound(P413_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  413;
    }

}
