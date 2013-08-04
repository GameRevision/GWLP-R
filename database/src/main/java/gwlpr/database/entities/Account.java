/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author _rusty
 */
@Entity 
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByEMail", query = "SELECT a FROM Account a WHERE a.eMail = :eMail"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByGoldStorage", query = "SELECT a FROM Account a WHERE a.goldStorage = :goldStorage")})
public class Account implements Serializable 
{
    @Basic(optional = false)
    @Lob
    @Column(name = "GUI")
    private byte[] gui;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    private int id;
    @Id
    @Basic(optional = false)
    @Column(name = "EMail")
    private String eMail;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "GoldStorage")
    private int goldStorage;
    @JoinColumn(name = "MaterialStorage", referencedColumnName = "ID")
    @ManyToOne
    private Inventory materialStorage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<Storagetab> storagetabCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<Factionstat> factionstatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountID")
    private Collection<Character> characterCollection;

    public Account() {
    }

    public Account(String eMail) {
        this.eMail = eMail;
    }

    public Account(String eMail, int id, String password, byte[] gui, int goldStorage) {
        this.eMail = eMail;
        this.id = id;
        this.password = password;
        this.gui = gui;
        this.goldStorage = goldStorage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGoldStorage() {
        return goldStorage;
    }

    public void setGoldStorage(int goldStorage) {
        this.goldStorage = goldStorage;
    }

    public Inventory getMaterialStorage() {
        return materialStorage;
    }

    public void setMaterialStorage(Inventory materialStorage) {
        this.materialStorage = materialStorage;
    }

    @XmlTransient
    public Collection<Storagetab> getStoragetabCollection() {
        return storagetabCollection;
    }

    public void setStoragetabCollection(Collection<Storagetab> storagetabCollection) {
        this.storagetabCollection = storagetabCollection;
    }

    @XmlTransient
    public Collection<Factionstat> getFactionstatCollection() {
        return factionstatCollection;
    }

    public void setFactionstatCollection(Collection<Factionstat> factionstatCollection) {
        this.factionstatCollection = factionstatCollection;
    }

    @XmlTransient
    public Collection<Character> getCharacterCollection() {
        return characterCollection;
    }

    public void setCharacterCollection(Collection<Character> characterCollection) {
        this.characterCollection = characterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eMail != null ? eMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.eMail == null && other.eMail != null) || (this.eMail != null && !this.eMail.equals(other.eMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Account[ eMail=" + eMail + " ]";
    }

    public byte[] getGui() {
        return gui;
    }

    public void setGui(byte[] gui) {
        this.gui = gui;
    }

}
