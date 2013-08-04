/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

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
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id"),
    @NamedQuery(name = "Item.findByQuantity", query = "SELECT i FROM Item i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "Item.findByFlags", query = "SELECT i FROM Item i WHERE i.flags = :flags"),
    @NamedQuery(name = "Item.findByDyeColor", query = "SELECT i FROM Item i WHERE i.dyeColor = :dyeColor")})
public class Item implements Serializable 
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private Collection<Itemstat> itemstatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemID")
    private Collection<Storeditem> storeditemCollection;
    @JoinColumn(name = "CustomizedFor", referencedColumnName = "ID")
    @ManyToOne
    private Character customizedFor;
    @JoinColumn(name = "BaseID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Itembase baseID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offhand")
    private Collection<Weapon> weaponCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leadhand")
    private Collection<Weapon> weaponCollection1;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, int quantity, int flags, int dyeColor) {
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
    public Collection<Itemstat> getItemstatCollection() {
        return itemstatCollection;
    }

    public void setItemstatCollection(Collection<Itemstat> itemstatCollection) {
        this.itemstatCollection = itemstatCollection;
    }

    @XmlTransient
    public Collection<Storeditem> getStoreditemCollection() {
        return storeditemCollection;
    }

    public void setStoreditemCollection(Collection<Storeditem> storeditemCollection) {
        this.storeditemCollection = storeditemCollection;
    }

    public Character getCustomizedFor() {
        return customizedFor;
    }

    public void setCustomizedFor(Character customizedFor) {
        this.customizedFor = customizedFor;
    }

    public Itembase getBaseID() {
        return baseID;
    }

    public void setBaseID(Itembase baseID) {
        this.baseID = baseID;
    }

    @XmlTransient
    public Collection<Weapon> getWeaponCollection() {
        return weaponCollection;
    }

    public void setWeaponCollection(Collection<Weapon> weaponCollection) {
        this.weaponCollection = weaponCollection;
    }

    @XmlTransient
    public Collection<Weapon> getWeaponCollection1() {
        return weaponCollection1;
    }

    public void setWeaponCollection1(Collection<Weapon> weaponCollection1) {
        this.weaponCollection1 = weaponCollection1;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Item[ id=" + id + " ]";
    }

}
