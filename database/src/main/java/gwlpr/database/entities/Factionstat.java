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
@Table(name = "factionstats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factionstat.findAll", query = "SELECT f FROM Factionstat f"),
    @NamedQuery(name = "Factionstat.findByAccountID", query = "SELECT f FROM Factionstat f WHERE f.factionstatPK.accountID = :accountID"),
    @NamedQuery(name = "Factionstat.findByType", query = "SELECT f FROM Factionstat f WHERE f.factionstatPK.type = :type"),
    @NamedQuery(name = "Factionstat.findByAmount", query = "SELECT f FROM Factionstat f WHERE f.amount = :amount"),
    @NamedQuery(name = "Factionstat.findByMax", query = "SELECT f FROM Factionstat f WHERE f.max = :max"),
    @NamedQuery(name = "Factionstat.findByTotal", query = "SELECT f FROM Factionstat f WHERE f.total = :total")})
public class Factionstat implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FactionstatPK factionstatPK;
    @Basic(optional = false)
    @Column(name = "Amount")
    private int amount;
    @Basic(optional = false)
    @Column(name = "Max")
    private int max;
    @Basic(optional = false)
    @Column(name = "Total")
    private int total;
    @JoinColumn(name = "Type", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Faction faction;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;

    public Factionstat() {
    }

    public Factionstat(FactionstatPK factionstatPK) {
        this.factionstatPK = factionstatPK;
    }

    public Factionstat(FactionstatPK factionstatPK, int amount, int max, int total) {
        this.factionstatPK = factionstatPK;
        this.amount = amount;
        this.max = max;
        this.total = total;
    }

    public Factionstat(int accountID, int type) {
        this.factionstatPK = new FactionstatPK(accountID, type);
    }

    public FactionstatPK getFactionstatPK() {
        return factionstatPK;
    }

    public void setFactionstatPK(FactionstatPK factionstatPK) {
        this.factionstatPK = factionstatPK;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
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
        hash += (factionstatPK != null ? factionstatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factionstat)) {
            return false;
        }
        Factionstat other = (Factionstat) object;
        if ((this.factionstatPK == null && other.factionstatPK != null) || (this.factionstatPK != null && !this.factionstatPK.equals(other.factionstatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Factionstat[ factionstatPK=" + factionstatPK + " ]";
    }

}
