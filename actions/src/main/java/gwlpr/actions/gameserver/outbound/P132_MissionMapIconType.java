
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P132_MissionMapIconType
    extends GenericAction
{

    private long localID;
    private long iconHash;

    public short getHeader() {
        return  132;
    }

    public void setLocalID(long localID) {
        this.localID = localID;
    }

    public void setIconHash(long iconHash) {
        this.iconHash = iconHash;
    }

}
