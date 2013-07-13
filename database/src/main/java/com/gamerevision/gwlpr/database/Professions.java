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
import javax.persistence.ManyToMany;
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
@Table(name = "professions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professions.findAll", query = "SELECT p FROM Professions p"),
    @NamedQuery(name = "Professions.findById", query = "SELECT p FROM Professions p WHERE p.id = :id"),
    @NamedQuery(name = "Professions.findByName", query = "SELECT p FROM Professions p WHERE p.name = :name")})
public class Professions implements Serializable 
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
    @ManyToMany(mappedBy = "professionsCollection")
    private Collection<Characters> charactersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profession")
    private Collection<Attributes> attributesCollection;
    @OneToMany(mappedBy = "secondaryProfession")
    private Collection<Characters> charactersCollection1;
    @OneToMany(mappedBy = "primaryProfession")
    private Collection<Characters> charactersCollection2;

    public Professions() {
    }

    public Professions(Integer id) {
        this.id = id;
    }

    public Professions(Integer id, String name) {
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
    public Collection<Characters> getCharactersCollection() {
        return charactersCollection;
    }

    public void setCharactersCollection(Collection<Characters> charactersCollection) {
        this.charactersCollection = charactersCollection;
    }

    @XmlTransient
    public Collection<Attributes> getAttributesCollection() {
        return attributesCollection;
    }

    public void setAttributesCollection(Collection<Attributes> attributesCollection) {
        this.attributesCollection = attributesCollection;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection1() {
        return charactersCollection1;
    }

    public void setCharactersCollection1(Collection<Characters> charactersCollection1) {
        this.charactersCollection1 = charactersCollection1;
    }

    @XmlTransient
    public Collection<Characters> getCharactersCollection2() {
        return charactersCollection2;
    }

    public void setCharactersCollection2(Collection<Characters> charactersCollection2) {
        this.charactersCollection2 = charactersCollection2;
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
        if (!(object instanceof Professions)) {
            return false;
        }
        Professions other = (Professions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Professions[ id=" + id + " ]";
    }

}
