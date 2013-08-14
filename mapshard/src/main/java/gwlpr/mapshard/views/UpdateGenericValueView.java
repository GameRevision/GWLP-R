/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.protocol.gameserver.outbound.P147_UpdateGenericValue;
import gwlpr.protocol.gameserver.outbound.P148_UpdateTargetGenericValue;
import gwlpr.protocol.gameserver.outbound.P150_UpdateGenericModifier;
import gwlpr.protocol.gameserver.outbound.P151_UpdateTargetGenericModifier;
import gwlpr.mapshard.models.enums.GenericValue;
import io.netty.channel.Channel;


/**
 * This view fills the UpdateGenericValue action.
 *
 * This is used to send different packets, depending on the input.
 * See the nested enum for the known values
 *
 * @author miracle444, _rusty
 */
public class UpdateGenericValueView
{
    
    public static void send(Channel channel, int agentID, GenericValue valueID, int value)
    {
        P147_UpdateGenericValue updateGenericValueInt = new P147_UpdateGenericValue();
        updateGenericValueInt.init(channel);
        updateGenericValueInt.setValueID(valueID.ordinal());
        updateGenericValueInt.setAgentID(agentID);
        updateGenericValueInt.setValue(value);

        channel.writeAndFlush(updateGenericValueInt);
    }

    public static void send(Channel channel, int agentID, GenericValue valueID, float value)
    {
        P150_UpdateGenericModifier updateGenericValueFloat = new P150_UpdateGenericModifier();
        updateGenericValueFloat.init(channel);
        updateGenericValueFloat.setValueID(valueID.ordinal());
        updateGenericValueFloat.setAgentID(agentID);
        updateGenericValueFloat.setValue(Float.floatToRawIntBits(value));

        channel.writeAndFlush(updateGenericValueFloat);
    }

    public static void send(Channel channel, int targetAgentID, int casterAgentID, GenericValue valueID, int value)
    {
        P148_UpdateTargetGenericValue updateGenericValueTarget = new P148_UpdateTargetGenericValue();
        updateGenericValueTarget.init(channel);
        updateGenericValueTarget.setValueID(valueID.ordinal());
        updateGenericValueTarget.setTarget(targetAgentID);
        updateGenericValueTarget.setCaster(casterAgentID);
        updateGenericValueTarget.setValue(value);

        channel.writeAndFlush(updateGenericValueTarget);
    }

    public static void send(Channel channel, int targetAgentID, int casterAgentID, GenericValue valueID, float value)
    {
        P151_UpdateTargetGenericModifier updateGenericValueModifier = new P151_UpdateTargetGenericModifier();
        updateGenericValueModifier.init(channel);
        updateGenericValueModifier.setValueID(valueID.ordinal());
        updateGenericValueModifier.setTarget(targetAgentID);
        updateGenericValueModifier.setCaster(casterAgentID);
        updateGenericValueModifier.setValue(Float.floatToRawIntBits(value));

        channel.writeAndFlush(updateGenericValueModifier);
    }
}
