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
@Table(name = "attributes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attributes.findAll", query = "SELECT a FROM Attributes a"),
    @NamedQuery(name = "Attributes.findById", query = "SELECT a FROM Attributes a WHERE a.id = :id"),
    @NamedQuery(name = "Attributes.findByName", query = "SELECT a FROM Attributes a WHERE a.name = :name")})
public class Attributes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attribute")
    private Collection<Skills> skillsCollection;
    @JoinColumn(name = "Profession", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Professions profession;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attributes")
    private Collection<Attributepoints> attributepointsCollection;

    public Attributes() {
    }

    public Attributes(Integer id) {
        this.id = id;
    }

    public Attributes(Integer id, String name) {
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
    public Collection<Skills> getSkillsCollection() {
        return skillsCollection;
    }

    public void setSkillsCollection(Collection<Skills> skillsCollection) {
        this.skillsCollection = skillsCollection;
    }

    public Professions getProfession() {
        return profession;
    }

    public void setProfession(Professions profession) {
        this.profession = profession;
    }

    @XmlTransient
    public Collection<Attributepoints> getAttributepointsCollection() {
        return attributepointsCollection;
    }

    public void setAttributepointsCollection(Collection<Attributepoints> attributepointsCollection) {
        this.attributepointsCollection = attributepointsCollection;
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
        if (!(object instanceof Attributes)) {
            return false;
        }
        Attributes other = (Attributes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Attributes[ id=" + id + " ]";
    }

}
