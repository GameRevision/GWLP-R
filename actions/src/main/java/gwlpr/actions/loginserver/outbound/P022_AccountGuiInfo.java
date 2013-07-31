
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P022_AccountGuiInfo
    extends GenericAction
{

    private long loginCount;
    @IsArray(constant = false, size = 1024, prefixLength = 1)
    private short[] settings;

    public short getHeader() {
        return  22;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    public void setSettings(short[] settings) {
        this.settings = settings;
    }

}
