
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P317_WeaponBarItem
    extends GenericAction
{

    private int itemStreamID;
    private short slot;
    private long leadHand;
    private long offHand;

    public short getHeader() {
        return  317;
    }

    public void setItemStreamID(int itemStreamID) {
        this.itemStreamID = itemStreamID;
    }

    public void setSlot(short slot) {
        this.slot = slot;
    }

    public void setLeadHand(long leadHand) {
        this.leadHand = leadHand;
    }

    public void setOffHand(long offHand) {
        this.offHand = offHand;
    }

}
