
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.Vector2;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P026_Unknown
    extends GWAction
{

    public long unknown1;
    public Vector2 unknown2;
    public short unknown3;

    static {
        GameServerActionFactory.registerOutbound(P026_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  26;
    }

}
