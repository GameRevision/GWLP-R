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
@Table(name = "inventories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventories.findAll", query = "SELECT i FROM Inventories i"),
    @NamedQuery(name = "Inventories.findById", query = "SELECT i FROM Inventories i WHERE i.id = :id"),
    @NamedQuery(name = "Inventories.findBySlots", query = "SELECT i FROM Inventories i WHERE i.slots = :slots")})
public class Inventories implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Slots")
    private int slots;
    @OneToMany(mappedBy = "materialStorage")
    private Collection<Accounts> accountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryID")
    private Collection<Storagetabs> storagetabsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventories")
    private Collection<Storeditems> storeditemsCollection;
    @OneToMany(mappedBy = "equipmentPack")
    private Collection<Characters> charactersCollection;
    @OneToMany(mappedBy = "equipment")
    private Collection<Characters> charactersCollection1;
    @OneToMany(mappedBy = "beltpouch")
    private Collection<Characters> charactersCollection2;
    @OneToMany(mappedBy = "bag2")
    private Collection<Characters> charactersCollection3;
    @OneToMany(mappedBy = "bag1")
    private Collection<Characters> charactersCollection4;
    @OneToMany(mappedBy = "backpack")
    private Collection<Characters> charactersCollection5;

    public Inventories() {
    }

    public Inventories(Integer id) {
        this.id = id;
    }

    public Inventories(Integer id, int slots) {
        this.id = id;
        this.slots = slots;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
    }

    @XmlTransient
    public Collection<Storagetabs> getStoragetabsCollection() {
        return storagetabsCollection;
    }

    public void setStoragetabsCollection(Collection<Storagetabs> storagetabsCollection) {
        this.storagetabsCollection = storagetabsCollection;
    }

    @XmlTransient
    public Collection<Storeditems> getStoreditemsCollection() {
        return storeditemsCollection;
    }

    public void setStoreditemsCollection(Collection<Storeditems> storeditemsCollection) {
        this.storeditemsCollection = storeditemsCollection;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection() {
        return charactersCollection;
    }

    public void setCharactersCollection(Collection<Characters> charactersCollection) {
        this.charactersCollection = charactersCollection;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection1() {
        return charactersCollection1;
    }

    public void setCharactersCollection1(Collection<Characters> charactersCollection1) {
        this.charactersCollection1 = charactersCollection1;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection2() {
        return charactersCollection2;
    }

    public void setCharactersCollection2(Collection<Characters> charactersCollection2) {
        this.charactersCollection2 = charactersCollection2;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection3() {
        return charactersCollection3;
    }

    public void setCharactersCollection3(Collection<Characters> charactersCollection3) {
        this.charactersCollection3 = charactersCollection3;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection4() {
        return charactersCollection4;
    }

    public void setCharactersCollection4(Collection<Characters> charactersCollection4) {
        this.charactersCollection4 = charactersCollection4;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection5() {
        return charactersCollection5;
    }

    public void setCharactersCollection5(Collection<Characters> charactersCollection5) {
        this.charactersCollection5 = charactersCollection5;
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
        if (!(object instanceof Inventories)) {
            return false;
        }
        Inventories other = (Inventories) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Inventories[ id=" + id + " ]";
    }

}
