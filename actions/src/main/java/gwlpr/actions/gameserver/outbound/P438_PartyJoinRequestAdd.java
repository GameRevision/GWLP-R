
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P438_PartyJoinRequestAdd
    extends GenericAction
{

    private int partyId;

    public short getHeader() {
        return  438;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

}
