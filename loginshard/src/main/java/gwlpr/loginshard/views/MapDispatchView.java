/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard.views;

import gwlpr.protocol.loginserver.outbound.P009_ReferToGameServer;
import gwlpr.loginshard.models.ClientBean;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;


/**
 * This view assembles the actions needed to dispatch a client to a game server.
 * 
 * @author miracle444, _rusty
 */
public class MapDispatchView
{
    
    /**
     * This method is used to refer the client (session) to a game server.
     * Note that the UUIDs of the internal client and server are only given
     * to the client as their hashCode()
     * 
     * @param       channel
     * @param       address                 Of the map shard's server socket.
     * @param       serverUid
     * @param       clientUid
     * @param       gameMapId 
     */
    public static void referToGameServer(Channel channel, InetSocketAddress address, UUID serverUid, UUID clientUid, int gameMapId)
    {
        P009_ReferToGameServer referToGameServer = new P009_ReferToGameServer();
        
        referToGameServer.init(channel);
        referToGameServer.setLoginCount(ClientBean.get(channel).getLoginCount());
        referToGameServer.setSecurityKey1(serverUid.hashCode());
        referToGameServer.setGameMapID(gameMapId);
        
        ByteBuffer buf = ByteBuffer.allocate(24);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putShort((short) 2);
        buf.order(ByteOrder.BIG_ENDIAN);
        buf.putShort((short) address.getPort()); // DONT FORGET THIS NEEDS TO BE BIG ENDIAN!!!!
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.put(address.getAddress().getAddress());

        referToGameServer.setServerConnectionInfo(buf.array());
        referToGameServer.setSecurityKey2(clientUid.hashCode());
        
        channel.writeAndFlush(referToGameServer);
    }
}
