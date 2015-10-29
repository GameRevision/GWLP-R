
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * This is used to destroy an agent's attribute
 * points specified with P044_UpdateAttribPts.
 * 
 */
public final class P236_AgentAttributeDestroy
    extends GWMessage
{

    private long agentID;

    @Override
    public short getHeader() {
        return  236;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P236_AgentAttributeDestroy[");
        sb.append("agentID=").append(this.agentID).append("]");
        return sb.toString();
    }

}