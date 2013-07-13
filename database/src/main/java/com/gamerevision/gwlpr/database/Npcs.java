/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
    @NamedQuery(name = "Npcs.findAll", query = "SELECT n FROM Npcs n"),
    @NamedQuery(name = "Npcs.findById", query = "SELECT n FROM Npcs n WHERE n.id = :id"),
    @NamedQuery(name = "Npcs.findByName", query = "SELECT n FROM Npcs n WHERE n.name = :name"),
    @NamedQuery(name = "Npcs.findByModel", query = "SELECT n FROM Npcs n WHERE n.model = :model"),
    @NamedQuery(name = "Npcs.findByAllegiance", query = "SELECT n FROM Npcs n WHERE n.allegiance = :allegiance")})
public class Npcs implements Serializable 
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
    private Weapons weapons;

    public Npcs() {
    }

    public Npcs(Integer id) {
        this.id = id;
    }

    public Npcs(Integer id, String name, int model, int allegiance) {
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

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
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
        if (!(object instanceof Npcs)) {
            return false;
        }
        Npcs other = (Npcs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Npcs[ id=" + id + " ]";
    }

}
