
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P072_Unknown
    extends GWAction
{

    public short unknown1;
    public int unknown2;
    public short unknown3;

    static {
        GameServerActionFactory.registerInbound(P072_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  72;
    }

}
