
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P041_PingTarget
    extends GenericAction
{

    private int partyMember;
    private long targetAgent;

    public short getHeader() {
        return  41;
    }

    public void setPartyMember(int partyMember) {
        this.partyMember = partyMember;
    }

    public void setTargetAgent(long targetAgent) {
        this.targetAgent = targetAgent;
    }

}
