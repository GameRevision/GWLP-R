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
@Table(name = "weaponsets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weaponset.findAll", query = "SELECT w FROM Weaponset w"),
    @NamedQuery(name = "Weaponset.findByCharacterID", query = "SELECT w FROM Weaponset w WHERE w.weaponsetPK.characterID = :characterID"),
    @NamedQuery(name = "Weaponset.findByNumber", query = "SELECT w FROM Weaponset w WHERE w.weaponsetPK.number = :number")})
public class Weaponset implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WeaponsetPK weaponsetPK;
    @JoinColumn(name = "Weapons", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Weapon weapons;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Character character;

    public Weaponset() {
    }

    public Weaponset(WeaponsetPK weaponsetPK) {
        this.weaponsetPK = weaponsetPK;
    }

    public Weaponset(int characterID, boolean number) {
        this.weaponsetPK = new WeaponsetPK(characterID, number);
    }

    public WeaponsetPK getWeaponsetPK() {
        return weaponsetPK;
    }

    public void setWeaponsetPK(WeaponsetPK weaponsetPK) {
        this.weaponsetPK = weaponsetPK;
    }

    public Weapon getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapon weapons) {
        this.weapons = weapons;
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
        hash += (weaponsetPK != null ? weaponsetPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weaponset)) {
            return false;
        }
        Weaponset other = (Weaponset) object;
        if ((this.weaponsetPK == null && other.weaponsetPK != null) || (this.weaponsetPK != null && !this.weaponsetPK.equals(other.weaponsetPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Weaponset[ weaponsetPK=" + weaponsetPK + " ]";
    }

}
