
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P147_UpdateGenericValue
    extends GenericAction
{

    private long valueID;
    private long agentID;
    private long value;

    public short getHeader() {
        return  147;
    }

    public void setValueID(long valueID) {
        this.valueID = valueID;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
