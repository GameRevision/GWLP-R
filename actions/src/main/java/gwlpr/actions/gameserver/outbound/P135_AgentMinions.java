
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P135_AgentMinions
    extends GenericAction
{

    private long agent;
    private long amount;

    public short getHeader() {
        return  135;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
