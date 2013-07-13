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
@Table(name = "weaponsets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weaponsets.findAll", query = "SELECT w FROM Weaponsets w"),
    @NamedQuery(name = "Weaponsets.findByCharacterID", query = "SELECT w FROM Weaponsets w WHERE w.weaponsetsPK.characterID = :characterID"),
    @NamedQuery(name = "Weaponsets.findByNumber", query = "SELECT w FROM Weaponsets w WHERE w.weaponsetsPK.number = :number")})
public class Weaponsets implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WeaponsetsPK weaponsetsPK;
    @JoinColumn(name = "Weapons", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Weapons weapons;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Characters characters;

    public Weaponsets() {
    }

    public Weaponsets(WeaponsetsPK weaponsetsPK) {
        this.weaponsetsPK = weaponsetsPK;
    }

    public Weaponsets(int characterID, boolean number) {
        this.weaponsetsPK = new WeaponsetsPK(characterID, number);
    }

    public WeaponsetsPK getWeaponsetsPK() {
        return weaponsetsPK;
    }

    public void setWeaponsetsPK(WeaponsetsPK weaponsetsPK) {
        this.weaponsetsPK = weaponsetsPK;
    }

    public Weapons getWeapons() {
        return weapons;
    }

    public void setWeapons(Weapons weapons) {
        this.weapons = weapons;
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
        hash += (weaponsetsPK != null ? weaponsetsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weaponsets)) {
            return false;
        }
        Weaponsets other = (Weaponsets) object;
        if ((this.weaponsetsPK == null && other.weaponsetsPK != null) || (this.weaponsetsPK != null && !this.weaponsetsPK.equals(other.weaponsetsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Weaponsets[ weaponsetsPK=" + weaponsetsPK + " ]";
    }

}
