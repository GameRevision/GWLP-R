
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P351_Unknown
    extends GenericAction
{

    private short unknown1;
    private long unknown2;

    public short getHeader() {
        return  351;
    }

    public void setUnknown1(short unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(long unknown2) {
        this.unknown2 = unknown2;
    }

}
