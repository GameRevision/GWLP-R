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
public class StoreditemPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "InventoryID")
    private int inventoryID;
    @Basic(optional = false)
    @Column(name = "Slot")
    private int slot;

    public StoreditemPK() {
    }

    public StoreditemPK(int inventoryID, int slot) {
        this.inventoryID = inventoryID;
        this.slot = slot;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) inventoryID;
        hash += (int) slot;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoreditemPK)) {
            return false;
        }
        StoreditemPK other = (StoreditemPK) object;
        if (this.inventoryID != other.inventoryID) {
            return false;
        }
        if (this.slot != other.slot) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.StoreditemPK[ inventoryID=" + inventoryID + ", slot=" + slot + " ]";
    }

}
