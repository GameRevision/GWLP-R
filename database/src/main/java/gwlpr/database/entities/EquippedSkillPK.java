/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author _rusty
 */
@Embeddable 
public class EquippedSkillPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "CharacterID")
    private int characterID;
    @Basic(optional = false)
    @Column(name = "SkillID")
    private int skillID;
    @Basic(optional = false)
    @Column(name = "Slot")
    private int slot;

    public EquippedSkillPK() {
    }

    public EquippedSkillPK(int characterID, int skillID, int slot) {
        this.characterID = characterID;
        this.skillID = skillID;
        this.slot = slot;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) characterID;
        hash += (int) skillID;
        hash += (int) slot;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EquippedSkillPK)) {
            return false;
        }
        EquippedSkillPK other = (EquippedSkillPK) object;
        if (this.characterID != other.characterID) {
            return false;
        }
        if (this.skillID != other.skillID) {
            return false;
        }
        if (this.slot != other.slot) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.EquippedSkillPK[ characterID=" + characterID + ", skillID=" + skillID + ", slot=" + slot + " ]";
    }

}
