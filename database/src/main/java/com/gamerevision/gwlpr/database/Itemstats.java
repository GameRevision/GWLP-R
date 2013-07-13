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
@Table(name = "itemstats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemstats.findAll", query = "SELECT i FROM Itemstats i"),
    @NamedQuery(name = "Itemstats.findByItemID", query = "SELECT i FROM Itemstats i WHERE i.itemstatsPK.itemID = :itemID"),
    @NamedQuery(name = "Itemstats.findByStatID", query = "SELECT i FROM Itemstats i WHERE i.itemstatsPK.statID = :statID"),
    @NamedQuery(name = "Itemstats.findByParameter1", query = "SELECT i FROM Itemstats i WHERE i.parameter1 = :parameter1"),
    @NamedQuery(name = "Itemstats.findByParameter2", query = "SELECT i FROM Itemstats i WHERE i.parameter2 = :parameter2")})
public class Itemstats implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemstatsPK itemstatsPK;
    @Basic(optional = false)
    @Column(name = "Parameter1")
    private int parameter1;
    @Basic(optional = false)
    @Column(name = "Parameter2")
    private int parameter2;
    @JoinColumn(name = "ItemID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Items items;

    public Itemstats() {
    }

    public Itemstats(ItemstatsPK itemstatsPK) {
        this.itemstatsPK = itemstatsPK;
    }

    public Itemstats(ItemstatsPK itemstatsPK, int parameter1, int parameter2) {
        this.itemstatsPK = itemstatsPK;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

    public Itemstats(int itemID, int statID) {
        this.itemstatsPK = new ItemstatsPK(itemID, statID);
    }

    public ItemstatsPK getItemstatsPK() {
        return itemstatsPK;
    }

    public void setItemstatsPK(ItemstatsPK itemstatsPK) {
        this.itemstatsPK = itemstatsPK;
    }

    public int getParameter1() {
        return parameter1;
    }

    public void setParameter1(int parameter1) {
        this.parameter1 = parameter1;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemstatsPK != null ? itemstatsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemstats)) {
            return false;
        }
        Itemstats other = (Itemstats) object;
        if ((this.itemstatsPK == null && other.itemstatsPK != null) || (this.itemstatsPK != null && !this.itemstatsPK.equals(other.itemstatsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Itemstats[ itemstatsPK=" + itemstatsPK + " ]";
    }

}
