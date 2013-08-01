/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.gameserver.outbound.P343_ItemGeneral;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import java.nio.ByteBuffer;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Code test for the serialization stuff.
 * This is no TDD, so dont complain about the shitty test ;)
 * 
 * Hint: for loop used for profiling.
 * 
 * @author _rusty
 */
public class SerializationTest 
{
    
    static final Logger LOGGER = LoggerFactory.getLogger(SerializationTest.class);
    
    
    @Test
    public void test()
    {
        // fake register our test packet as inbound login server packet...
        // this should be done statically in the packet
        LoginServerActionFactory.registerInbound(P008_TestPacket.class);
        
        LoginServerActionFactory factory = new LoginServerActionFactory();
        
        // enable this for profiling
//        for (int i = 0; i < 1000000; i++) 
//        {
            // create a new empty TestIncomingPacket
            P008_TestPacket outgoing = P008_TestPacket.getMockUp();

            // serialize it
            ByteBuffer buffer = factory.doOutFilter(outgoing);
            buffer.flip();

            // deserialize it (dont forget the header!)
            P008_TestPacket incoming = (P008_TestPacket) factory.doInFilter(buffer).get(0);
            
            // compare them (disable this when profiling)
            P008_TestPacket.assertCompare(outgoing, incoming);
//        }        
    }
}
