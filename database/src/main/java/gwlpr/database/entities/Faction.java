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
@Table(name = "factions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faction.findAll", query = "SELECT f FROM Faction f"),
    @NamedQuery(name = "Faction.findById", query = "SELECT f FROM Faction f WHERE f.id = :id"),
    @NamedQuery(name = "Faction.findByName", query = "SELECT f FROM Faction f WHERE f.name = :name")})
public class Faction implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faction")
    private Collection<Factionstat> factionstatCollection;

    public Faction() {
    }

    public Faction(Integer id) {
        this.id = id;
    }

    public Faction(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Factionstat> getFactionstatCollection() {
        return factionstatCollection;
    }

    public void setFactionstatCollection(Collection<Factionstat> factionstatCollection) {
        this.factionstatCollection = factionstatCollection;
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
        if (!(object instanceof Faction)) {
            return false;
        }
        Faction other = (Faction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Faction[ id=" + id + " ]";
    }

}
