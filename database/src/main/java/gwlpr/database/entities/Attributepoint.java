/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "attributepoints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attributepoint.findAll", query = "SELECT a FROM Attributepoint a"),
    @NamedQuery(name = "Attributepoint.findByCharacterID", query = "SELECT a FROM Attributepoint a WHERE a.attributepointPK.characterID = :characterID"),
    @NamedQuery(name = "Attributepoint.findByAttributeID", query = "SELECT a FROM Attributepoint a WHERE a.attributepointPK.attributeID = :attributeID"),
    @NamedQuery(name = "Attributepoint.findByAmount", query = "SELECT a FROM Attributepoint a WHERE a.amount = :amount")})
public class Attributepoint implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttributepointPK attributepointPK;
    @Basic(optional = false)
    @Column(name = "Amount")
    private int amount;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Character character;
    @JoinColumn(name = "AttributeID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Attribute attribute;

    public Attributepoint() {
    }

    public Attributepoint(AttributepointPK attributepointPK) {
        this.attributepointPK = attributepointPK;
    }

    public Attributepoint(AttributepointPK attributepointPK, int amount) {
        this.attributepointPK = attributepointPK;
        this.amount = amount;
    }

    public Attributepoint(int characterID, int attributeID) {
        this.attributepointPK = new AttributepointPK(characterID, attributeID);
    }

    public AttributepointPK getAttributepointPK() {
        return attributepointPK;
    }

    public void setAttributepointPK(AttributepointPK attributepointPK) {
        this.attributepointPK = attributepointPK;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributepointPK != null ? attributepointPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attributepoint)) {
            return false;
        }
        Attributepoint other = (Attributepoint) object;
        if ((this.attributepointPK == null && other.attributepointPK != null) || (this.attributepointPK != null && !this.attributepointPK.equals(other.attributepointPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Attributepoint[ attributepointPK=" + attributepointPK + " ]";
    }

}
