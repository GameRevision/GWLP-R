
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P138_Unknown
    extends GWAction
{

    public short unknown1;
    public short unknown2;

    static {
        GameServerActionFactory.registerInbound(P138_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  138;
    }

}
