
package gwlpr.protocol.gameserver.inbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P056_Unknown
    extends GWMessage
{

    private long agentID1;
    private short unknown2;

    @Override
    public short getHeader() {
        return  56;
    }

    public long getAgentID1() {
        return agentID1;
    }

    public short getUnknown2() {
        return unknown2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P056_Unknown[");
        sb.append("agentID1=").append(this.agentID1).append(",unknown2=").append(this.unknown2).append("]");
        return sb.toString();
    }

}
