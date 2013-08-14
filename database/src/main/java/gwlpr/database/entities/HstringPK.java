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
public class HstringPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "StringID")
    private short stringID;
    @Basic(optional = false)
    @Column(name = "BlockID")
    private short blockID;

    public HstringPK() {
    }

    public HstringPK(short stringID, short blockID) {
        this.stringID = stringID;
        this.blockID = blockID;
    }

    public short getStringID() {
        return stringID;
    }

    public void setStringID(short stringID) {
        this.stringID = stringID;
    }

    public short getBlockID() {
        return blockID;
    }

    public void setBlockID(short blockID) {
        this.blockID = blockID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) stringID;
        hash += (int) blockID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HstringPK)) {
            return false;
        }
        HstringPK other = (HstringPK) object;
        if (this.stringID != other.stringID) {
            return false;
        }
        if (this.blockID != other.blockID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.HstringPK[ stringID=" + stringID + ", blockID=" + blockID + " ]";
    }

}
