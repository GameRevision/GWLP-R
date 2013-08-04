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
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findById", query = "SELECT i FROM Inventory i WHERE i.id = :id"),
    @NamedQuery(name = "Inventory.findBySlots", query = "SELECT i FROM Inventory i WHERE i.slots = :slots")})
public class Inventory implements Serializable 
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
    private Collection<Account> accountCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryID")
    private Collection<Storagetab> storagetabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private Collection<Storeditem> storeditemCollection;
    @OneToMany(mappedBy = "equipmentPack")
    private Collection<Character> characterCollection;
    @OneToMany(mappedBy = "equipment")
    private Collection<Character> characterCollection1;
    @OneToMany(mappedBy = "beltpouch")
    private Collection<Character> characterCollection2;
    @OneToMany(mappedBy = "bag2")
    private Collection<Character> characterCollection3;
    @OneToMany(mappedBy = "bag1")
    private Collection<Character> characterCollection4;
    @OneToMany(mappedBy = "backpack")
    private Collection<Character> characterCollection5;

    public Inventory() {
    }

    public Inventory(Integer id) {
        this.id = id;
    }

    public Inventory(Integer id, int slots) {
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
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    @XmlTransient
    public Collection<Storagetab> getStoragetabCollection() {
        return storagetabCollection;
    }

    public void setStoragetabCollection(Collection<Storagetab> storagetabCollection) {
        this.storagetabCollection = storagetabCollection;
    }

    @XmlTransient
    public Collection<Storeditem> getStoreditemCollection() {
        return storeditemCollection;
    }

    public void setStoreditemCollection(Collection<Storeditem> storeditemCollection) {
        this.storeditemCollection = storeditemCollection;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection() {
        return characterCollection;
    }

    public void setCharacterCollection(Collection<Character> characterCollection) {
        this.characterCollection = characterCollection;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection1() {
        return characterCollection1;
    }

    public void setCharacterCollection1(Collection<Character> characterCollection1) {
        this.characterCollection1 = characterCollection1;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection2() {
        return characterCollection2;
    }

    public void setCharacterCollection2(Collection<Character> characterCollection2) {
        this.characterCollection2 = characterCollection2;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection3() {
        return characterCollection3;
    }

    public void setCharacterCollection3(Collection<Character> characterCollection3) {
        this.characterCollection3 = characterCollection3;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection4() {
        return characterCollection4;
    }

    public void setCharacterCollection4(Collection<Character> characterCollection4) {
        this.characterCollection4 = characterCollection4;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection5() {
        return characterCollection5;
    }

    public void setCharacterCollection5(Collection<Character> characterCollection5) {
        this.characterCollection5 = characterCollection5;
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
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Inventory[ id=" + id + " ]";
    }

}
