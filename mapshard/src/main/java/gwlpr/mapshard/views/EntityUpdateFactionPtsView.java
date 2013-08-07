/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;

import gwlpr.mapshard.entitysystem.Components.*;
import gwlpr.mapshard.entitysystem.Entity;
import gwlpr.mapshard.models.enums.Faction;
import gwlpr.protocol.gameserver.outbound.P221_UpdateFaction;
import io.netty.channel.Channel;
import java.util.Map;


/**
 * Use this to send the faction and chardata related packets
 * 
 * @author _rusty
 */
public class EntityUpdateFactionPtsView 
{
    
    public static void sendFactionData(Channel channel, Entity entity)
    {
        // retrieve some entity components
        CharData charData = entity.get(CharData.class);
        Map<Faction, Integer[]> factionData = entity.get(FactionData.class).factionPoints;
        
        P221_UpdateFaction updateFaction = new P221_UpdateFaction();
        updateFaction.init(channel);
        updateFaction.setLevel(charData.level);
        updateFaction.setMorale(charData.morale);
        updateFaction.setExperience(charData.experience);
        
        if (factionData.containsKey(Faction.Kurzick))
        {
            updateFaction.setKurzickFree(factionData.get(Faction.Kurzick)[0]);
            updateFaction.setKurzickTotal(factionData.get(Faction.Kurzick)[1]);
        }
        
        if (factionData.containsKey(Faction.Luxon))
        {
            updateFaction.setLuxonFree(factionData.get(Faction.Luxon)[0]);
            updateFaction.setLuxonTotal(factionData.get(Faction.Luxon)[1]);
        }
        
        if (factionData.containsKey(Faction.Imperial))
        {
            updateFaction.setImperialFree(factionData.get(Faction.Imperial)[0]);
            updateFaction.setImperialTotal(factionData.get(Faction.Imperial)[1]);
        }
        
        if (factionData.containsKey(Faction.Balthazar))
        {
            updateFaction.setBalthFree(factionData.get(Faction.Balthazar)[0]);
            updateFaction.setBalthTotal(factionData.get(Faction.Balthazar)[1]);
        }

        channel.write(updateFaction);
    }
}
