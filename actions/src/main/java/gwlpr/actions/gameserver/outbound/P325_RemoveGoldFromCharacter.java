
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P325_RemoveGoldFromCharacter
    extends GWAction
{

    public int itemStreamID;
    public long amount;

    static {
        GameServerActionFactory.registerOutbound(P325_RemoveGoldFromCharacter.class);
    }

    @Override
    public short getHeader() {
        return  325;
    }

}
