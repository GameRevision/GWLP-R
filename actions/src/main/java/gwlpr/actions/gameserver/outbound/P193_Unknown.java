
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P193_Unknown
    extends GWAction
{

    public short unknown1;
    public long unknown2;
    public short unknown3;
    public short unknown4;

    static {
        GameServerActionFactory.registerOutbound(P193_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  193;
    }

}
