
package gwlpr.actions.gameserver.outbound;

import realityshard.shardlet.utils.GenericAction;


/**
 * Auto-generated by "Packet generator".
 * 
 * 
 */
public final class P309_CreateInventoryPage
    extends GenericAction
{

    private int itemStreamID;
    private short type;
    private short storage;
    private int pageID;
    private short slots;
    private long associatedItem;

    public short getHeader() {
        return  309;
    }

    public void setItemStreamID(int itemStreamID) {
        this.itemStreamID = itemStreamID;
    }

    public void setType(short type) {
        this.type = type;
    }

    public void setStorage(short storage) {
        this.storage = storage;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public void setSlots(short slots) {
        this.slots = slots;
    }

    public void setAssociatedItem(long associatedItem) {
        this.associatedItem = associatedItem;
    }

}
