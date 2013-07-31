/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * Code taken from iDemmel (with permission).
 * 
 * TODO: test nested
 * 
 * @author _rusty
 */
public class DeserializationTest 
{
    @Test
    public void test()
    {
        // create a new deserializer
        SerializationFilter packetDeserializer = SerializationFilterFactory.produceSerializer(P008_TestPacket.class);
        
        // build the fake inputstream
        // the stream represents the data shown in the "assertEqual" tests
        byte[] bytes = new byte[] {8, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 20, 21, 22, 23, 24, 25, 4, 0, 116, 0, 111, 0, 116, 0, 111, 0, 4, 0, 26, 27, 28, 29, 5, 0, 0, 0, 5, 0, 30, 31, 32, 33, 34, 6, 7, 0, 0, 0, 4, 0, 116, 0, 105, 0, 116, 0, 105, 0};

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // the header isn't parsed by using reflection
        buffer.getShort();
        
        // deserialize the object
        P008_TestPacket testIncomingPacket = new P008_TestPacket();
        packetDeserializer.deserialize(buffer, testIncomingPacket);

        // test that the deserializer correctly extracted all values
        assertEquals(1, testIncomingPacket.getUnsignedInteger1());
        assertEquals(2, testIncomingPacket.getUnsignedInteger2());
        assertEquals(3, testIncomingPacket.getUnsignedShort1());
        
        assertEquals(1, testIncomingPacket.getConstantUnsignedByteArray1().length);
        assertEquals(20, testIncomingPacket.getConstantUnsignedByteArray1()[0]);
        
        assertEquals(2, testIncomingPacket.getConstantUnsignedByteArray2().length);
        assertEquals(21, testIncomingPacket.getConstantUnsignedByteArray2()[0]);
        assertEquals(22, testIncomingPacket.getConstantUnsignedByteArray2()[1]);
        
        assertEquals(3, testIncomingPacket.getConstantUnsignedByteArray3().length);
        assertEquals(23, testIncomingPacket.getConstantUnsignedByteArray3()[0]);
        assertEquals(24, testIncomingPacket.getConstantUnsignedByteArray3()[1]);
        assertEquals(25, testIncomingPacket.getConstantUnsignedByteArray3()[2]);
        
        assertEquals("toto", testIncomingPacket.getString1());
        assertEquals(4, testIncomingPacket.getUnsignedShort2());
        
        assertEquals(4, testIncomingPacket.getConstantUnsignedByteArray4().length);
        assertEquals(26, testIncomingPacket.getConstantUnsignedByteArray4()[0]);
        assertEquals(27, testIncomingPacket.getConstantUnsignedByteArray4()[1]);
        assertEquals(28, testIncomingPacket.getConstantUnsignedByteArray4()[2]);
        assertEquals(29, testIncomingPacket.getConstantUnsignedByteArray4()[3]);
        
        assertEquals(5, testIncomingPacket.getUnsignedInteger3());
        
        assertEquals(5, testIncomingPacket.getVariableUnsignedByteArray1().length);
        assertEquals(30, testIncomingPacket.getVariableUnsignedByteArray1()[0]);
        assertEquals(31, testIncomingPacket.getVariableUnsignedByteArray1()[1]);
        assertEquals(32, testIncomingPacket.getVariableUnsignedByteArray1()[2]);
        assertEquals(33, testIncomingPacket.getVariableUnsignedByteArray1()[3]);
        assertEquals(34, testIncomingPacket.getVariableUnsignedByteArray1()[4]);
        
        assertEquals(6, testIncomingPacket.getUnsignedByte1());
        assertEquals(7, testIncomingPacket.getUnsignedInteger4());
        assertEquals("titi", testIncomingPacket.getString2());
    }
}
