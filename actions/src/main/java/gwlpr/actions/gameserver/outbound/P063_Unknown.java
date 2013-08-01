
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P063_Unknown
    extends GWAction
{

    @IsArray(constant = false, size = 64, prefixLength = 2)
    public P063_Unknown.NestedUnknown1 [] unknown1;

    static {
        GameServerActionFactory.registerOutbound(P063_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  63;
    }

    public final static class NestedUnknown1
        implements NestedMarker
    {

        public long unknown1;

    }

}
