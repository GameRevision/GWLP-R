/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "npcs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Npc.findAll", query = "SELECT n FROM Npc n"),
    @NamedQuery(name = "Npc.findById", query = "SELECT n FROM Npc n WHERE n.id = :id"),
    @NamedQuery(name = "Npc.findByName", query = "SELECT n FROM Npc n WHERE n.name = :name"),
    @NamedQuery(name = "Npc.findByModel", query = "SELECT n FROM Npc n WHERE n.model = :model"),
    @NamedQuery(name = "Npc.findByAllegiance", query = "SELECT n FROM Npc n WHERE n.allegiance = :allegiance")})
public class Npc implements Serializable 
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
    @Basic(optional = false)
    @Column(name = "Model")
    private int model;
    @Basic(optional = false)
    @Column(name = "Allegiance")
    private int allegiance;
    @JoinColumn(name = "Weapons", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Weapon weapons;

    public Npc() {
    }

    public Npc(Integer id) {
        this.id = id;
    }

    public Npc(Integer id, String name, int model, int allegiance) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.allegiance = allegiance;
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

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getAllegiance() {
        return allegiance;
    }

    public void setAllegiance(int allegiance) {
        this.allegiance = allegiance;
    }

    public Weapon getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapon weapons) {
        this.weapons = weapons;
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
        if (!(object instanceof Npc)) {
            return false;
        }
        Npc other = (Npc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Npc[ id=" + id + " ]";
    }

}
