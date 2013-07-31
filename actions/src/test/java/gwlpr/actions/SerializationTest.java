/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Code taken from iDemmel (with permission).
 * 
 * TODO: test nested
 * 
 * @author _rusty
 */
public class SerializationTest 
{
    static final Logger LOGGER = LoggerFactory.getLogger(SerializationTest.class);
    
    @Test
    public void test()
    {
        // create a new serializer
        SerializationFilter packetSerializer = SerializationFilterFactory.produceSerializer(P008_TestPacket.class);

        // create a new empty TestIncomingPacket
        P008_TestPacket testIncomingPacket = new P008_TestPacket();
        testIncomingPacket.setUnsignedInteger1(1);
        testIncomingPacket.setUnsignedInteger2(2);
        testIncomingPacket.setUnsignedShort1(3);
        testIncomingPacket.setConstantUnsignedByteArray1(new short[]{20});
        testIncomingPacket.setConstantUnsignedByteArray2(new short[]{21, 22});
        testIncomingPacket.setConstantUnsignedByteArray3(new short[]{23, 24, 25});
        testIncomingPacket.setString1("toto");
        testIncomingPacket.setUnsignedShort2(4);
        testIncomingPacket.setConstantUnsignedByteArray4(new short[]{26, 27, 28, 29});
        testIncomingPacket.setUnsignedInteger3(5);
        testIncomingPacket.setVariableUnsignedByteArray1(new short[]{30, 31, 32, 33, 34});
        testIncomingPacket.setUnsignedByte1((short) 6);
        testIncomingPacket.setUnsignedInteger4(7);
        testIncomingPacket.setString2("titi");

        // build the buffer that should have the same data
        byte[] bytes = new byte[] {8, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 20, 21, 22, 23, 24, 25, 4, 0, 116, 0, 111, 0, 116, 0, 111, 0, 4, 0, 26, 27, 28, 29, 5, 0, 0, 0, 5, 0, 30, 31, 32, 33, 34, 6, 7, 0, 0, 0, 4, 0, 116, 0, 105, 0, 116, 0, 105, 0};

        ByteBuffer requiredBuffer = ByteBuffer.wrap(bytes);
        requiredBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        // build the buffer that will contain all the generated output data
        ByteBuffer outputBuffer = ByteBuffer.allocate(70);
        outputBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        // serialize the given packet
        // but put the header before doing so...
        outputBuffer.putShort((short)8);
        packetSerializer.serialize(outputBuffer, testIncomingPacket);

        // compare the buffers
        outputBuffer.flip();
        
        while (requiredBuffer.hasRemaining() || outputBuffer.hasRemaining()) 
        {
            assert requiredBuffer.hasRemaining();
            assert outputBuffer.hasRemaining();
            
            byte r = requiredBuffer.get();
            byte o = outputBuffer.get();
            
            LOGGER.info(String.format("Required: %d \tFound: %d", r, o));
            assertEquals(r, o);
        }
    }
}
