
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P017_SkillUnlocked
    extends GWAction
{

    public int skillID;
    public short unlocked;

    static {
        GameServerActionFactory.registerOutbound(P017_SkillUnlocked.class);
    }

    @Override
    public short getHeader() {
        return  17;
    }

}
