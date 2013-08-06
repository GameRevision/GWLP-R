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
public class SpawnpointPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "MapID")
    private int mapID;

    public SpawnpointPK() {
    }

    public SpawnpointPK(int id, int mapID) {
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
        if (!(object instanceof SpawnpointPK)) {
            return false;
        }
        SpawnpointPK other = (SpawnpointPK) object;
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
        return "gwlpr.database.entities.SpawnpointPK[ id=" + id + ", mapID=" + mapID + " ]";
    }

}
