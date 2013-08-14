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
@Table(name = "storagetabs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storagetab.findAll", query = "SELECT s FROM Storagetab s"),
    @NamedQuery(name = "Storagetab.findByAccountID", query = "SELECT s FROM Storagetab s WHERE s.storagetabPK.accountID = :accountID"),
    @NamedQuery(name = "Storagetab.findByNumber", query = "SELECT s FROM Storagetab s WHERE s.storagetabPK.number = :number")})
public class Storagetab implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StoragetabPK storagetabPK;
    @JoinColumn(name = "InventoryID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Inventory inventoryID;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;

    public Storagetab() {
    }

    public Storagetab(StoragetabPK storagetabPK) {
        this.storagetabPK = storagetabPK;
    }

    public Storagetab(int accountID, int number) {
        this.storagetabPK = new StoragetabPK(accountID, number);
    }

    public StoragetabPK getStoragetabPK() {
        return storagetabPK;
    }

    public void setStoragetabPK(StoragetabPK storagetabPK) {
        this.storagetabPK = storagetabPK;
    }

    public Inventory getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Inventory inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storagetabPK != null ? storagetabPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Storagetab)) {
            return false;
        }
        Storagetab other = (Storagetab) object;
        if ((this.storagetabPK == null && other.storagetabPK != null) || (this.storagetabPK != null && !this.storagetabPK.equals(other.storagetabPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Storagetab[ storagetabPK=" + storagetabPK + " ]";
    }

}
