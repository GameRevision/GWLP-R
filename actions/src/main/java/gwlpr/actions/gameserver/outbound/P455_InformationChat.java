
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * Will display text of hard coded string IDs in the
 * chat box
 * 
 */
public final class P455_InformationChat
    extends GWAction
{

    public short text;

    static {
        GameServerActionFactory.registerOutbound(P455_InformationChat.class);
    }

    @Override
    public short getHeader() {
        return  455;
    }

}
