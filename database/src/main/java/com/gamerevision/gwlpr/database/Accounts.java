/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author _rusty
 */
@Entity 
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findById", query = "SELECT a FROM Accounts a WHERE a.id = :id"),
    @NamedQuery(name = "Accounts.findByEMail", query = "SELECT a FROM Accounts a WHERE a.eMail = :eMail"),
    @NamedQuery(name = "Accounts.findByPassword", query = "SELECT a FROM Accounts a WHERE a.password = :password"),
    @NamedQuery(name = "Accounts.findByGoldStorage", query = "SELECT a FROM Accounts a WHERE a.goldStorage = :goldStorage")})
public class Accounts implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "EMail")
    private String eMail;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Lob
    @Column(name = "GUI")
    private byte[] gui;
    @Basic(optional = false)
    @Column(name = "GoldStorage")
    private int goldStorage;
    @JoinColumn(name = "MaterialStorage", referencedColumnName = "ID")
    @ManyToOne
    private Inventories materialStorage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accounts")
    private Collection<Storagetabs> storagetabsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accounts")
    private Collection<Factionstats> factionstatsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountID")
    private Collection<Characters> charactersCollection;

    public Accounts() {
    }

    public Accounts(Integer id) {
        this.id = id;
    }

    public Accounts(Integer id, String eMail, String password, byte[] gui, int goldStorage) {
        this.id = id;
        this.eMail = eMail;
        this.password = password;
        this.gui = gui;
        this.goldStorage = goldStorage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getGui() {
        return gui;
    }

    public void setGui(byte[] gui) {
        this.gui = gui;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }

    public Inventories getMaterialStorage() {
        return materialStorage;
    }

    public void setMaterialStorage(Inventories materialStorage) {
        this.materialStorage = materialStorage;
    }

    @XmlTransient
    public Collection<Storagetabs> getStoragetabsCollection() {
        return storagetabsCollection;
    }

    public void setStoragetabsCollection(Collection<Storagetabs> storagetabsCollection) {
        this.storagetabsCollection = storagetabsCollection;
    }

    @XmlTransient
    public Collection<Factionstats> getFactionstatsCollection() {
        return factionstatsCollection;
    }

    public void setFactionstatsCollection(Collection<Factionstats> factionstatsCollection) {
        this.factionstatsCollection = factionstatsCollection;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection() {
        return charactersCollection;
    }

    public void setCharactersCollection(Collection<Characters> charactersCollection) {
        this.charactersCollection = charactersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Accounts[ id=" + id + " ]";
    }

}
