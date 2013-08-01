
package gwlpr.actions.loginserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P043_Unknown
    extends GWAction
{

    public long unknown1;
    @IsArray(constant = false, size = 128, prefixLength = 2)
    public P043_Unknown.NestedUnknown2 [] unknown2;
    @IsArray(constant = false, size = 128, prefixLength = 2)
    public P043_Unknown.NestedUnknown3 [] unknown3;

    static {
        LoginServerActionFactory.registerInbound(P043_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  43;
    }

    public final static class NestedUnknown2
        implements NestedMarker
    {

        public long unknown1;

    }

    public final static class NestedUnknown3
        implements NestedMarker
    {

        public long unknown1;

    }

}
