
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P439_PartyInviteCancel
    extends GenericAction
{

    private int partyId;

    public short getHeader() {
        return  439;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

}
