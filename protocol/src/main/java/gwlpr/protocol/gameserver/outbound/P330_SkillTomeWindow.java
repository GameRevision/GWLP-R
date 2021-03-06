
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P330_SkillTomeWindow
    extends GWMessage
{

    private long itemLocalID;
    private short profession;
    private short isElite;

    @Override
    public short getHeader() {
        return  330;
    }

    public void setItemLocalID(long itemLocalID) {
        this.itemLocalID = itemLocalID;
    }

    public void setProfession(short profession) {
        this.profession = profession;
    }

    public void setIsElite(short isElite) {
        this.isElite = isElite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P330_SkillTomeWindow[");
        sb.append("itemLocalID=").append(this.itemLocalID).append(",profession=").append(this.profession).append(",isElite=").append(this.isElite).append("]");
        return sb.toString();
    }

}
