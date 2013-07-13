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
public class CommandsPK implements Serializable 
{
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;

    public CommandsPK() {
    }

    public CommandsPK(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommandsPK)) {
            return false;
        }
        CommandsPK other = (CommandsPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.CommandsPK[ id=" + id + ", name=" + name + " ]";
    }

}
