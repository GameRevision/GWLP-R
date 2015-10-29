
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P228_UpdateVitalStat
    extends GWMessage
{

    private long agentID;
    private long stats;

    @Override
    public short getHeader() {
        return  228;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public void setStats(long stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P228_UpdateVitalStat[");
        sb.append("agentID=").append(this.agentID).append(",stats=").append(this.stats).append("]");
        return sb.toString();
    }

}