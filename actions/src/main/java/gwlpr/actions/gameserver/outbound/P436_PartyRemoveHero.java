
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P436_PartyRemoveHero
    extends GenericAction
{

    private int partyId;
    private int charLocalID;
    private int agentID;

    public short getHeader() {
        return  436;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public void setCharLocalID(int charLocalID) {
        this.charLocalID = charLocalID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

}
