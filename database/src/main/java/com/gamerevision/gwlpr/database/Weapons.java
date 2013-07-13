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
@Table(name = "weapons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weapons.findAll", query = "SELECT w FROM Weapons w"),
    @NamedQuery(name = "Weapons.findById", query = "SELECT w FROM Weapons w WHERE w.id = :id")})
public class Weapons implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weapons")
    private Collection<Npcs> npcsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weapons")
    private Collection<Weaponsets> weaponsetsCollection;
    @JoinColumn(name = "Offhand", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Items offhand;
    @JoinColumn(name = "Leadhand", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Items leadhand;

    public Weapons() {
    }

    public Weapons(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<Npcs> getNpcsCollection() {
        return npcsCollection;
    }

    public void setNpcsCollection(Collection<Npcs> npcsCollection) {
        this.npcsCollection = npcsCollection;
    }

    @XmlTransient
    public Collection<Weaponsets> getWeaponsetsCollection() {
        return weaponsetsCollection;
    }

    public void setWeaponsetsCollection(Collection<Weaponsets> weaponsetsCollection) {
        this.weaponsetsCollection = weaponsetsCollection;
    }

    public Items getOffhand() {
        return offhand;
    }

    public void setOffhand(Items offhand) {
        this.offhand = offhand;
    }

    public Items getLeadhand() {
        return leadhand;
    }

    public void setLeadhand(Items leadhand) {
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
        if (!(object instanceof Weapons)) {
            return false;
        }
        Weapons other = (Weapons) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Weapons[ id=" + id + " ]";
    }

}
