/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

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
    @NamedQuery(name = "Attributepoints.findAll", query = "SELECT a FROM Attributepoints a"),
    @NamedQuery(name = "Attributepoints.findByCharacterID", query = "SELECT a FROM Attributepoints a WHERE a.attributepointsPK.characterID = :characterID"),
    @NamedQuery(name = "Attributepoints.findByAttributeID", query = "SELECT a FROM Attributepoints a WHERE a.attributepointsPK.attributeID = :attributeID"),
    @NamedQuery(name = "Attributepoints.findByAmount", query = "SELECT a FROM Attributepoints a WHERE a.amount = :amount")})
public class Attributepoints implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttributepointsPK attributepointsPK;
    @Basic(optional = false)
    @Column(name = "Amount")
    private int amount;
    @JoinColumn(name = "CharacterID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Characters characters;
    @JoinColumn(name = "AttributeID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Attributes attributes;

    public Attributepoints() {
    }

    public Attributepoints(AttributepointsPK attributepointsPK) {
        this.attributepointsPK = attributepointsPK;
    }

    public Attributepoints(AttributepointsPK attributepointsPK, int amount) {
        this.attributepointsPK = attributepointsPK;
        this.amount = amount;
    }

    public Attributepoints(int characterID, int attributeID) {
        this.attributepointsPK = new AttributepointsPK(characterID, attributeID);
    }

    public AttributepointsPK getAttributepointsPK() {
        return attributepointsPK;
    }

    public void setAttributepointsPK(AttributepointsPK attributepointsPK) {
        this.attributepointsPK = attributepointsPK;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Characters getCharacters() {
        return characters;
    }

    public void setCharacters(Characters characters) {
        this.characters = characters;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributepointsPK != null ? attributepointsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attributepoints)) {
            return false;
        }
        Attributepoints other = (Attributepoints) object;
        if ((this.attributepointsPK == null && other.attributepointsPK != null) || (this.attributepointsPK != null && !this.attributepointsPK.equals(other.attributepointsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Attributepoints[ attributepointsPK=" + attributepointsPK + " ]";
    }

}
