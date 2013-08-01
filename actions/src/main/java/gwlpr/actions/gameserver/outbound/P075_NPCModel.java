
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;
import gwlpr.actions.utils.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P075_NPCModel
    extends GWAction
{

    public long localID;
    @IsArray(constant = false, size = 8, prefixLength = 2)
    public P075_NPCModel.NestedModelFile[] modelFile;

    static {
        GameServerActionFactory.registerOutbound(P075_NPCModel.class);
    }

    @Override
    public short getHeader() {
        return  75;
    }

    public final static class NestedModelFile
        implements NestedMarker
    {

        public long unknown1;

    }

}
