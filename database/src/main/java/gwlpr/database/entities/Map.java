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
@Table(name = "maps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Map.findAll", query = "SELECT m FROM Map m"),
    @NamedQuery(name = "Map.findById", query = "SELECT m FROM Map m WHERE m.id = :id"),
    @NamedQuery(name = "Map.findByGameID", query = "SELECT m FROM Map m WHERE m.gameID = :gameID"),
    @NamedQuery(name = "Map.findByHash", query = "SELECT m FROM Map m WHERE m.hash = :hash"),
    @NamedQuery(name = "Map.findByName", query = "SELECT m FROM Map m WHERE m.name = :name"),
    @NamedQuery(name = "Map.findByPvP", query = "SELECT m FROM Map m WHERE m.pvP = :pvP")})
public class Map implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "GameID")
    private int gameID;
    @Basic(optional = false)
    @Column(name = "Hash")
    private int hash;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "PvP")
    private short pvP;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "map")
    private Collection<Spawnpoint> spawnpointCollection;
    @OneToMany(mappedBy = "lastOutpost")
    private Collection<Character> characterCollection;

    public Map() {
    }

    public Map(Integer id) {
        this.id = id;
    }

    public Map(Integer id, int gameID, int hash, String name, short pvP) {
        this.id = id;
        this.gameID = gameID;
        this.hash = hash;
        this.name = name;
        this.pvP = pvP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getPvP() {
        return pvP;
    }

    public void setPvP(short pvP) {
        this.pvP = pvP;
    }

    @XmlTransient
    public Collection<Spawnpoint> getSpawnpointCollection() {
        return spawnpointCollection;
    }

    public void setSpawnpointCollection(Collection<Spawnpoint> spawnpointCollection) {
        this.spawnpointCollection = spawnpointCollection;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection() {
        return characterCollection;
    }

    public void setCharacterCollection(Collection<Character> characterCollection) {
        this.characterCollection = characterCollection;
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
        if (!(object instanceof Map)) {
            return false;
        }
        Map other = (Map) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Map[ id=" + id + " ]";
    }

}
