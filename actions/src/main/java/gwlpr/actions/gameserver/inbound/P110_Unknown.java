
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P110_Unknown
    extends GWAction
{

    public long unknown1;
    public long unknown2;
    public int unknown3;
    public short unknown4;

    static {
        GameServerActionFactory.registerInbound(P110_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  110;
    }

}
