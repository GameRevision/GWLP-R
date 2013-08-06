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
@Table(name = "weapons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weapon.findAll", query = "SELECT w FROM Weapon w"),
    @NamedQuery(name = "Weapon.findById", query = "SELECT w FROM Weapon w WHERE w.id = :id")})
public class Weapon implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weapons")
    private Collection<Npc> npcCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weapons")
    private Collection<Weaponset> weaponsetCollection;
    @JoinColumn(name = "Offhand", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Item offhand;
    @JoinColumn(name = "Leadhand", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Item leadhand;

    public Weapon() {
    }

    public Weapon(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<Npc> getNpcCollection() {
        return npcCollection;
    }

    public void setNpcCollection(Collection<Npc> npcCollection) {
        this.npcCollection = npcCollection;
    }

    @XmlTransient
    public Collection<Weaponset> getWeaponsetCollection() {
        return weaponsetCollection;
    }

    public void setWeaponsetCollection(Collection<Weaponset> weaponsetCollection) {
        this.weaponsetCollection = weaponsetCollection;
    }

    public Item getOffhand() {
        return offhand;
    }

    public void setOffhand(Item offhand) {
        this.offhand = offhand;
    }

    public Item getLeadhand() {
        return leadhand;
    }

    public void setLeadhand(Item leadhand) {
        this.leadhand = leadhand;
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
        if (!(object instanceof Weapon)) {
            return false;
        }
        Weapon other = (Weapon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Weapon[ id=" + id + " ]";
    }

}
