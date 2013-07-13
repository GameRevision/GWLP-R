/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "characters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Characters.findAll", query = "SELECT c FROM Characters c"),
    @NamedQuery(name = "Characters.findById", query = "SELECT c FROM Characters c WHERE c.id = :id"),
    @NamedQuery(name = "Characters.findByName", query = "SELECT c FROM Characters c WHERE c.name = :name"),
    @NamedQuery(name = "Characters.findByGold", query = "SELECT c FROM Characters c WHERE c.gold = :gold"),
    @NamedQuery(name = "Characters.findBySkillPoints", query = "SELECT c FROM Characters c WHERE c.skillPoints = :skillPoints"),
    @NamedQuery(name = "Characters.findBySkillPointsTotal", query = "SELECT c FROM Characters c WHERE c.skillPointsTotal = :skillPointsTotal"),
    @NamedQuery(name = "Characters.findByExperience", query = "SELECT c FROM Characters c WHERE c.experience = :experience"),
    @NamedQuery(name = "Characters.findByActiveWeaponset", query = "SELECT c FROM Characters c WHERE c.activeWeaponset = :activeWeaponset"),
    @NamedQuery(name = "Characters.findByShowHelm", query = "SELECT c FROM Characters c WHERE c.showHelm = :showHelm"),
    @NamedQuery(name = "Characters.findByShowCape", query = "SELECT c FROM Characters c WHERE c.showCape = :showCape"),
    @NamedQuery(name = "Characters.findByShowCostumeHead", query = "SELECT c FROM Characters c WHERE c.showCostumeHead = :showCostumeHead"),
    @NamedQuery(name = "Characters.findByShowCostumeBody", query = "SELECT c FROM Characters c WHERE c.showCostumeBody = :showCostumeBody"),
    @NamedQuery(name = "Characters.findByCampaign", query = "SELECT c FROM Characters c WHERE c.campaign = :campaign"),
    @NamedQuery(name = "Characters.findByFace", query = "SELECT c FROM Characters c WHERE c.face = :face"),
    @NamedQuery(name = "Characters.findByHaircolor", query = "SELECT c FROM Characters c WHERE c.haircolor = :haircolor"),
    @NamedQuery(name = "Characters.findByHairstyle", query = "SELECT c FROM Characters c WHERE c.hairstyle = :hairstyle"),
    @NamedQuery(name = "Characters.findByHeight", query = "SELECT c FROM Characters c WHERE c.height = :height"),
    @NamedQuery(name = "Characters.findBySex", query = "SELECT c FROM Characters c WHERE c.sex = :sex"),
    @NamedQuery(name = "Characters.findBySkin", query = "SELECT c FROM Characters c WHERE c.skin = :skin")})
public class Characters implements Serializable 
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
    @Column(name = "Gold")
    private Integer gold;
    @Column(name = "SkillPoints")
    private Integer skillPoints;
    @Column(name = "SkillPointsTotal")
    private Integer skillPointsTotal;
    @Column(name = "Experience")
    private Integer experience;
    @Column(name = "ActiveWeaponset")
    private Integer activeWeaponset;
    @Column(name = "ShowHelm")
    private Boolean showHelm;
    @Column(name = "ShowCape")
    private Boolean showCape;
    @Column(name = "ShowCostumeHead")
    private Boolean showCostumeHead;
    @Column(name = "ShowCostumeBody")
    private Boolean showCostumeBody;
    @Column(name = "Campaign")
    private Boolean campaign;
    @Column(name = "Face")
    private Boolean face;
    @Column(name = "Haircolor")
    private Boolean haircolor;
    @Column(name = "Hairstyle")
    private Boolean hairstyle;
    @Column(name = "Height")
    private Boolean height;
    @Column(name = "Sex")
    private Boolean sex;
    @Column(name = "Skin")
    private Boolean skin;
    @JoinTable(name = "skillaccess", joinColumns = {
        @JoinColumn(name = "CharacterID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "SkillID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Skills> skillsCollection;
    @JoinTable(name = "professionaccess", joinColumns = {
        @JoinColumn(name = "CharacterID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "Profession", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Professions> professionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "characters")
    private Collection<Skillsequipped> skillsequippedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "characters")
    private Collection<Weaponsets> weaponsetsCollection;
    @OneToMany(mappedBy = "customizedFor")
    private Collection<Items> itemsCollection;
    @JoinColumn(name = "SecondaryProfession", referencedColumnName = "ID")
    @ManyToOne
    private Professions secondaryProfession;
    @JoinColumn(name = "PrimaryProfession", referencedColumnName = "ID")
    @ManyToOne
    private Professions primaryProfession;
    @JoinColumn(name = "Level", referencedColumnName = "Level")
    @ManyToOne
    private Levels level;
    @JoinColumn(name = "LastOutpost", referencedColumnName = "ID")
    @ManyToOne
    private Maps lastOutpost;
    @JoinColumn(name = "EquipmentPack", referencedColumnName = "ID")
    @ManyToOne
    private Inventories equipmentPack;
    @JoinColumn(name = "Equipment", referencedColumnName = "ID")
    @ManyToOne
    private Inventories equipment;
    @JoinColumn(name = "Beltpouch", referencedColumnName = "ID")
    @ManyToOne
    private Inventories beltpouch;
    @JoinColumn(name = "Bag2", referencedColumnName = "ID")
    @ManyToOne
    private Inventories bag2;
    @JoinColumn(name = "Bag1", referencedColumnName = "ID")
    @ManyToOne
    private Inventories bag1;
    @JoinColumn(name = "Backpack", referencedColumnName = "ID")
    @ManyToOne
    private Inventories backpack;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Accounts accountID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "characters")
    private Collection<Attributepoints> attributepointsCollection;

    public Characters() {
    }

    public Characters(Integer id) {
        this.id = id;
    }

    public Characters(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(Integer skillPoints) {
        this.skillPoints = skillPoints;
    }

    public Integer getSkillPointsTotal() {
        return skillPointsTotal;
    }

    public void setSkillPointsTotal(Integer skillPointsTotal) {
        this.skillPointsTotal = skillPointsTotal;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getActiveWeaponset() {
        return activeWeaponset;
    }

    public void setActiveWeaponset(Integer activeWeaponset) {
        this.activeWeaponset = activeWeaponset;
    }

    public Boolean getShowHelm() {
        return showHelm;
    }

    public void setShowHelm(Boolean showHelm) {
        this.showHelm = showHelm;
    }

    public Boolean getShowCape() {
        return showCape;
    }

    public void setShowCape(Boolean showCape) {
        this.showCape = showCape;
    }

    public Boolean getShowCostumeHead() {
        return showCostumeHead;
    }

    public void setShowCostumeHead(Boolean showCostumeHead) {
        this.showCostumeHead = showCostumeHead;
    }

    public Boolean getShowCostumeBody() {
        return showCostumeBody;
    }

    public void setShowCostumeBody(Boolean showCostumeBody) {
        this.showCostumeBody = showCostumeBody;
    }

    public Boolean getCampaign() {
        return campaign;
    }

    public void setCampaign(Boolean campaign) {
        this.campaign = campaign;
    }

    public Boolean getFace() {
        return face;
    }

    public void setFace(Boolean face) {
        this.face = face;
    }

    public Boolean getHaircolor() {
        return haircolor;
    }

    public void setHaircolor(Boolean haircolor) {
        this.haircolor = haircolor;
    }

    public Boolean getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(Boolean hairstyle) {
        this.hairstyle = hairstyle;
    }

    public Boolean getHeight() {
        return height;
    }

    public void setHeight(Boolean height) {
        this.height = height;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getSkin() {
        return skin;
    }

    public void setSkin(Boolean skin) {
        this.skin = skin;
    }

    @XmlTransient
    public Collection<Skills> getSkillsCollection() {
        return skillsCollection;
    }

    public void setSkillsCollection(Collection<Skills> skillsCollection) {
        this.skillsCollection = skillsCollection;
    }

    @XmlTransient
    public Collection<Professions> getProfessionsCollection() {
        return professionsCollection;
    }

    public void setProfessionsCollection(Collection<Professions> professionsCollection) {
        this.professionsCollection = professionsCollection;
    }

    @XmlTransient
    public Collection<Skillsequipped> getSkillsequippedCollection() {
        return skillsequippedCollection;
    }

    public void setSkillsequippedCollection(Collection<Skillsequipped> skillsequippedCollection) {
        this.skillsequippedCollection = skillsequippedCollection;
    }

    @XmlTransient
    public Collection<Weaponsets> getWeaponsetsCollection() {
        return weaponsetsCollection;
    }

    public void setWeaponsetsCollection(Collection<Weaponsets> weaponsetsCollection) {
        this.weaponsetsCollection = weaponsetsCollection;
    }

    @XmlTransient
    public Collection<Items> getItemsCollection() {
        return itemsCollection;
    }

    public void setItemsCollection(Collection<Items> itemsCollection) {
        this.itemsCollection = itemsCollection;
    }

    public Professions getSecondaryProfession() {
        return secondaryProfession;
    }

    public void setSecondaryProfession(Professions secondaryProfession) {
        this.secondaryProfession = secondaryProfession;
    }

    public Professions getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(Professions primaryProfession) {
        this.primaryProfession = primaryProfession;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    public Maps getLastOutpost() {
        return lastOutpost;
    }

    public void setLastOutpost(Maps lastOutpost) {
        this.lastOutpost = lastOutpost;
    }

    public Inventories getEquipmentPack() {
        return equipmentPack;
    }

    public void setEquipmentPack(Inventories equipmentPack) {
        this.equipmentPack = equipmentPack;
    }

    public Inventories getEquipment() {
        return equipment;
    }

    public void setEquipment(Inventories equipment) {
        this.equipment = equipment;
    }

    public Inventories getBeltpouch() {
        return beltpouch;
    }

    public void setBeltpouch(Inventories beltpouch) {
        this.beltpouch = beltpouch;
    }

    public Inventories getBag2() {
        return bag2;
    }

    public void setBag2(Inventories bag2) {
        this.bag2 = bag2;
    }

    public Inventories getBag1() {
        return bag1;
    }

    public void setBag1(Inventories bag1) {
        this.bag1 = bag1;
    }

    public Inventories getBackpack() {
        return backpack;
    }

    public void setBackpack(Inventories backpack) {
        this.backpack = backpack;
    }

    public Accounts getAccountID() {
        return accountID;
    }

    public void setAccountID(Accounts accountID) {
        this.accountID = accountID;
    }

    @XmlTransient
    public Collection<Attributepoints> getAttributepointsCollection() {
        return attributepointsCollection;
    }

    public void setAttributepointsCollection(Collection<Attributepoints> attributepointsCollection) {
        this.attributepointsCollection = attributepointsCollection;
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
        if (!(object instanceof Characters)) {
            return false;
        }
        Characters other = (Characters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gamerevision.gwlpr.database.Characters[ id=" + id + " ]";
    }

}
