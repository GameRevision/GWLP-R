
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P414_Unknown
    extends GWAction
{

    public int unknown1;
    public long unknown2;
    public short unknown3;
    public short unknown4;
    public short unknown5;
    public String unknown6;

    static {
        GameServerActionFactory.registerOutbound(P414_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  414;
    }

}
