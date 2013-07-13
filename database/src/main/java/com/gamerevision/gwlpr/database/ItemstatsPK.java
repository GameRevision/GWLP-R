/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author _rusty
 */
@Embeddable 
public class ItemstatsPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "ItemID")
    private int itemID;
    @Basic(optional = false)
    @Column(name = "StatID")
    private int statID;

    public ItemstatsPK() {
    }

    public ItemstatsPK(int itemID, int statID) {
        this.itemID = itemID;
        this.statID = statID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getStatID() {
        return statID;
    }

    public void setStatID(int statID) {
        this.statID = statID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) itemID;
        hash += (int) statID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemstatsPK)) {
            return false;
        }
        ItemstatsPK other = (ItemstatsPK) object;
        if (this.itemID != other.itemID) {
            return false;
        }
        if (this.statID != other.statID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.ItemstatsPK[ itemID=" + itemID + ", statID=" + statID + " ]";
    }

}
