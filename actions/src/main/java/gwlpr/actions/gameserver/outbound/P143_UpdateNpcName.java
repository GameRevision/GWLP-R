
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P143_UpdateNpcName
    extends GenericAction
{

    private long agentID;
    private String name;

    public short getHeader() {
        return  143;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public void setName(String name) {
        this.name = name;
    }

}
