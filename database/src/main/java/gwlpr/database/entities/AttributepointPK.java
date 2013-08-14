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
public class AttributepointPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "CharacterID")
    private int characterID;
    @Basic(optional = false)
    @Column(name = "AttributeID")
    private int attributeID;

    public AttributepointPK() {
    }

    public AttributepointPK(int characterID, int attributeID) {
        this.characterID = characterID;
        this.attributeID = attributeID;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(int attributeID) {
        this.attributeID = attributeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) characterID;
        hash += (int) attributeID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributepointPK)) {
            return false;
        }
        AttributepointPK other = (AttributepointPK) object;
        if (this.characterID != other.characterID) {
            return false;
        }
        if (this.attributeID != other.attributeID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.AttributepointPK[ characterID=" + characterID + ", attributeID=" + attributeID + " ]";
    }

}
