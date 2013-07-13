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
@Table(name = "storagetabs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storagetabs.findAll", query = "SELECT s FROM Storagetabs s"),
    @NamedQuery(name = "Storagetabs.findByAccountID", query = "SELECT s FROM Storagetabs s WHERE s.storagetabsPK.accountID = :accountID"),
    @NamedQuery(name = "Storagetabs.findByNumber", query = "SELECT s FROM Storagetabs s WHERE s.storagetabsPK.number = :number")})
public class Storagetabs implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StoragetabsPK storagetabsPK;
    @JoinColumn(name = "InventoryID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Inventories inventoryID;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Accounts accounts;

    public Storagetabs() {
    }

    public Storagetabs(StoragetabsPK storagetabsPK) {
        this.storagetabsPK = storagetabsPK;
    }

    public Storagetabs(int accountID, int number) {
        this.storagetabsPK = new StoragetabsPK(accountID, number);
    }

    public StoragetabsPK getStoragetabsPK() {
        return storagetabsPK;
    }

    public void setStoragetabsPK(StoragetabsPK storagetabsPK) {
        this.storagetabsPK = storagetabsPK;
    }

    public Inventories getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Inventories inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storagetabsPK != null ? storagetabsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetabs)) {
            return false;
        }
        Storagetabs other = (Storagetabs) object;
        if ((this.storagetabsPK == null && other.storagetabsPK != null) || (this.storagetabsPK != null && !this.storagetabsPK.equals(other.storagetabsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Storagetabs[ storagetabsPK=" + storagetabsPK + " ]";
    }

}
