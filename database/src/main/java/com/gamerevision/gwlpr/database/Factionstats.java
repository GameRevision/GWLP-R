/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
    @NamedQuery(name = "Factionstats.findAll", query = "SELECT f FROM Factionstats f"),
    @NamedQuery(name = "Factionstats.findByAccountID", query = "SELECT f FROM Factionstats f WHERE f.factionstatsPK.accountID = :accountID"),
    @NamedQuery(name = "Factionstats.findByType", query = "SELECT f FROM Factionstats f WHERE f.factionstatsPK.type = :type"),
    @NamedQuery(name = "Factionstats.findByAmount", query = "SELECT f FROM Factionstats f WHERE f.amount = :amount"),
    @NamedQuery(name = "Factionstats.findByMax", query = "SELECT f FROM Factionstats f WHERE f.max = :max"),
    @NamedQuery(name = "Factionstats.findByTotal", query = "SELECT f FROM Factionstats f WHERE f.total = :total")})
public class Factionstats implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FactionstatsPK factionstatsPK;
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
    private Factions factions;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Accounts accounts;

    public Factionstats() {
    }

    public Factionstats(FactionstatsPK factionstatsPK) {
        this.factionstatsPK = factionstatsPK;
    }

    public Factionstats(FactionstatsPK factionstatsPK, int amount, int max, int total) {
        this.factionstatsPK = factionstatsPK;
        this.amount = amount;
        this.max = max;
        this.total = total;
    }

    public Factionstats(int accountID, int type) {
        this.factionstatsPK = new FactionstatsPK(accountID, type);
    }

    public FactionstatsPK getFactionstatsPK() {
        return factionstatsPK;
    }

    public void setFactionstatsPK(FactionstatsPK factionstatsPK) {
        this.factionstatsPK = factionstatsPK;
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

    public Factions getFactions() {
        return factions;
    }

    public void setFactions(Factions factions) {
        this.factions = factions;
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
        hash += (factionstatsPK != null ? factionstatsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factionstats)) {
            return false;
        }
        Factionstats other = (Factionstats) object;
        if ((this.factionstatsPK == null && other.factionstatsPK != null) || (this.factionstatsPK != null && !this.factionstatsPK.equals(other.factionstatsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Factionstats[ factionstatsPK=" + factionstatsPK + " ]";
    }

}
