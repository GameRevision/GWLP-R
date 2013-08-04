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
@Table(name = "itembases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itembase.findAll", query = "SELECT i FROM Itembase i"),
    @NamedQuery(name = "Itembase.findById", query = "SELECT i FROM Itembase i WHERE i.id = :id"),
    @NamedQuery(name = "Itembase.findByFileID", query = "SELECT i FROM Itembase i WHERE i.fileID = :fileID"),
    @NamedQuery(name = "Itembase.findByName", query = "SELECT i FROM Itembase i WHERE i.name = :name"),
    @NamedQuery(name = "Itembase.findByType", query = "SELECT i FROM Itembase i WHERE i.type = :type")})
public class Itembase implements Serializable 
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
    private Collection<Item> itemCollection;

    public Itembase() {
    }

    public Itembase(Integer id) {
        this.id = id;
    }

    public Itembase(Integer id, int fileID, String name, boolean type) {
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
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
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
        if (!(object instanceof Itembase)) {
            return false;
        }
        Itembase other = (Itembase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Itembase[ id=" + id + " ]";
    }

}
