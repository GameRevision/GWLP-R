/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import gwlpr.protocol.loginserver.LoginServerCodec;
import gwlpr.protocol.util.Vector4;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
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
        LoginServerCodec.registerInbound(P008_TestPacket.class);
        
        LoginServerCodec codec = new LoginServerCodec();
        
        // enable this for profiling
//        for (int i = 0; i < 1000000; i++) 
//        {
            // create a new empty TestIncomingPacket and the buffer
            P008_TestPacket outgoing = P008_TestPacket.getMockUp();
            ByteBuf buffer = Unpooled.buffer(512);

            // serialize it
            codec.encode(null, outgoing, buffer);

            // deserialize it (dont forget the header!)
            List<Object> incoming = new ArrayList<>();
            codec.decode(null, buffer, incoming);
            
            // compare them (disable this when profiling)
            P008_TestPacket.assertCompare(outgoing, (P008_TestPacket)incoming.get(0));
//        }        
    }
}
