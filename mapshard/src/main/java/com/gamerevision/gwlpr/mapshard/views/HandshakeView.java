/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P370_InstanceLoadHeadAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P371_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P395_InstanceLoadDistrictInfoAction;
import com.gamerevision.gwlpr.actions.loginserver.stoc.P5633_ServerSeedAction;
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
    public static P5633_ServerSeedAction serverSeed(Session session)
    {
        P5633_ServerSeedAction serverSeed = new P5633_ServerSeedAction();
        serverSeed.init(session);
        serverSeed.setServerSeed(new byte[20]);
        return serverSeed;
    }
    
    
    /**
     * Step 2
     */
    public static P370_InstanceLoadHeadAction instanceHead(Session session)
    {
        P370_InstanceLoadHeadAction instanceLoadHead = new P370_InstanceLoadHeadAction();
        instanceLoadHead.init(session);
        instanceLoadHead.setData1((byte) 0x1F);
        instanceLoadHead.setData2((byte) 0x1F);
        instanceLoadHead.setData3((byte) 0);
        instanceLoadHead.setData4((byte) 0);
        return instanceLoadHead;
    }
    
    
    /**
     * Step 3
     */
    public static P371_UnknownAction charName(Session session, String name) 
    {
        P371_UnknownAction instanceLoadCharName = new P371_UnknownAction();
        instanceLoadCharName.init(session);
        instanceLoadCharName.setUnknown1(name.toCharArray());
        return instanceLoadCharName;
    }
    
    
    /**
     * Step 4
     */
    public static P395_InstanceLoadDistrictInfoAction districtInfo(Session session, int mapId)
    {
        P395_InstanceLoadDistrictInfoAction instanceLoadDistrictInfo = new P395_InstanceLoadDistrictInfoAction();
        instanceLoadDistrictInfo.init(session);
        instanceLoadDistrictInfo.setCharAgent(1);
        instanceLoadDistrictInfo.setDistrictAndRegion(0);
        instanceLoadDistrictInfo.setLanguage((byte) 0);
        instanceLoadDistrictInfo.setMapID((short) mapId);
        instanceLoadDistrictInfo.setisExplorable((byte) 1);
        instanceLoadDistrictInfo.setisObserver((byte) 0);
        return instanceLoadDistrictInfo;
    }
}
