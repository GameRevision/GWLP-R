
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P458_StatusText
    extends GenericAction
{

    private short show;
    private short hasCancel;
    private String text;

    public short getHeader() {
        return  458;
    }

    public void setShow(short show) {
        this.show = show;
    }

    public void setHasCancel(short hasCancel) {
        this.hasCancel = hasCancel;
    }

    public void setText(String text) {
        this.text = text;
    }

}
