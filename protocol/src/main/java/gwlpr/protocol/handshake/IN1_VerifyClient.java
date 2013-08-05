/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.handshake;

import gwlpr.protocol.serialization.GWMessage;
import gwlpr.protocol.util.IsArray;


/**
 * Generated by hand :P
 * This packet has actually its own length as a header.
 * 
 * @author _rusty
 */
public class IN1_VerifyClient
    extends GWMessage
{
    private int data1;
    private long data2;
    private long data3;
    @IsArray(constant = true, size = 4, prefixLength = -1)
    private byte[] key1;
    private long mapId;
    @IsArray(constant = true, size = 4, prefixLength = -1)
    private byte[] key2;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    private byte[] accountHash;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    private byte[] characterHash;
    private long data5;
    private long data6;

    @Override
    public short getHeader() {
        return 1280;
    }

    public int getData1() {
        return data1;
    }

    public long getData2() {
        return data2;
    }

    public long getData3() {
        return data3;
    }

    public byte[] getKey1() {
        return key1;
    }

    public long getMapId() {
        return mapId;
    }

    public byte[] getKey2() {
        return key2;
    }

    public byte[] getAccountHash() {
        return accountHash;
    }

    public byte[] getCharacterHash() {
        return characterHash;
    }

    public long getData5() {
        return data5;
    }

    public long getData6() {
        return data6;
    }
}
