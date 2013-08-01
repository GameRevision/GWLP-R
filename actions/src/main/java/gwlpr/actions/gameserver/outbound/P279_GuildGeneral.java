
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.gameserver.GameServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * This can be used to display a guild in the guild
 * window and/or apply a cape to player agent(s).
 * 
 */
public final class P279_GuildGeneral
    extends GWAction
{

    public int localID;
    @IsArray(constant = true, size = 16, prefixLength = -1)
    public byte[] guildHallData;
    public String guildName;
    public String guildTag;
    public short features;
    public short territory;
    public short capeBackgroundColor;
    public short capePatternColor;
    public short capeEmblemColor;
    public short capeShape;
    public short capePattern;
    public int capeEmblem;
    public short capeTrim;
    public short allegiance;
    public long allianceFaction;
    public long qualifierPoints;
    public long rating;
    public long rank;
    public short unknown1;

    static {
        GameServerActionFactory.registerOutbound(P279_GuildGeneral.class);
    }

    @Override
    public short getHeader() {
        return  279;
    }

}
