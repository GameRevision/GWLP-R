
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P009_Unknown
    extends GWAction
{

    public short unknown1;
    public long unknown2;
    public long unknown3;

    static {
        GameServerActionFactory.registerOutbound(P009_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  9;
    }

}
