
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P151_UpdateTargetGenericModifier
    extends GWAction
{

    public long valueID;
    public long target;
    public long caster;
    public long value;

    static {
        GameServerActionFactory.registerOutbound(P151_UpdateTargetGenericModifier.class);
    }

    @Override
    public short getHeader() {
        return  151;
    }

}
