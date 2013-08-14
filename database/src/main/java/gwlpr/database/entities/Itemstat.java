/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "itemstats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemstat.findAll", query = "SELECT i FROM Itemstat i"),
    @NamedQuery(name = "Itemstat.findByItemID", query = "SELECT i FROM Itemstat i WHERE i.itemstatPK.itemID = :itemID"),
    @NamedQuery(name = "Itemstat.findByStatID", query = "SELECT i FROM Itemstat i WHERE i.itemstatPK.statID = :statID"),
    @NamedQuery(name = "Itemstat.findByParameter1", query = "SELECT i FROM Itemstat i WHERE i.parameter1 = :parameter1"),
    @NamedQuery(name = "Itemstat.findByParameter2", query = "SELECT i FROM Itemstat i WHERE i.parameter2 = :parameter2")})
public class Itemstat implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemstatPK itemstatPK;
    @Basic(optional = false)
    @Column(name = "Parameter1")
    private int parameter1;
    @Basic(optional = false)
    @Column(name = "Parameter2")
    private int parameter2;
    @JoinColumn(name = "ItemID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Item item;

    public Itemstat() {
    }

    public Itemstat(ItemstatPK itemstatPK) {
        this.itemstatPK = itemstatPK;
    }

    public Itemstat(ItemstatPK itemstatPK, int parameter1, int parameter2) {
        this.itemstatPK = itemstatPK;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

    public Itemstat(int itemID, int statID) {
        this.itemstatPK = new ItemstatPK(itemID, statID);
    }

    public ItemstatPK getItemstatPK() {
        return itemstatPK;
    }

    public void setItemstatPK(ItemstatPK itemstatPK) {
        this.itemstatPK = itemstatPK;
    }

    public int getParameter1() {
        return parameter1;
    }

    public void setParameter1(int parameter1) {
        this.parameter1 = parameter1;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemstatPK != null ? itemstatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemstat)) {
            return false;
        }
        Itemstat other = (Itemstat) object;
        if ((this.itemstatPK == null && other.itemstatPK != null) || (this.itemstatPK != null && !this.itemstatPK.equals(other.itemstatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Itemstat[ itemstatPK=" + itemstatPK + " ]";
    }

}
