/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "skillsequipped")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquippedSkill.findAll", query = "SELECT e FROM EquippedSkill e"),
    @NamedQuery(name = "EquippedSkill.findByCharacterID", query = "SELECT e FROM EquippedSkill e WHERE e.equippedSkillPK.characterID = :characterID"),
    @NamedQuery(name = "EquippedSkill.findBySkillID", query = "SELECT e FROM EquippedSkill e WHERE e.equippedSkillPK.skillID = :skillID"),
    @NamedQuery(name = "EquippedSkill.findBySlot", query = "SELECT e FROM EquippedSkill e WHERE e.equippedSkillPK.slot = :slot")})
public class EquippedSkill implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EquippedSkillPK equippedSkillPK;
    @JoinColumn(name = "SkillID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Skill skill;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Character character;

    public EquippedSkill() {
    }

    public EquippedSkill(EquippedSkillPK equippedSkillPK) {
        this.equippedSkillPK = equippedSkillPK;
    }

    public EquippedSkill(int characterID, int skillID, int slot) {
        this.equippedSkillPK = new EquippedSkillPK(characterID, skillID, slot);
    }

    public EquippedSkillPK getEquippedSkillPK() {
        return equippedSkillPK;
    }

    public void setEquippedSkillPK(EquippedSkillPK equippedSkillPK) {
        this.equippedSkillPK = equippedSkillPK;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (equippedSkillPK != null ? equippedSkillPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquippedSkill)) {
            return false;
        }
        EquippedSkill other = (EquippedSkill) object;
        if ((this.equippedSkillPK == null && other.equippedSkillPK != null) || (this.equippedSkillPK != null && !this.equippedSkillPK.equals(other.equippedSkillPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.EquippedSkill[ equippedSkillPK=" + equippedSkillPK + " ]";
    }

}
