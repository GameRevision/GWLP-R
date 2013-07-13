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
@Table(name = "items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i"),
    @NamedQuery(name = "Items.findById", query = "SELECT i FROM Items i WHERE i.id = :id"),
    @NamedQuery(name = "Items.findByQuantity", query = "SELECT i FROM Items i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "Items.findByFlags", query = "SELECT i FROM Items i WHERE i.flags = :flags"),
    @NamedQuery(name = "Items.findByDyeColor", query = "SELECT i FROM Items i WHERE i.dyeColor = :dyeColor")})
public class Items implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Quantity")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "Flags")
    private int flags;
    @Basic(optional = false)
    @Column(name = "DyeColor")
    private int dyeColor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "items")
    private Collection<Itemstats> itemstatsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<Storeditems> storeditemsCollection;
    @JoinColumn(name = "CustomizedFor", referencedColumnName = "ID")
    @ManyToOne
    private Characters customizedFor;
    @JoinColumn(name = "BaseID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Itembases baseID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offhand")
    private Collection<Weapons> weaponsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leadhand")
    private Collection<Weapons> weaponsCollection1;

    public Items() {
    }

    public Items(Integer id) {
        this.id = id;
    }

    public Items(Integer id, int quantity, int flags, int dyeColor) {
        this.id = id;
        this.quantity = quantity;
        this.flags = flags;
        this.dyeColor = dyeColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getDyeColor() {
        return dyeColor;
    }

    public void setDyeColor(int dyeColor) {
        this.dyeColor = dyeColor;
    }

    @XmlTransient
    public Collection<Itemstats> getItemstatsCollection() {
        return itemstatsCollection;
    }

    public void setItemstatsCollection(Collection<Itemstats> itemstatsCollection) {
        this.itemstatsCollection = itemstatsCollection;
    }

    @XmlTransient
    public Collection<Storeditems> getStoreditemsCollection() {
        return storeditemsCollection;
    }

    public void setStoreditemsCollection(Collection<Storeditems> storeditemsCollection) {
        this.storeditemsCollection = storeditemsCollection;
    }

    public Characters getCustomizedFor() {
        return customizedFor;
    }

    public void setCustomizedFor(Characters customizedFor) {
        this.customizedFor = customizedFor;
    }

    public Itembases getBaseID() {
        return baseID;
    }

    public void setBaseID(Itembases baseID) {
        this.baseID = baseID;
    }

    @XmlTransient
    public Collection<Weapons> getWeaponsCollection() {
        return weaponsCollection;
    }

    public void setWeaponsCollection(Collection<Weapons> weaponsCollection) {
        this.weaponsCollection = weaponsCollection;
    }

    @XmlTransient
    public Collection<Weapons> getWeaponsCollection1() {
        return weaponsCollection1;
    }

    public void setWeaponsCollection1(Collection<Weapons> weaponsCollection1) {
        this.weaponsCollection1 = weaponsCollection1;
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
        if (!(object instanceof Items)) {
            return false;
        }
        Items other = (Items) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Items[ id=" + id + " ]";
    }

}
