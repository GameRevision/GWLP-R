/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.utils.IsArray;
import org.junit.Ignore;
import realityshard.shardlet.utils.GenericAction;


/**
 * Code taken from iDemmel (with permission).
 * 
 * @author _rusty
 */
@Ignore
public class P008_TestPacket extends GenericAction 
{
    private long unsignedInteger1;
    private long unsignedInteger2;
    private int unsignedShort1;
    @IsArray(constant=true, size=1, prefixLength=2)
    private short[] constantUnsignedByteArray1;
    @IsArray(constant=true, size=2, prefixLength=2)
    private short[] constantUnsignedByteArray2;
    @IsArray(constant=true, size=3, prefixLength=2)
    private short[] constantUnsignedByteArray3;
    private String string1;
    private int unsignedShort2;
    @IsArray(constant=true, size=4, prefixLength=2)
    private short[] constantUnsignedByteArray4;
    private long unsignedInteger3;
    @IsArray(constant=false, size=5, prefixLength=2)
    private short[] variableUnsignedByteArray1;
    private short unsignedByte1;
    private long unsignedInteger4;
    private String string2;

    public short[] getConstantUnsignedByteArray1() {
        return constantUnsignedByteArray1;
    }

    public void setConstantUnsignedByteArray1(short[] constantUnsignedByteArray1) {
        this.constantUnsignedByteArray1 = constantUnsignedByteArray1;
    }

    public short[] getConstantUnsignedByteArray2() {
        return constantUnsignedByteArray2;
    }

    public void setConstantUnsignedByteArray2(short[] constantUnsignedByteArray2) {
        this.constantUnsignedByteArray2 = constantUnsignedByteArray2;
    }

    public short[] getConstantUnsignedByteArray3() {
        return constantUnsignedByteArray3;
    }

    public void setConstantUnsignedByteArray3(short[] constantUnsignedByteArray3) {
        this.constantUnsignedByteArray3 = constantUnsignedByteArray3;
    }

    public short[] getConstantUnsignedByteArray4() {
        return constantUnsignedByteArray4;
    }

    public void setConstantUnsignedByteArray4(short[] constantUnsignedByteArray4) {
        this.constantUnsignedByteArray4 = constantUnsignedByteArray4;
    }

    public long getUnsignedInteger4() {
        return unsignedInteger4;
    }

    public void setUnsignedInteger4(long unsignedInteger4) {
        this.unsignedInteger4 = unsignedInteger4;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public short getUnsignedByte1() {
        return unsignedByte1;
    }

    public void setUnsignedByte1(short unsignedByte1) {
        this.unsignedByte1 = unsignedByte1;
    }

    public long getUnsignedInteger1() {
        return unsignedInteger1;
    }

    public void setUnsignedInteger1(long unsignedInteger1) {
        this.unsignedInteger1 = unsignedInteger1;
    }

    public long getUnsignedInteger2() {
        return unsignedInteger2;
    }

    public void setUnsignedInteger2(long unsignedInteger2) {
        this.unsignedInteger2 = unsignedInteger2;
    }

    public long getUnsignedInteger3() {
        return unsignedInteger3;
    }

    public void setUnsignedInteger3(long unsignedInteger3) {
        this.unsignedInteger3 = unsignedInteger3;
    }

    public int getUnsignedShort1() {
        return unsignedShort1;
    }

    public void setUnsignedShort1(int unsignedShort1) {
        this.unsignedShort1 = unsignedShort1;
    }

    public int getUnsignedShort2() {
        return unsignedShort2;
    }

    public void setUnsignedShort2(int unsignedShort2) {
        this.unsignedShort2 = unsignedShort2;
    }

    public short[] getVariableUnsignedByteArray1() {
        return variableUnsignedByteArray1;
    }

    public void setVariableUnsignedByteArray1(short[] variableUnsignedByteArray1) {
        this.variableUnsignedByteArray1 = variableUnsignedByteArray1;
    }
}
