
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * This is used to destroy an agent's attribute
 * points specified with P044_UpdateAttribPts.
 * 
 */
public final class P236_AgentAttributeDestroy
    extends GenericAction
{

    private long agentID;

    public short getHeader() {
        return  236;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

}
