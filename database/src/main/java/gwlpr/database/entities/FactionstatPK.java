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
public class FactionstatPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "AccountID")
    private int accountID;
    @Basic(optional = false)
    @Column(name = "Type")
    private int type;

    public FactionstatPK() {
    }

    public FactionstatPK(int accountID, int type) {
        this.accountID = accountID;
        this.type = type;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) accountID;
        hash += (int) type;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FactionstatPK)) {
            return false;
        }
        FactionstatPK other = (FactionstatPK) object;
        if (this.accountID != other.accountID) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.FactionstatPK[ accountID=" + accountID + ", type=" + type + " ]";
    }

}
