
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P057_Unknown
    extends GWAction
{

    public long unknown1;
    public long unknown2;

    static {
        GameServerActionFactory.registerInbound(P057_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  57;
    }

}
