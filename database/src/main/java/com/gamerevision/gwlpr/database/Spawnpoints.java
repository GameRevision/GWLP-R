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
@Table(name = "spawnpoints")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spawnpoints.findAll", query = "SELECT s FROM Spawnpoints s"),
    @NamedQuery(name = "Spawnpoints.findById", query = "SELECT s FROM Spawnpoints s WHERE s.spawnpointsPK.id = :id"),
    @NamedQuery(name = "Spawnpoints.findByMapID", query = "SELECT s FROM Spawnpoints s WHERE s.spawnpointsPK.mapID = :mapID"),
    @NamedQuery(name = "Spawnpoints.findByX", query = "SELECT s FROM Spawnpoints s WHERE s.x = :x"),
    @NamedQuery(name = "Spawnpoints.findByY", query = "SELECT s FROM Spawnpoints s WHERE s.y = :y"),
    @NamedQuery(name = "Spawnpoints.findByPlane", query = "SELECT s FROM Spawnpoints s WHERE s.plane = :plane"),
    @NamedQuery(name = "Spawnpoints.findByRadius", query = "SELECT s FROM Spawnpoints s WHERE s.radius = :radius")})
public class Spawnpoints implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SpawnpointsPK spawnpointsPK;
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
    private Maps maps;

    public Spawnpoints() {
    }

    public Spawnpoints(SpawnpointsPK spawnpointsPK) {
        this.spawnpointsPK = spawnpointsPK;
    }

    public Spawnpoints(SpawnpointsPK spawnpointsPK, float x, float y, int plane, int radius) {
        this.spawnpointsPK = spawnpointsPK;
        this.x = x;
        this.y = y;
        this.plane = plane;
        this.radius = radius;
    }

    public Spawnpoints(int id, int mapID) {
        this.spawnpointsPK = new SpawnpointsPK(id, mapID);
    }

    public SpawnpointsPK getSpawnpointsPK() {
        return spawnpointsPK;
    }

    public void setSpawnpointsPK(SpawnpointsPK spawnpointsPK) {
        this.spawnpointsPK = spawnpointsPK;
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

    public Maps getMaps() {
        return maps;
    }

    public void setMaps(Maps maps) {
        this.maps = maps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spawnpointsPK != null ? spawnpointsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spawnpoints)) {
            return false;
        }
        Spawnpoints other = (Spawnpoints) object;
        if ((this.spawnpointsPK == null && other.spawnpointsPK != null) || (this.spawnpointsPK != null && !this.spawnpointsPK.equals(other.spawnpointsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Spawnpoints[ spawnpointsPK=" + spawnpointsPK + " ]";
    }

}
