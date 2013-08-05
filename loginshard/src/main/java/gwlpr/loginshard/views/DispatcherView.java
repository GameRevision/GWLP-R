/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P009_ReferToGameServer;
import gwlpr.loginshard.ChannelAttachment;
import io.netty.channel.Channel;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This view assembles the actions needed to dispatch a client to a game server.
 * 
 * @author miracle444, _rusty
 */
public class DispatcherView
{
    
    private static Logger LOGGER = LoggerFactory.getLogger(DispatcherView.class);

    
    /**
     * This method is used to refer the client (session) to a game server.
     * 
     * @param       channel
     * @param       ip 
     * @param       port 
     * @param       key1
     * @param       key2
     * @param       mapId 
     */
    public static void referToGameServer(Channel channel, String ip, int port, int key1, int key2, int mapId)
    {
        P009_ReferToGameServer referToGameServer = new P009_ReferToGameServer();
        
        referToGameServer.init(channel);
        referToGameServer.setLoginCount(channel.attr(ChannelAttachment.KEY).get().getLoginCount());
        referToGameServer.setSecurityKey1(key1);
        referToGameServer.setGameMapID(mapId);
        
        ByteBuffer buf = ByteBuffer.allocate(24);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putShort((short) 2);
        buf.order(ByteOrder.BIG_ENDIAN);
        buf.putShort((short) port); // DONT FORGET THIS NEEDS TO BE BIG ENDIAN!!!!
        buf.order(ByteOrder.LITTLE_ENDIAN);
        
        try { buf.put(Inet4Address.getByName(ip).getAddress()); }
        catch (UnknownHostException ex) 
        {
            LOGGER.error(String.format("Unkown host: [{}]", ip), ex);
        }
        
        referToGameServer.setServerConnectionInfo(buf.array());
        referToGameServer.setSecurityKey2(key2);
        
        channel.write(referToGameServer);
    }
}
