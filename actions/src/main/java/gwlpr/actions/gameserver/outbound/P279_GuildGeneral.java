
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * This can be used to display a guild in the guild
 * window and/or apply a cape to player agent(s).
 * 
 */
public final class P279_GuildGeneral
    extends GenericAction
{

    private int localID;
    @IsArray(constant = true, size = 16, prefixLength = 1)
    private short[] guildHallData;
    private String guildName;
    private String guildTag;
    private short features;
    private short territory;
    private short capeBackgroundColor;
    private short capePatternColor;
    private short capeEmblemColor;
    private short capeShape;
    private short capePattern;
    private int capeEmblem;
    private short capeTrim;
    private short allegiance;
    private long allianceFaction;
    private long qualifierPoints;
    private long rating;
    private long rank;
    private short unknown1;

    public short getHeader() {
        return  279;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }

    public void setGuildHallData(short[] guildHallData) {
        this.guildHallData = guildHallData;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public void setGuildTag(String guildTag) {
        this.guildTag = guildTag;
    }

    public void setFeatures(short features) {
        this.features = features;
    }

    public void setTerritory(short territory) {
        this.territory = territory;
    }

    public void setCapeBackgroundColor(short capeBackgroundColor) {
        this.capeBackgroundColor = capeBackgroundColor;
    }

    public void setCapePatternColor(short capePatternColor) {
        this.capePatternColor = capePatternColor;
    }

    public void setCapeEmblemColor(short capeEmblemColor) {
        this.capeEmblemColor = capeEmblemColor;
    }

    public void setCapeShape(short capeShape) {
        this.capeShape = capeShape;
    }

    public void setCapePattern(short capePattern) {
        this.capePattern = capePattern;
    }

    public void setCapeEmblem(int capeEmblem) {
        this.capeEmblem = capeEmblem;
    }

    public void setCapeTrim(short capeTrim) {
        this.capeTrim = capeTrim;
    }

    public void setAllegiance(short allegiance) {
        this.allegiance = allegiance;
    }

    public void setAllianceFaction(long allianceFaction) {
        this.allianceFaction = allianceFaction;
    }

    public void setQualifierPoints(long qualifierPoints) {
        this.qualifierPoints = qualifierPoints;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public void setUnknown1(short unknown1) {
        this.unknown1 = unknown1;
    }

}
