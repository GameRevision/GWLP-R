
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P068_QuestAdd
    extends GenericAction
{

    private long iD;
    private long status;
    private String category;
    private String name;
    private String givenBy;
    private int mapID;

    public short getHeader() {
        return  68;
    }

    public void setID(long iD) {
        this.iD = iD;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

}
