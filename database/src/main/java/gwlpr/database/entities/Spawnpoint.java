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
@Table(name = "spawnpoints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spawnpoint.findAll", query = "SELECT s FROM Spawnpoint s"),
    @NamedQuery(name = "Spawnpoint.findById", query = "SELECT s FROM Spawnpoint s WHERE s.spawnpointPK.id = :id"),
    @NamedQuery(name = "Spawnpoint.findByMapID", query = "SELECT s FROM Spawnpoint s WHERE s.spawnpointPK.mapID = :mapID"),
    @NamedQuery(name = "Spawnpoint.findByX", query = "SELECT s FROM Spawnpoint s WHERE s.x = :x"),
    @NamedQuery(name = "Spawnpoint.findByY", query = "SELECT s FROM Spawnpoint s WHERE s.y = :y"),
    @NamedQuery(name = "Spawnpoint.findByPlane", query = "SELECT s FROM Spawnpoint s WHERE s.plane = :plane"),
    @NamedQuery(name = "Spawnpoint.findByRadius", query = "SELECT s FROM Spawnpoint s WHERE s.radius = :radius")})
public class Spawnpoint implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SpawnpointPK spawnpointPK;
    @Basic(optional = false)
    @Column(name = "X")
    private float x;
    @Basic(optional = false)
    @Column(name = "Y")
    private float y;
    @Basic(optional = false)
    @Column(name = "Plane")
    private int plane;
    @Basic(optional = false)
    @Column(name = "Radius")
    private int radius;
    @JoinColumn(name = "MapID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Map map;

    public Spawnpoint() {
    }

    public Spawnpoint(SpawnpointPK spawnpointPK) {
        this.spawnpointPK = spawnpointPK;
    }

    public Spawnpoint(SpawnpointPK spawnpointPK, float x, float y, int plane, int radius) {
        this.spawnpointPK = spawnpointPK;
        this.x = x;
        this.y = y;
        this.plane = plane;
        this.radius = radius;
    }

    public Spawnpoint(int id, int mapID) {
        this.spawnpointPK = new SpawnpointPK(id, mapID);
    }

    public SpawnpointPK getSpawnpointPK() {
        return spawnpointPK;
    }

    public void setSpawnpointPK(SpawnpointPK spawnpointPK) {
        this.spawnpointPK = spawnpointPK;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getPlane() {
        return plane;
    }

    public void setPlane(int plane) {
        this.plane = plane;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spawnpointPK != null ? spawnpointPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spawnpoint)) {
            return false;
        }
        Spawnpoint other = (Spawnpoint) object;
        if ((this.spawnpointPK == null && other.spawnpointPK != null) || (this.spawnpointPK != null && !this.spawnpointPK.equals(other.spawnpointPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.Spawnpoint[ spawnpointPK=" + spawnpointPK + " ]";
    }

}
