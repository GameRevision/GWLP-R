
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P389_ChallengeRecordsDisplay
    extends GWAction
{

    public int mission;
    public short type;

    static {
        GameServerActionFactory.registerOutbound(P389_ChallengeRecordsDisplay.class);
    }

    @Override
    public short getHeader() {
        return  389;
    }

}
