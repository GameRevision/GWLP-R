
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P398_Unknown
    extends GWAction
{

    public long unknown1;
    public int unknown2;
    public int unknown3;
    public short unknown4;
    public short unknown5;
    public short unknown6;
    @IsArray(constant = false, size = 2, prefixLength = 2)
    public P398_Unknown.NestedUnknown7 [] unknown7;

    static {
        GameServerActionFactory.registerOutbound(P398_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  398;
    }

    public final static class NestedUnknown7
        implements NestedMarker
    {

        public short unknown1;
        public short unknown2;
        public short unknown3;
        public short unknown4;
        public short unknown5;
        public short unknown6;
        public short unknown7;
        public int unknown8;
        public short unknown9;
        public String unknown10;

    }

}
