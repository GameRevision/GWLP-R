
package gwlpr.protocol.gameserver.outbound;

import java.util.Arrays;
import gwlpr.protocol.serialization.GWMessage;
import gwlpr.protocol.util.IsArray;
import gwlpr.protocol.util.NestedMarker;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P388_Unknown
    extends GWMessage
{

    private int unknown1;
    private short unknown2;
    private short unknown3;
    private short unknown4;
    private long unknown5;
    private String unknown6;
    @IsArray(constant = false, size = 8, prefixLength = 2)
    private P388_Unknown.NestedUnknown7 [] unknown7;

    @Override
    public short getHeader() {
        return  388;
    }

    public void setUnknown1(int unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(short unknown2) {
        this.unknown2 = unknown2;
    }

    public void setUnknown3(short unknown3) {
        this.unknown3 = unknown3;
    }

    public void setUnknown4(short unknown4) {
        this.unknown4 = unknown4;
    }

    public void setUnknown5(long unknown5) {
        this.unknown5 = unknown5;
    }

    public void setUnknown6(String unknown6) {
        this.unknown6 = unknown6;
    }

    public void setUnknown7(P388_Unknown.NestedUnknown7 [] unknown7) {
        this.unknown7 = unknown7;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P388_Unknown[");
        sb.append("unknown1=").append(this.unknown1).append(",unknown2=").append(this.unknown2).append(",unknown3=").append(this.unknown3).append(",unknown4=").append(this.unknown4).append(",unknown5=").append(this.unknown5).append(",unknown6=").append(this.unknown6 .toString()).append(",unknown7=").append(Arrays.toString(this.unknown7)).append("]");
        return sb.toString();
    }

    public final static class NestedUnknown7
        implements NestedMarker
    {

        private String unknown1;

        public void setUnknown1(String unknown1) {
            this.unknown1 = unknown1;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("NestedUnknown7[");
            sb.append("unknown1=").append(this.unknown1 .toString()).append("]");
            return sb.toString();
        }

    }

}
