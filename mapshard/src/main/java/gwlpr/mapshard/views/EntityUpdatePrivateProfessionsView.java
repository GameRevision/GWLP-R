/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.mapshard.entitysystem.Components;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.protocol.gameserver.outbound.P171_UpdatePrivProfessions;
import io.netty.channel.Channel;


/**
 * This view fills a UpdatePrivateProfessions action
 *
 * @author miracle444
 */
public class EntityUpdatePrivateProfessionsView
{

    public static void send(Channel channel, Entity entity)
    {
        // retrieve the entity related data we need...
        int agentID = entity.get(Components.AgentIdentifiers.class).agentID;
        Components.CharData charData = entity.get(Components.CharData.class);
        
        send(channel, agentID, charData.primary.ordinal(), charData.secondary.ordinal(), true);
    }
    
    public static void send(Channel channel, int agentID, int primary, int secondary, boolean isPvP)
    {
        P171_UpdatePrivProfessions updatePrivateProfessions = new P171_UpdatePrivProfessions();
        updatePrivateProfessions.init(channel);
        updatePrivateProfessions.setAgentID(agentID);
        updatePrivateProfessions.setPrimaryProf((byte) primary);
        updatePrivateProfessions.setSecondaryProf((byte) secondary);
        updatePrivateProfessions.setIsPvP((byte) (isPvP ? 1 : 0));

        channel.writeAndFlush(updatePrivateProfessions);
    }
}