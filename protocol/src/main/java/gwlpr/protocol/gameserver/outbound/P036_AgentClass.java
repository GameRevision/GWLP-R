
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P036_AgentClass
    extends GWMessage
{

    private long agent;
    private long agentClass;

    @Override
    public short getHeader() {
        return  36;
    }

    public void setAgent(long agent) {
        this.agent = agent;
    }

    public void setAgentClass(long agentClass) {
        this.agentClass = agentClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P036_AgentClass[");
        sb.append("agent=").append(this.agent).append(",agentClass=").append(this.agentClass).append("]");
        return sb.toString();
    }

}
