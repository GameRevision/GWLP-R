
package gwlpr.actions.gameserver.outbound;

import gwlpr.actions.utils.IsArray;
import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P237_ItemPrices
    extends GenericAction
{

    @IsArray(constant = false, size = 16, prefixLength = 2)
    private long[] prices;

    public short getHeader() {
        return  237;
    }

    public void setPrices(long[] prices) {
        this.prices = prices;
    }

}
