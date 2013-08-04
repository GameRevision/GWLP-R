/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

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
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u"),
    @NamedQuery(name = "UserGroup.findById", query = "SELECT u FROM UserGroup u WHERE u.id = :id"),
    @NamedQuery(name = "UserGroup.findByName", query = "SELECT u FROM UserGroup u WHERE u.name = :name"),
    @NamedQuery(name = "UserGroup.findByPrefix", query = "SELECT u FROM UserGroup u WHERE u.prefix = :prefix"),
    @NamedQuery(name = "UserGroup.findByChatColor", query = "SELECT u FROM UserGroup u WHERE u.chatColor = :chatColor")})
public class UserGroup implements Serializable 
{
    @ManyToMany(mappedBy = "userGroupCollection")
    private Collection<Command> commandsCollection;
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
    @ManyToMany(mappedBy = "userGroupCollection")
    private Collection<Command> commandCollection;

    public UserGroup() {
    }

    public UserGroup(Integer id) {
        this.id = id;
    }

    public UserGroup(Integer id, String name, String prefix, int chatColor) {
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
    public Collection<Command> getCommandCollection() {
        return commandCollection;
    }

    public void setCommandCollection(Collection<Command> commandCollection) {
        this.commandCollection = commandCollection;
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
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.UserGroup[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Command> getCommandsCollection() {
        return commandsCollection;
    }

    public void setCommandsCollection(Collection<Command> commandsCollection) {
        this.commandsCollection = commandsCollection;
    }

}
