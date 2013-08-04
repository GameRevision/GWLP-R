/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.loginserver;

import gwlpr.protocol.NettyGWCodec;
import gwlpr.protocol.serialization.GWMessage;
import gwlpr.protocol.serialization.GWMessageSerializationRegistry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Register the login server packets here.
 * 
 * @author _rusty
 */
public class LoginServerCodec extends NettyGWCodec
{
    
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginServerCodec.class);
    private final static Map<Integer, Class<? extends GWMessage>> MESSAGES = new ConcurrentHashMap<>();
            
    
    static 
    {
        for (Class<? extends GWMessage> clazz : new Reflections("gwlpr.protocol.loginserver.inbound").getSubTypesOf(GWMessage.class)) 
        {
           registerInbound(clazz);
        }
        
        for (Class<? extends GWMessage> clazz : new Reflections("gwlpr.protocol.loginserver.outbound").getSubTypesOf(GWMessage.class)) 
        {
           registerOutbound(clazz);
        }
    }
    
    
    /**
     * Register an inbound message.
     * 
     * @param message 
     */
    public static void registerInbound(Class<? extends GWMessage> message)
    {
        try 
        {
            MESSAGES.put((int)message.newInstance().getHeader(), message);
        } 
        catch (InstantiationException | IllegalAccessException ex) 
        {
            LOGGER.error("Couldnt register an message: Couldnt create an instance of it.", ex);
            return;
        }
        
        GWMessageSerializationRegistry.register(message);
    }
    
    
    /**
     * Register an outbound message.
     * 
     * @param message 
     */
    public static void registerOutbound(Class<? extends GWMessage> message)
    {
        GWMessageSerializationRegistry.register(message);
    }
    
    
    @Override
    protected Class<? extends GWMessage> getByHeader(int header) 
    {
        return MESSAGES.get(header);
    }
}
