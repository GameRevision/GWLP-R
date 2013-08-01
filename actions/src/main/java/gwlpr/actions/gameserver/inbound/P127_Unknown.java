
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P127_Unknown
    extends GWAction
{

    public int unknown1;
    public int unknown2;
    @IsArray(constant = false, size = 4, prefixLength = 2)
    public P127_Unknown.NestedUnknown3 [] unknown3;
    public short unknown4;
    public short unknown5;
    public short unknown6;

    static {
        GameServerActionFactory.registerInbound(P127_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  127;
    }

    public final static class NestedUnknown3
        implements NestedMarker
    {

        public int unknown1;

    }

}
