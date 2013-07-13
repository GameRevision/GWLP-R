/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
    @NamedQuery(name = "Skillsequipped.findAll", query = "SELECT s FROM Skillsequipped s"),
    @NamedQuery(name = "Skillsequipped.findByCharacterID", query = "SELECT s FROM Skillsequipped s WHERE s.skillsequippedPK.characterID = :characterID"),
    @NamedQuery(name = "Skillsequipped.findBySkillID", query = "SELECT s FROM Skillsequipped s WHERE s.skillsequippedPK.skillID = :skillID"),
    @NamedQuery(name = "Skillsequipped.findBySlot", query = "SELECT s FROM Skillsequipped s WHERE s.skillsequippedPK.slot = :slot")})
public class Skillsequipped implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SkillsequippedPK skillsequippedPK;
    @JoinColumn(name = "SkillID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Skills skills;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Characters characters;

    public Skillsequipped() {
    }

    public Skillsequipped(SkillsequippedPK skillsequippedPK) {
        this.skillsequippedPK = skillsequippedPK;
    }

    public Skillsequipped(int characterID, int skillID, int slot) {
        this.skillsequippedPK = new SkillsequippedPK(characterID, skillID, slot);
    }

    public SkillsequippedPK getSkillsequippedPK() {
        return skillsequippedPK;
    }

    public void setSkillsequippedPK(SkillsequippedPK skillsequippedPK) {
        this.skillsequippedPK = skillsequippedPK;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skillsequippedPK != null ? skillsequippedPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Skillsequipped)) {
            return false;
        }
        Skillsequipped other = (Skillsequipped) object;
        if ((this.skillsequippedPK == null && other.skillsequippedPK != null) || (this.skillsequippedPK != null && !this.skillsequippedPK.equals(other.skillsequippedPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Skillsequipped[ skillsequippedPK=" + skillsequippedPK + " ]";
    }

}
