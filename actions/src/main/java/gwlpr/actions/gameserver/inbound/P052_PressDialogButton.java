
package gwlpr.actions.gameserver.inbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P052_PressDialogButton
    extends GenericAction
{

    private long buttonID;

    public short getHeader() {
        return  52;
    }

    public long getButtonID() {
        return buttonID;
    }

}
