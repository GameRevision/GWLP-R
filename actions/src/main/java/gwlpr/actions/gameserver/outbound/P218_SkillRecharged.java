
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P218_SkillRecharged
    extends GWAction
{

    public long agentID;
    public int skillID;
    public long skillInstance;

    static {
        GameServerActionFactory.registerOutbound(P218_SkillRecharged.class);
    }

    @Override
    public short getHeader() {
        return  218;
    }

}
