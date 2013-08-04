/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

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
    @NamedQuery(name = "Storeditem.findAll", query = "SELECT s FROM Storeditem s"),
    @NamedQuery(name = "Storeditem.findByInventoryID", query = "SELECT s FROM Storeditem s WHERE s.storeditemPK.inventoryID = :inventoryID"),
    @NamedQuery(name = "Storeditem.findBySlot", query = "SELECT s FROM Storeditem s WHERE s.storeditemPK.slot = :slot")})
public class Storeditem implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StoreditemPK storeditemPK;
    @JoinColumn(name = "ItemID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Item itemID;
    @JoinColumn(name = "InventoryID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventory inventory;

    public Storeditem() {
    }

    public Storeditem(StoreditemPK storeditemPK) {
        this.storeditemPK = storeditemPK;
    }

    public Storeditem(int inventoryID, int slot) {
        this.storeditemPK = new StoreditemPK(inventoryID, slot);
    }

    public StoreditemPK getStoreditemPK() {
        return storeditemPK;
    }

    public void setStoreditemPK(StoreditemPK storeditemPK) {
        this.storeditemPK = storeditemPK;
    }

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeditemPK != null ? storeditemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storeditem)) {
            return false;
        }
        Storeditem other = (Storeditem) object;
        if ((this.storeditemPK == null && other.storeditemPK != null) || (this.storeditemPK != null && !this.storeditemPK.equals(other.storeditemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Storeditem[ storeditemPK=" + storeditemPK + " ]";
    }

}
