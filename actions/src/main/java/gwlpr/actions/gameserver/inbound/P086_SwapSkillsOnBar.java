
package gwlpr.actions.gameserver.inbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P086_SwapSkillsOnBar
    extends GWAction
{

    public long agentID;
    public long skillID1;
    public long unknown1;
    public long skillID2;
    public long unknown2;

    static {
        GameServerActionFactory.registerInbound(P086_SwapSkillsOnBar.class);
    }

    @Override
    public short getHeader() {
        return  86;
    }

}
