
package gwlpr.actions.loginserver.inbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P001_ComputerUser
    extends GenericAction
{

    private String userName;
    private String computerName;

    public short getHeader() {
        return  1;
    }

    public String getUserName() {
        return userName;
    }

    public String getComputerName() {
        return computerName;
    }

}
