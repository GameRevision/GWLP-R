/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author _rusty
 */
@Entity 
@Table(name = "commands")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commands.findAll", query = "SELECT c FROM Commands c"),
    @NamedQuery(name = "Commands.findById", query = "SELECT c FROM Commands c WHERE c.commandsPK.id = :id"),
    @NamedQuery(name = "Commands.findByName", query = "SELECT c FROM Commands c WHERE c.commandsPK.name = :name")})
public class Commands implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommandsPK commandsPK;
    @JoinTable(name = "grouppermissions", joinColumns = {
        @JoinColumn(name = "CommandID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "GroupID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Groups> groupsCollection;

    public Commands() {
    }

    public Commands(CommandsPK commandsPK) {
        this.commandsPK = commandsPK;
    }

    public Commands(int id, String name) {
        this.commandsPK = new CommandsPK(id, name);
    }

    public CommandsPK getCommandsPK() {
        return commandsPK;
    }

    public void setCommandsPK(CommandsPK commandsPK) {
        this.commandsPK = commandsPK;
    }

    @XmlTransient
    public Collection<Groups> getGroupsCollection() {
        return groupsCollection;
    }

    public void setGroupsCollection(Collection<Groups> groupsCollection) {
        this.groupsCollection = groupsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commandsPK != null ? commandsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commands)) {
            return false;
        }
        Commands other = (Commands) object;
        if ((this.commandsPK == null && other.commandsPK != null) || (this.commandsPK != null && !this.commandsPK.equals(other.commandsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Commands[ commandsPK=" + commandsPK + " ]";
    }

}
