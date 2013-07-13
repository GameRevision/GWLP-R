/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "groups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.findById", query = "SELECT g FROM Groups g WHERE g.id = :id"),
    @NamedQuery(name = "Groups.findByName", query = "SELECT g FROM Groups g WHERE g.name = :name"),
    @NamedQuery(name = "Groups.findByPrefix", query = "SELECT g FROM Groups g WHERE g.prefix = :prefix"),
    @NamedQuery(name = "Groups.findByChatColor", query = "SELECT g FROM Groups g WHERE g.chatColor = :chatColor")})
public class Groups implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Prefix")
    private String prefix;
    @Basic(optional = false)
    @Column(name = "ChatColor")
    private int chatColor;
    @ManyToMany(mappedBy = "groupsCollection")
    private Collection<Commands> commandsCollection;

    public Groups() {
    }

    public Groups(Integer id) {
        this.id = id;
    }

    public Groups(Integer id, String name, String prefix, int chatColor) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.chatColor = chatColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getChatColor() {
        return chatColor;
    }

    public void setChatColor(int chatColor) {
        this.chatColor = chatColor;
    }

    @XmlTransient
    public Collection<Commands> getCommandsCollection() {
        return commandsCollection;
    }

    public void setCommandsCollection(Collection<Commands> commandsCollection) {
        this.commandsCollection = commandsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Groups[ id=" + id + " ]";
    }

}
