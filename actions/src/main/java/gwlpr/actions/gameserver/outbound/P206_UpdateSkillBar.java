
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P206_UpdateSkillBar
    extends GWAction
{

    public long agentID;
    @IsArray(constant = false, size = 8, prefixLength = 2)
    public P206_UpdateSkillBar.NestedSkillBar[] skillBar;
    @IsArray(constant = false, size = 8, prefixLength = 2)
    public P206_UpdateSkillBar.NestedSkillBarPvPMask[] skillBarPvPMask;
    public short unknown1;

    static {
        GameServerActionFactory.registerOutbound(P206_UpdateSkillBar.class);
    }

    @Override
    public short getHeader() {
        return  206;
    }

    public final static class NestedSkillBar
        implements NestedMarker
    {

        public long unknown1;

    }

    public final static class NestedSkillBarPvPMask
        implements NestedMarker
    {

        public long unknown1;

    }

}
