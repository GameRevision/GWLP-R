
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P463_PartySearchSeek
    extends GWAction
{

    public int seeking;

    static {
        GameServerActionFactory.registerOutbound(P463_PartySearchSeek.class);
    }

    @Override
    public short getHeader() {
        return  463;
    }

}
