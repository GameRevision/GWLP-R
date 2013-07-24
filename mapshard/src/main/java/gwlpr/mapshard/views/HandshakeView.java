/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.actions.gameserver.stoc.P370_InstanceLoadHeadAction;
import gwlpr.actions.gameserver.stoc.P371_UnknownAction;
import gwlpr.actions.gameserver.stoc.P395_InstanceLoadDistrictInfoAction;
import gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
import com.realityshard.shardlet.Session;


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
    public static void serverSeed(Session session)
    {
        P5633_ServerSeedAction serverSeed = new P5633_ServerSeedAction();
        serverSeed.init(session);
        serverSeed.setServerSeed(new byte[20]);
        
        session.send(serverSeed);
    }
    
    
    /**
     * Step 2
     */
    public static void instanceHead(Session session)
    {
        P370_InstanceLoadHeadAction instanceLoadHead = new P370_InstanceLoadHeadAction();
        instanceLoadHead.init(session);
        instanceLoadHead.setData1((byte) 0x1F);
        instanceLoadHead.setData2((byte) 0x1F);
        instanceLoadHead.setData3((byte) 0);
        instanceLoadHead.setData4((byte) 0);
        
        session.send(instanceLoadHead);
    }
    
    
    /**
     * Step 3
     */
    public static void charName(Session session, String name) 
    {
        P371_UnknownAction instanceLoadCharName = new P371_UnknownAction();
        instanceLoadCharName.init(session);
        instanceLoadCharName.setUnknown1(name.toCharArray());
        
        session.send(instanceLoadCharName);
    }
    
    
    /**
     * Step 4
     */
    public static void districtInfo(Session session, int localID, int mapId)
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
