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
@Table(name = "itembases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itembases.findAll", query = "SELECT i FROM Itembases i"),
    @NamedQuery(name = "Itembases.findById", query = "SELECT i FROM Itembases i WHERE i.id = :id"),
    @NamedQuery(name = "Itembases.findByFileID", query = "SELECT i FROM Itembases i WHERE i.fileID = :fileID"),
    @NamedQuery(name = "Itembases.findByName", query = "SELECT i FROM Itembases i WHERE i.name = :name"),
    @NamedQuery(name = "Itembases.findByType", query = "SELECT i FROM Itembases i WHERE i.type = :type")})
public class Itembases implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FileID")
    private int fileID;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Type")
    private boolean type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baseID")
    private Collection<Items> itemsCollection;

    public Itembases() {
    }

    public Itembases(Integer id) {
        this.id = id;
    }

    public Itembases(Integer id, int fileID, String name, boolean type) {
        this.id = id;
        this.fileID = fileID;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Items> getItemsCollection() {
        return itemsCollection;
    }

    public void setItemsCollection(Collection<Items> itemsCollection) {
        this.itemsCollection = itemsCollection;
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
        if (!(object instanceof Itembases)) {
            return false;
        }
        Itembases other = (Itembases) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Itembases[ id=" + id + " ]";
    }

}
