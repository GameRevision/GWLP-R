
package gwlpr.protocol.loginserver.outbound;

import java.util.Arrays;
import gwlpr.protocol.serialization.GWMessage;
import gwlpr.protocol.util.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P034_Unknown
    extends GWMessage
{

    private long unknown1;
    @IsArray(constant = true, size = 12, prefixLength = -1)
    private byte[] unknown2;

    @Override
    public short getHeader() {
        return  34;
    }

    public void setUnknown1(long unknown1) {
        this.unknown1 = unknown1;
    }

    public void setUnknown2(byte[] unknown2) {
        this.unknown2 = unknown2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P034_Unknown[");
        sb.append("unknown1=").append(this.unknown1).append(",unknown2=").append(Arrays.toString(this.unknown2)).append("]");
        return sb.toString();
    }

}
