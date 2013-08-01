
package gwlpr.actions.loginserver.outbound;

import gwlpr.actions.GWAction;
import gwlpr.actions.loginserver.LoginServerActionFactory;
import gwlpr.actions.utils.IsArray;


/**
 * Auto-generated by PacketCodeGen.
 * 
 */
public final class P009_ReferToGameServer
    extends GWAction
{

    public long loginCount;
    public long securityKey1;
    public long gameMapID;
    @IsArray(constant = true, size = 24, prefixLength = -1)
    public byte[] serverConnectionInfo;
    public long securityKey2;

    static {
        LoginServerActionFactory.registerOutbound(P009_ReferToGameServer.class);
    }

    @Override
    public short getHeader() {
        return  9;
    }

}
