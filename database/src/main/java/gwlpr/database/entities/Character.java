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
    @NamedQuery(name = "Character.findAll", query = "SELECT c FROM Character c"),
    @NamedQuery(name = "Character.findById", query = "SELECT c FROM Character c WHERE c.id = :id"),
    @NamedQuery(name = "Character.findByName", query = "SELECT c FROM Character c WHERE c.name = :name"),
    @NamedQuery(name = "Character.findByGold", query = "SELECT c FROM Character c WHERE c.gold = :gold"),
    @NamedQuery(name = "Character.findBySkillPoints", query = "SELECT c FROM Character c WHERE c.skillPoints = :skillPoints"),
    @NamedQuery(name = "Character.findBySkillPointsTotal", query = "SELECT c FROM Character c WHERE c.skillPointsTotal = :skillPointsTotal"),
    @NamedQuery(name = "Character.findByExperience", query = "SELECT c FROM Character c WHERE c.experience = :experience"),
    @NamedQuery(name = "Character.findByActiveWeaponset", query = "SELECT c FROM Character c WHERE c.activeWeaponset = :activeWeaponset"),
    @NamedQuery(name = "Character.findByShowHelm", query = "SELECT c FROM Character c WHERE c.showHelm = :showHelm"),
    @NamedQuery(name = "Character.findByShowCape", query = "SELECT c FROM Character c WHERE c.showCape = :showCape"),
    @NamedQuery(name = "Character.findByShowCostumeHead", query = "SELECT c FROM Character c WHERE c.showCostumeHead = :showCostumeHead"),
    @NamedQuery(name = "Character.findByShowCostumeBody", query = "SELECT c FROM Character c WHERE c.showCostumeBody = :showCostumeBody"),
    @NamedQuery(name = "Character.findByCampaign", query = "SELECT c FROM Character c WHERE c.campaign = :campaign"),
    @NamedQuery(name = "Character.findByFace", query = "SELECT c FROM Character c WHERE c.face = :face"),
    @NamedQuery(name = "Character.findByHaircolor", query = "SELECT c FROM Character c WHERE c.haircolor = :haircolor"),
    @NamedQuery(name = "Character.findByHairstyle", query = "SELECT c FROM Character c WHERE c.hairstyle = :hairstyle"),
    @NamedQuery(name = "Character.findByHeight", query = "SELECT c FROM Character c WHERE c.height = :height"),
    @NamedQuery(name = "Character.findBySex", query = "SELECT c FROM Character c WHERE c.sex = :sex"),
    @NamedQuery(name = "Character.findBySkin", query = "SELECT c FROM Character c WHERE c.skin = :skin")})
public class Character implements Serializable 
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
    private Collection<Skill> skillCollection;
    @JoinTable(name = "professionaccess", joinColumns = {
        @JoinColumn(name = "CharacterID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "Profession", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<Profession> professionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character")
    private Collection<EquippedSkill> equippedSkillCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character")
    private Collection<Weaponset> weaponsetCollection;
    @OneToMany(mappedBy = "customizedFor")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "SecondaryProfession", referencedColumnName = "ID")
    @ManyToOne
    private Profession secondaryProfession;
    @JoinColumn(name = "PrimaryProfession", referencedColumnName = "ID")
    @ManyToOne
    private Profession primaryProfession;
    @JoinColumn(name = "Level", referencedColumnName = "Level")
    @ManyToOne
    private Level level;
    @JoinColumn(name = "LastOutpost", referencedColumnName = "ID")
    @ManyToOne
    private Map lastOutpost;
    @JoinColumn(name = "EquipmentPack", referencedColumnName = "ID")
    @ManyToOne
    private Inventory equipmentPack;
    @JoinColumn(name = "Equipment", referencedColumnName = "ID")
    @ManyToOne
    private Inventory equipment;
    @JoinColumn(name = "Beltpouch", referencedColumnName = "ID")
    @ManyToOne
    private Inventory beltpouch;
    @JoinColumn(name = "Bag2", referencedColumnName = "ID")
    @ManyToOne
    private Inventory bag2;
    @JoinColumn(name = "Bag1", referencedColumnName = "ID")
    @ManyToOne
    private Inventory bag1;
    @JoinColumn(name = "Backpack", referencedColumnName = "ID")
    @ManyToOne
    private Inventory backpack;
    @JoinColumn(name = "AccountID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "character")
    private Collection<Attributepoint> attributepointCollection;

    public Character() {
    }

    public Character(Integer id) {
        this.id = id;
    }

    public Character(Integer id, String name) {
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
    public Collection<Skill> getSkillCollection() {
        return skillCollection;
    }

    public void setSkillCollection(Collection<Skill> skillCollection) {
        this.skillCollection = skillCollection;
    }

    @XmlTransient
    public Collection<Profession> getProfessionCollection() {
        return professionCollection;
    }

    public void setProfessionCollection(Collection<Profession> professionCollection) {
        this.professionCollection = professionCollection;
    }

    @XmlTransient
    public Collection<EquippedSkill> getEquippedSkillCollection() {
        return equippedSkillCollection;
    }

    public void setEquippedSkillCollection(Collection<EquippedSkill> equippedSkillCollection) {
        this.equippedSkillCollection = equippedSkillCollection;
    }

    @XmlTransient
    public Collection<Weaponset> getWeaponsetCollection() {
        return weaponsetCollection;
    }

    public void setWeaponsetCollection(Collection<Weaponset> weaponsetCollection) {
        this.weaponsetCollection = weaponsetCollection;
    }

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Profession getSecondaryProfession() {
        return secondaryProfession;
    }

    public void setSecondaryProfession(Profession secondaryProfession) {
        this.secondaryProfession = secondaryProfession;
    }

    public Profession getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(Profession primaryProfession) {
        this.primaryProfession = primaryProfession;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Map getLastOutpost() {
        return lastOutpost;
    }

    public void setLastOutpost(Map lastOutpost) {
        this.lastOutpost = lastOutpost;
    }

    public Inventory getEquipmentPack() {
        return equipmentPack;
    }

    public void setEquipmentPack(Inventory equipmentPack) {
        this.equipmentPack = equipmentPack;
    }

    public Inventory getEquipment() {
        return equipment;
    }

    public void setEquipment(Inventory equipment) {
        this.equipment = equipment;
    }

    public Inventory getBeltpouch() {
        return beltpouch;
    }

    public void setBeltpouch(Inventory beltpouch) {
        this.beltpouch = beltpouch;
    }

    public Inventory getBag2() {
        return bag2;
    }

    public void setBag2(Inventory bag2) {
        this.bag2 = bag2;
    }

    public Inventory getBag1() {
        return bag1;
    }

    public void setBag1(Inventory bag1) {
        this.bag1 = bag1;
    }

    public Inventory getBackpack() {
        return backpack;
    }

    public void setBackpack(Inventory backpack) {
        this.backpack = backpack;
    }

    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    @XmlTransient
    public Collection<Attributepoint> getAttributepointCollection() {
        return attributepointCollection;
    }

    public void setAttributepointCollection(Collection<Attributepoint> attributepointCollection) {
        this.attributepointCollection = attributepointCollection;
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
        if (!(object instanceof Character)) {
            return false;
        }
        Character other = (Character) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gwlpr.database.entities.gen.Character[ id=" + id + " ]";
    }

}
