/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P370_InstanceLoadHeadAction;
import com.realityshard.shardlet.Session;


/**
 * This view fills the InstanceLoadHead action.
 * 
 * @author miracle444
 */
public class InstanceLoadHeadView
{

    public static P370_InstanceLoadHeadAction create(Session session)
    {
        P370_InstanceLoadHeadAction instanceLoadHead = new P370_InstanceLoadHeadAction();
        instanceLoadHead.init(session);
        instanceLoadHead.setData1((byte) 0x1F);
        instanceLoadHead.setData2((byte) 0x1F);
        instanceLoadHead.setData3((byte) 0);
        instanceLoadHead.setData4((byte) 0);
        return instanceLoadHead;
    }
}
