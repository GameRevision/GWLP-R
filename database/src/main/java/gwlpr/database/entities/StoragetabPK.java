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
public class StoragetabPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "AccountID")
    private int accountID;
    @Basic(optional = false)
    @Column(name = "Number")
    private int number;

    public StoragetabPK() {
    }

    public StoragetabPK(int accountID, int number) {
        this.accountID = accountID;
        this.number = number;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) accountID;
        hash += (int) number;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoragetabPK)) {
            return false;
        }
        StoragetabPK other = (StoragetabPK) object;
        if (this.accountID != other.accountID) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.StoragetabPK[ accountID=" + accountID + ", number=" + number + " ]";
    }

}
