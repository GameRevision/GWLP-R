
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P109_HeroModel
    extends GWAction
{

    public int heroID;
    public long modelFile;

    static {
        GameServerActionFactory.registerOutbound(P109_HeroModel.class);
    }

    @Override
    public short getHeader() {
        return  109;
    }

}
