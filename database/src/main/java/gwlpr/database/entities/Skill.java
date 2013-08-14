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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "skills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skill.findAll", query = "SELECT s FROM Skill s"),
    @NamedQuery(name = "Skill.findById", query = "SELECT s FROM Skill s WHERE s.id = :id"),
    @NamedQuery(name = "Skill.findByName", query = "SELECT s FROM Skill s WHERE s.name = :name")})
public class Skill implements Serializable 
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
    @ManyToMany(mappedBy = "skillCollection")
    private Collection<Character> characterCollection;
    @JoinColumn(name = "Attribute", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Attribute attribute;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skill")
    private Collection<EquippedSkill> equippedSkillCollection;

    public Skill() {
    }

    public Skill(Integer id) {
        this.id = id;
    }

    public Skill(Integer id, String name) {
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

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @XmlTransient
    public Collection<EquippedSkill> getEquippedSkillCollection() {
        return equippedSkillCollection;
    }

    public void setEquippedSkillCollection(Collection<EquippedSkill> equippedSkillCollection) {
        this.equippedSkillCollection = equippedSkillCollection;
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
        if (!(object instanceof Skill)) {
            return false;
        }
        Skill other = (Skill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Skill[ id=" + id + " ]";
    }

}
