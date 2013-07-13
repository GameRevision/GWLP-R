/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author _rusty
 */
@Entity 
@Table(name = "storeditems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storeditems.findAll", query = "SELECT s FROM Storeditems s"),
    @NamedQuery(name = "Storeditems.findByInventoryID", query = "SELECT s FROM Storeditems s WHERE s.storeditemsPK.inventoryID = :inventoryID"),
    @NamedQuery(name = "Storeditems.findBySlot", query = "SELECT s FROM Storeditems s WHERE s.storeditemsPK.slot = :slot")})
public class Storeditems implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StoreditemsPK storeditemsPK;
    @JoinColumn(name = "ItemID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Items itemID;
    @JoinColumn(name = "InventoryID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventories inventories;

    public Storeditems() {
    }

    public Storeditems(StoreditemsPK storeditemsPK) {
        this.storeditemsPK = storeditemsPK;
    }

    public Storeditems(int inventoryID, int slot) {
        this.storeditemsPK = new StoreditemsPK(inventoryID, slot);
    }

    public StoreditemsPK getStoreditemsPK() {
        return storeditemsPK;
    }

    public void setStoreditemsPK(StoreditemsPK storeditemsPK) {
        this.storeditemsPK = storeditemsPK;
    }

    public Items getItemID() {
        return itemID;
    }

    public void setItemID(Items itemID) {
        this.itemID = itemID;
    }

    public Inventories getInventories() {
        return inventories;
    }

    public void setInventories(Inventories inventories) {
        this.inventories = inventories;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeditemsPK != null ? storeditemsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storeditems)) {
            return false;
        }
        Storeditems other = (Storeditems) object;
        if ((this.storeditemsPK == null && other.storeditemsPK != null) || (this.storeditemsPK != null && !this.storeditemsPK.equals(other.storeditemsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Storeditems[ storeditemsPK=" + storeditemsPK + " ]";
    }

}
