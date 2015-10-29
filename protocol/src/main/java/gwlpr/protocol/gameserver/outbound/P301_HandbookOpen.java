
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P301_HandbookOpen
    extends GWMessage
{

    private long localID;
    private long unknown1;

    @Override
    public short getHeader() {
        return  301;
    }

    public void setLocalID(long localID) {
        this.localID = localID;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P301_HandbookOpen[");
        sb.append("localID=").append(this.localID).append(",unknown1=").append(this.unknown1).append("]");
        return sb.toString();
    }

}