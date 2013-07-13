/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author _rusty
 */
@Embeddable 
public class StoreditemsPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "InventoryID")
    private int inventoryID;
    @Basic(optional = false)
    @Column(name = "Slot")
    private int slot;

    public StoreditemsPK() {
    }

    public StoreditemsPK(int inventoryID, int slot) {
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
        if (!(object instanceof StoreditemsPK)) {
            return false;
        }
        StoreditemsPK other = (StoreditemsPK) object;
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
        return "com.gamerevision.gwlpr.database.StoreditemsPK[ inventoryID=" + inventoryID + ", slot=" + slot + " ]";
    }

}
