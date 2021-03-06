
package gwlpr.protocol.gameserver.outbound;

import gwlpr.protocol.serialization.GWMessage;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P395_InstanceLoadDistrictInfo
    extends GWMessage
{

    private long charAgent;
    private int mapID;
    private short isExplorable;
    /**
     * 
     * Differs from the dumps: short + short, not int.
     * 
     */
    private int districtNumber;
    /**
     * 
     * Differs from the dumps: short + short, not int.
     * 
     */
    private int region;
    private short language;
    private short isObserver;

    @Override
    public short getHeader() {
        return  395;
    }

    public void setCharAgent(long charAgent) {
        this.charAgent = charAgent;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public void setIsExplorable(short isExplorable) {
        this.isExplorable = isExplorable;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setLanguage(short language) {
        this.language = language;
    }

    public void setIsObserver(short isObserver) {
        this.isObserver = isObserver;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("P395_InstanceLoadDistrictInfo[");
        sb.append("charAgent=").append(this.charAgent).append(",mapID=").append(this.mapID).append(",isExplorable=").append(this.isExplorable).append(",districtNumber=").append(this.districtNumber).append(",region=").append(this.region).append(",language=").append(this.language).append(",isObserver=").append(this.isObserver).append("]");
        return sb.toString();
    }

}
