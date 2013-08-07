/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P370_InstanceLoadHead;
import gwlpr.protocol.gameserver.outbound.P371_UnknownAction;
import gwlpr.protocol.gameserver.outbound.P395_InstanceLoadDistrictInfo;
import gwlpr.database.entities.Map;
import io.netty.channel.Channel;
import realityshard.shardlet.Session;


/**
 * Handshake actions.
 * 
 * @author miracle444, _rusty
 */
public class HandshakeView
{    
    
    /**
     * Step 1
     */
    public static void instanceHead(Channel channel)
    {
        P370_InstanceLoadHead instanceLoadHead = new P370_InstanceLoadHead();
        instanceLoadHead.init(channel);
        instanceLoadHead.setUnknown1((byte) 0x1F);
        instanceLoadHead.setUnknown2((byte) 0x1F);
        instanceLoadHead.setUnknown3((byte) 0);
        instanceLoadHead.setUnknown4((byte) 0);
        
        channel.write(instanceLoadHead);
    }
    
    
    /**
     * Step 2
     */
    public static void charName(Channel channel, String name) 
    {
        P371_UnknownAction instanceLoadCharName = new P371_UnknownAction();
        instanceLoadCharName.init(session);
        instanceLoadCharName.setUnknown1(name.toCharArray());
        
        session.send(instanceLoadCharName);
    }
    
    
    /**
     * Step 3
     */
    public static void districtInfo(Session session, Map map)
    {
        P395_InstanceLoadDistrictInfoAction instanceLoadDistrictInfo = new P395_InstanceLoadDistrictInfoAction();
        instanceLoadDistrictInfo.init(session);
        instanceLoadDistrictInfo.setCharAgent(localID);
        instanceLoadDistrictInfo.setDistrictAndRegion(0);
        instanceLoadDistrictInfo.setLanguage((byte) 0);
        instanceLoadDistrictInfo.setMapID((short) mapId);
        instanceLoadDistrictInfo.setisExplorable((byte) 1);
        instanceLoadDistrictInfo.setisObserver((byte) 0);
        
        session.send(instanceLoadDistrictInfo);
    }
}
