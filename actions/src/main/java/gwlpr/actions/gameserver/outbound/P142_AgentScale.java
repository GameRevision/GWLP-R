
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P142_AgentScale
    extends GenericAction
{

    private long agentID;
    private long scale;

    public short getHeader() {
        return  142;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public void setScale(long scale) {
        this.scale = scale;
    }

}
