
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P105_HeroDestroy
    extends GenericAction
{

    private long agentID;

    public short getHeader() {
        return  105;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

}
