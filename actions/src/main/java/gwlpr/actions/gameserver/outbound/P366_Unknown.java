
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P366_Unknown
    extends GWAction
{

    public int unknown1;
    public String unknown2;
    public String unknown3;
    public int unknown4;
    public short unknown5;
    public short unknown6;
    public short unknown7;
    public short unknown8;
    public int unknown9;
    public int unknown10;
    public short unknown11;
    public short unknown12;
    public short unknown13;
    public short unknown14;
    public int unknown15;
    public int unknown16;
    public long unknown17;
    public long unknown18;
    public long unknown19;
    public short unknown20;

    static {
        GameServerActionFactory.registerOutbound(P366_Unknown.class);
    }

    @Override
    public short getHeader() {
        return  366;
    }

}
