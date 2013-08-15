/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.protocol.gameserver.outbound.P044_AgentAttributeCreate;
import io.netty.channel.Channel;


/**
 * This view fills the UpdateAttributePoints action.
 *
 * @author miracle444
 */
public class EntityUpdateAttributesView
{

    public static void sendInitial(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(AgentIdentifiers.class).agentID;
        CharData charData = entity.get(CharData.class);
        
        sendInitial(channel, agentID, charData.attributeFreePts, charData.attributeMaxPts);
    }
    
    
    public static void sendInitial(Channel channel, int agentID, int freePts, int maxPts)
    {        
        P044_AgentAttributeCreate updateAttributePoints = new P044_AgentAttributeCreate();
        updateAttributePoints.init(channel);
        updateAttributePoints.setAgentID(agentID);
        updateAttributePoints.setFreePts((byte) freePts);
        updateAttributePoints.setMaxPts((byte) maxPts);

        channel.writeAndFlush(updateAttributePoints);
    }
}
