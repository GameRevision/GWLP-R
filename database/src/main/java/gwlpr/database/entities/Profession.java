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
    @NamedQuery(name = "Profession.findAll", query = "SELECT p FROM Profession p"),
    @NamedQuery(name = "Profession.findById", query = "SELECT p FROM Profession p WHERE p.id = :id"),
    @NamedQuery(name = "Profession.findByName", query = "SELECT p FROM Profession p WHERE p.name = :name")})
public class Profession implements Serializable 
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
    @ManyToMany(mappedBy = "professionCollection")
    private Collection<Character> characterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profession")
    private Collection<Attribute> attributeCollection;
    @OneToMany(mappedBy = "secondaryProfession")
    private Collection<Character> characterCollection1;
    @OneToMany(mappedBy = "primaryProfession")
    private Collection<Character> characterCollection2;

    public Profession() {
    }

    public Profession(Integer id) {
        this.id = id;
    }

    public Profession(Integer id, String name) {
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
    public Collection<Character> getCharacterCollection() {
        return characterCollection;
    }

    public void setCharacterCollection(Collection<Character> characterCollection) {
        this.characterCollection = characterCollection;
    }

    @XmlTransient
    public Collection<Attribute> getAttributeCollection() {
        return attributeCollection;
    }

    public void setAttributeCollection(Collection<Attribute> attributeCollection) {
        this.attributeCollection = attributeCollection;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection1() {
        return characterCollection1;
    }

    public void setCharacterCollection1(Collection<Character> characterCollection1) {
        this.characterCollection1 = characterCollection1;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection2() {
        return characterCollection2;
    }

    public void setCharacterCollection2(Collection<Character> characterCollection2) {
        this.characterCollection2 = characterCollection2;
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
        if (!(object instanceof Profession)) {
            return false;
        }
        Profession other = (Profession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Profession[ id=" + id + " ]";
    }

}
