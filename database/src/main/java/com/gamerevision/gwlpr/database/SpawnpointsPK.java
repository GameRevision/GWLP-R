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
public class SpawnpointsPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "MapID")
    private int mapID;

    public SpawnpointsPK() {
    }

    public SpawnpointsPK(int id, int mapID) {
        this.id = id;
        this.mapID = mapID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) mapID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpawnpointsPK)) {
            return false;
        }
        SpawnpointsPK other = (SpawnpointsPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.mapID != other.mapID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.SpawnpointsPK[ id=" + id + ", mapID=" + mapID + " ]";
    }

}
