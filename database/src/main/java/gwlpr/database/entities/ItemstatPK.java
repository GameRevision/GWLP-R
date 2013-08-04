/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author _rusty
 */
@Embeddable 
public class ItemstatPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "ItemID")
    private int itemID;
    @Basic(optional = false)
    @Column(name = "StatID")
    private int statID;

    public ItemstatPK() {
    }

    public ItemstatPK(int itemID, int statID) {
        this.itemID = itemID;
        this.statID = statID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getStatID() {
        return statID;
    }

    public void setStatID(int statID) {
        this.statID = statID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) itemID;
        hash += (int) statID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemstatPK)) {
            return false;
        }
        ItemstatPK other = (ItemstatPK) object;
        if (this.itemID != other.itemID) {
            return false;
        }
        if (this.statID != other.statID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.ItemstatPK[ itemID=" + itemID + ", statID=" + statID + " ]";
    }

}
