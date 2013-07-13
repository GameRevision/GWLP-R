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
@Table(name = "maps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maps.findAll", query = "SELECT m FROM Maps m"),
    @NamedQuery(name = "Maps.findById", query = "SELECT m FROM Maps m WHERE m.id = :id"),
    @NamedQuery(name = "Maps.findByGameID", query = "SELECT m FROM Maps m WHERE m.gameID = :gameID"),
    @NamedQuery(name = "Maps.findByHash", query = "SELECT m FROM Maps m WHERE m.hash = :hash"),
    @NamedQuery(name = "Maps.findByName", query = "SELECT m FROM Maps m WHERE m.name = :name"),
    @NamedQuery(name = "Maps.findByPvP", query = "SELECT m FROM Maps m WHERE m.pvP = :pvP")})
public class Maps implements Serializable 
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
    private boolean pvP;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "maps")
    private Collection<Spawnpoints> spawnpointsCollection;
    @OneToMany(mappedBy = "lastOutpost")
    private Collection<Characters> charactersCollection;

    public Maps() {
    }

    public Maps(Integer id) {
        this.id = id;
    }

    public Maps(Integer id, int gameID, int hash, String name, boolean pvP) {
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

    public boolean getPvP() {
        return pvP;
    }

    public void setPvP(boolean pvP) {
        this.pvP = pvP;
    }

    @XmlTransient
    public Collection<Spawnpoints> getSpawnpointsCollection() {
        return spawnpointsCollection;
    }

    public void setSpawnpointsCollection(Collection<Spawnpoints> spawnpointsCollection) {
        this.spawnpointsCollection = spawnpointsCollection;
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
        if (!(object instanceof Maps)) {
            return false;
        }
        Maps other = (Maps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Maps[ id=" + id + " ]";
    }

}
