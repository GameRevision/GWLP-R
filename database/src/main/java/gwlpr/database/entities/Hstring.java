/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author _rusty
 */
@Entity 
@Table(name = "hstring")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hstring.findAll", query = "SELECT h FROM Hstring h"),
    @NamedQuery(name = "Hstring.findByStringID", query = "SELECT h FROM Hstring h WHERE h.hstringPK.stringID = :stringID"),
    @NamedQuery(name = "Hstring.findByBlockID", query = "SELECT h FROM Hstring h WHERE h.hstringPK.blockID = :blockID"),
    @NamedQuery(name = "Hstring.findByEncryptedLength", query = "SELECT h FROM Hstring h WHERE h.encryptedLength = :encryptedLength"),
    @NamedQuery(name = "Hstring.findByDisplacement", query = "SELECT h FROM Hstring h WHERE h.displacement = :displacement"),
    @NamedQuery(name = "Hstring.findByCompressingValue", query = "SELECT h FROM Hstring h WHERE h.compressingValue = :compressingValue")})
public class Hstring implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HstringPK hstringPK;
    @Basic(optional = false)
    @Lob
    @Column(name = "EncryptedString")
    private byte[] encryptedString;
    @Basic(optional = false)
    @Column(name = "EncryptedLength")
    private int encryptedLength;
    @Basic(optional = false)
    @Column(name = "Displacement")
    private int displacement;
    @Basic(optional = false)
    @Column(name = "CompressingValue")
    private int compressingValue;

    public Hstring() {
    }

    public Hstring(HstringPK hstringPK) {
        this.hstringPK = hstringPK;
    }

    public Hstring(HstringPK hstringPK, byte[] encryptedString, int encryptedLength, int displacement, int compressingValue) {
        this.hstringPK = hstringPK;
        this.encryptedString = encryptedString;
        this.encryptedLength = encryptedLength;
        this.displacement = displacement;
        this.compressingValue = compressingValue;
    }

    public Hstring(short stringID, short blockID) {
        this.hstringPK = new HstringPK(stringID, blockID);
    }

    public HstringPK getHstringPK() {
        return hstringPK;
    }

    public void setHstringPK(HstringPK hstringPK) {
        this.hstringPK = hstringPK;
    }

    public byte[] getEncryptedString() {
        return encryptedString;
    }

    public void setEncryptedString(byte[] encryptedString) {
        this.encryptedString = encryptedString;
    }

    public int getEncryptedLength() {
        return encryptedLength;
    }

    public void setEncryptedLength(int encryptedLength) {
        this.encryptedLength = encryptedLength;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getCompressingValue() {
        return compressingValue;
    }

    public void setCompressingValue(int compressingValue) {
        this.compressingValue = compressingValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hstringPK != null ? hstringPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hstring)) {
            return false;
        }
        Hstring other = (Hstring) object;
        if ((this.hstringPK == null && other.hstringPK != null) || (this.hstringPK != null && !this.hstringPK.equals(other.hstringPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Hstring[ hstringPK=" + hstringPK + " ]";
    }

}
