/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Profession;
import gwlpr.database.entities.Level;
import gwlpr.database.entities.Map;
import gwlpr.database.entities.Inventory;
import gwlpr.database.entities.Account;
import gwlpr.database.entities.Skill;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.EquippedSkill;
import gwlpr.database.entities.Weaponset;
import gwlpr.database.entities.Item;
import gwlpr.database.entities.Attributepoint;
import gwlpr.database.entities.Character;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class CharacterJpaController implements Serializable 
{

    public CharacterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Character character) {
        if (character.getSkillCollection() == null) {
            character.setSkillCollection(new ArrayList<Skill>());
        }
        if (character.getProfessionCollection() == null) {
            character.setProfessionCollection(new ArrayList<Profession>());
        }
        if (character.getEquippedSkillCollection() == null) {
            character.setEquippedSkillCollection(new ArrayList<EquippedSkill>());
        }
        if (character.getWeaponsetCollection() == null) {
            character.setWeaponsetCollection(new ArrayList<Weaponset>());
        }
        if (character.getItemCollection() == null) {
            character.setItemCollection(new ArrayList<Item>());
        }
        if (character.getAttributepointCollection() == null) {
            character.setAttributepointCollection(new ArrayList<Attributepoint>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profession secondaryProfession = character.getSecondaryProfession();
            if (secondaryProfession != null) {
                secondaryProfession = em.getReference(secondaryProfession.getClass(), secondaryProfession.getId());
                character.setSecondaryProfession(secondaryProfession);
            }
            Profession primaryProfession = character.getPrimaryProfession();
            if (primaryProfession != null) {
                primaryProfession = em.getReference(primaryProfession.getClass(), primaryProfession.getId());
                character.setPrimaryProfession(primaryProfession);
            }
            Level level = character.getLevel();
            if (level != null) {
                level = em.getReference(level.getClass(), level.getLevel());
                character.setLevel(level);
            }
            Map lastOutpost = character.getLastOutpost();
            if (lastOutpost != null) {
                lastOutpost = em.getReference(lastOutpost.getClass(), lastOutpost.getId());
                character.setLastOutpost(lastOutpost);
            }
            Inventory equipmentPack = character.getEquipmentPack();
            if (equipmentPack != null) {
                equipmentPack = em.getReference(equipmentPack.getClass(), equipmentPack.getId());
                character.setEquipmentPack(equipmentPack);
            }
            Inventory equipment = character.getEquipment();
            if (equipment != null) {
                equipment = em.getReference(equipment.getClass(), equipment.getId());
                character.setEquipment(equipment);
            }
            Inventory beltpouch = character.getBeltpouch();
            if (beltpouch != null) {
                beltpouch = em.getReference(beltpouch.getClass(), beltpouch.getId());
                character.setBeltpouch(beltpouch);
            }
            Inventory bag2 = character.getBag2();
            if (bag2 != null) {
                bag2 = em.getReference(bag2.getClass(), bag2.getId());
                character.setBag2(bag2);
            }
            Inventory bag1 = character.getBag1();
            if (bag1 != null) {
                bag1 = em.getReference(bag1.getClass(), bag1.getId());
                character.setBag1(bag1);
            }
            Inventory backpack = character.getBackpack();
            if (backpack != null) {
                backpack = em.getReference(backpack.getClass(), backpack.getId());
                character.setBackpack(backpack);
            }
            Account accountID = character.getAccountID();
            if (accountID != null) {
                accountID = em.getReference(accountID.getClass(), accountID.getEMail());
                character.setAccountID(accountID);
            }
            Collection<Skill> attachedSkillCollection = new ArrayList<Skill>();
            for (Skill skillCollectionSkillToAttach : character.getSkillCollection()) {
                skillCollectionSkillToAttach = em.getReference(skillCollectionSkillToAttach.getClass(), skillCollectionSkillToAttach.getId());
                attachedSkillCollection.add(skillCollectionSkillToAttach);
            }
            character.setSkillCollection(attachedSkillCollection);
            Collection<Profession> attachedProfessionCollection = new ArrayList<Profession>();
            for (Profession professionCollectionProfessionToAttach : character.getProfessionCollection()) {
                professionCollectionProfessionToAttach = em.getReference(professionCollectionProfessionToAttach.getClass(), professionCollectionProfessionToAttach.getId());
                attachedProfessionCollection.add(professionCollectionProfessionToAttach);
            }
            character.setProfessionCollection(attachedProfessionCollection);
            Collection<EquippedSkill> attachedEquippedSkillCollection = new ArrayList<EquippedSkill>();
            for (EquippedSkill equippedSkillCollectionEquippedSkillToAttach : character.getEquippedSkillCollection()) {
                equippedSkillCollectionEquippedSkillToAttach = em.getReference(equippedSkillCollectionEquippedSkillToAttach.getClass(), equippedSkillCollectionEquippedSkillToAttach.getEquippedSkillPK());
                attachedEquippedSkillCollection.add(equippedSkillCollectionEquippedSkillToAttach);
            }
            character.setEquippedSkillCollection(attachedEquippedSkillCollection);
            Collection<Weaponset> attachedWeaponsetCollection = new ArrayList<Weaponset>();
            for (Weaponset weaponsetCollectionWeaponsetToAttach : character.getWeaponsetCollection()) {
                weaponsetCollectionWeaponsetToAttach = em.getReference(weaponsetCollectionWeaponsetToAttach.getClass(), weaponsetCollectionWeaponsetToAttach.getWeaponsetPK());
                attachedWeaponsetCollection.add(weaponsetCollectionWeaponsetToAttach);
            }
            character.setWeaponsetCollection(attachedWeaponsetCollection);
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : character.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getId());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            character.setItemCollection(attachedItemCollection);
            Collection<Attributepoint> attachedAttributepointCollection = new ArrayList<Attributepoint>();
            for (Attributepoint attributepointCollectionAttributepointToAttach : character.getAttributepointCollection()) {
                attributepointCollectionAttributepointToAttach = em.getReference(attributepointCollectionAttributepointToAttach.getClass(), attributepointCollectionAttributepointToAttach.getAttributepointPK());
                attachedAttributepointCollection.add(attributepointCollectionAttributepointToAttach);
            }
            character.setAttributepointCollection(attachedAttributepointCollection);
            em.persist(character);
            if (secondaryProfession != null) {
                secondaryProfession.getCharacterCollection().add(character);
                secondaryProfession = em.merge(secondaryProfession);
            }
            if (primaryProfession != null) {
                primaryProfession.getCharacterCollection().add(character);
                primaryProfession = em.merge(primaryProfession);
            }
            if (level != null) {
                level.getCharacterCollection().add(character);
                level = em.merge(level);
            }
            if (lastOutpost != null) {
                lastOutpost.getCharacterCollection().add(character);
                lastOutpost = em.merge(lastOutpost);
            }
            if (equipmentPack != null) {
                equipmentPack.getCharacterCollection().add(character);
                equipmentPack = em.merge(equipmentPack);
            }
            if (equipment != null) {
                equipment.getCharacterCollection().add(character);
                equipment = em.merge(equipment);
            }
            if (beltpouch != null) {
                beltpouch.getCharacterCollection().add(character);
                beltpouch = em.merge(beltpouch);
            }
            if (bag2 != null) {
                bag2.getCharacterCollection().add(character);
                bag2 = em.merge(bag2);
            }
            if (bag1 != null) {
                bag1.getCharacterCollection().add(character);
                bag1 = em.merge(bag1);
            }
            if (backpack != null) {
                backpack.getCharacterCollection().add(character);
                backpack = em.merge(backpack);
            }
            if (accountID != null) {
                accountID.getCharacterCollection().add(character);
                accountID = em.merge(accountID);
            }
            for (Skill skillCollectionSkill : character.getSkillCollection()) {
                skillCollectionSkill.getCharacterCollection().add(character);
                skillCollectionSkill = em.merge(skillCollectionSkill);
            }
            for (Profession professionCollectionProfession : character.getProfessionCollection()) {
                professionCollectionProfession.getCharacterCollection().add(character);
                professionCollectionProfession = em.merge(professionCollectionProfession);
            }
            for (EquippedSkill equippedSkillCollectionEquippedSkill : character.getEquippedSkillCollection()) {
                Character oldCharacterOfEquippedSkillCollectionEquippedSkill = equippedSkillCollectionEquippedSkill.getCharacter();
                equippedSkillCollectionEquippedSkill.setCharacter(character);
                equippedSkillCollectionEquippedSkill = em.merge(equippedSkillCollectionEquippedSkill);
                if (oldCharacterOfEquippedSkillCollectionEquippedSkill != null) {
                    oldCharacterOfEquippedSkillCollectionEquippedSkill.getEquippedSkillCollection().remove(equippedSkillCollectionEquippedSkill);
                    oldCharacterOfEquippedSkillCollectionEquippedSkill = em.merge(oldCharacterOfEquippedSkillCollectionEquippedSkill);
                }
            }
            for (Weaponset weaponsetCollectionWeaponset : character.getWeaponsetCollection()) {
                Character oldCharacterOfWeaponsetCollectionWeaponset = weaponsetCollectionWeaponset.getCharacter();
                weaponsetCollectionWeaponset.setCharacter(character);
                weaponsetCollectionWeaponset = em.merge(weaponsetCollectionWeaponset);
                if (oldCharacterOfWeaponsetCollectionWeaponset != null) {
                    oldCharacterOfWeaponsetCollectionWeaponset.getWeaponsetCollection().remove(weaponsetCollectionWeaponset);
                    oldCharacterOfWeaponsetCollectionWeaponset = em.merge(oldCharacterOfWeaponsetCollectionWeaponset);
                }
            }
            for (Item itemCollectionItem : character.getItemCollection()) {
                Character oldCustomizedForOfItemCollectionItem = itemCollectionItem.getCustomizedFor();
                itemCollectionItem.setCustomizedFor(character);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldCustomizedForOfItemCollectionItem != null) {
                    oldCustomizedForOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldCustomizedForOfItemCollectionItem = em.merge(oldCustomizedForOfItemCollectionItem);
                }
            }
            for (Attributepoint attributepointCollectionAttributepoint : character.getAttributepointCollection()) {
                Character oldCharacterOfAttributepointCollectionAttributepoint = attributepointCollectionAttributepoint.getCharacter();
                attributepointCollectionAttributepoint.setCharacter(character);
                attributepointCollectionAttributepoint = em.merge(attributepointCollectionAttributepoint);
                if (oldCharacterOfAttributepointCollectionAttributepoint != null) {
                    oldCharacterOfAttributepointCollectionAttributepoint.getAttributepointCollection().remove(attributepointCollectionAttributepoint);
                    oldCharacterOfAttributepointCollectionAttributepoint = em.merge(oldCharacterOfAttributepointCollectionAttributepoint);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Character character) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Character persistentCharacter = em.find(Character.class, character.getId());
            Profession secondaryProfessionOld = persistentCharacter.getSecondaryProfession();
            Profession secondaryProfessionNew = character.getSecondaryProfession();
            Profession primaryProfessionOld = persistentCharacter.getPrimaryProfession();
            Profession primaryProfessionNew = character.getPrimaryProfession();
            Level levelOld = persistentCharacter.getLevel();
            Level levelNew = character.getLevel();
            Map lastOutpostOld = persistentCharacter.getLastOutpost();
            Map lastOutpostNew = character.getLastOutpost();
            Inventory equipmentPackOld = persistentCharacter.getEquipmentPack();
            Inventory equipmentPackNew = character.getEquipmentPack();
            Inventory equipmentOld = persistentCharacter.getEquipment();
            Inventory equipmentNew = character.getEquipment();
            Inventory beltpouchOld = persistentCharacter.getBeltpouch();
            Inventory beltpouchNew = character.getBeltpouch();
            Inventory bag2Old = persistentCharacter.getBag2();
            Inventory bag2New = character.getBag2();
            Inventory bag1Old = persistentCharacter.getBag1();
            Inventory bag1New = character.getBag1();
            Inventory backpackOld = persistentCharacter.getBackpack();
            Inventory backpackNew = character.getBackpack();
            Account accountIDOld = persistentCharacter.getAccountID();
            Account accountIDNew = character.getAccountID();
            Collection<Skill> skillCollectionOld = persistentCharacter.getSkillCollection();
            Collection<Skill> skillCollectionNew = character.getSkillCollection();
            Collection<Profession> professionCollectionOld = persistentCharacter.getProfessionCollection();
            Collection<Profession> professionCollectionNew = character.getProfessionCollection();
            Collection<EquippedSkill> equippedSkillCollectionOld = persistentCharacter.getEquippedSkillCollection();
            Collection<EquippedSkill> equippedSkillCollectionNew = character.getEquippedSkillCollection();
            Collection<Weaponset> weaponsetCollectionOld = persistentCharacter.getWeaponsetCollection();
            Collection<Weaponset> weaponsetCollectionNew = character.getWeaponsetCollection();
            Collection<Item> itemCollectionOld = persistentCharacter.getItemCollection();
            Collection<Item> itemCollectionNew = character.getItemCollection();
            Collection<Attributepoint> attributepointCollectionOld = persistentCharacter.getAttributepointCollection();
            Collection<Attributepoint> attributepointCollectionNew = character.getAttributepointCollection();
            List<String> illegalOrphanMessages = null;
            for (EquippedSkill equippedSkillCollectionOldEquippedSkill : equippedSkillCollectionOld) {
                if (!equippedSkillCollectionNew.contains(equippedSkillCollectionOldEquippedSkill)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EquippedSkill " + equippedSkillCollectionOldEquippedSkill + " since its character field is not nullable.");
                }
            }
            for (Weaponset weaponsetCollectionOldWeaponset : weaponsetCollectionOld) {
                if (!weaponsetCollectionNew.contains(weaponsetCollectionOldWeaponset)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Weaponset " + weaponsetCollectionOldWeaponset + " since its character field is not nullable.");
                }
            }
            for (Attributepoint attributepointCollectionOldAttributepoint : attributepointCollectionOld) {
                if (!attributepointCollectionNew.contains(attributepointCollectionOldAttributepoint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attributepoint " + attributepointCollectionOldAttributepoint + " since its character field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (secondaryProfessionNew != null) {
                secondaryProfessionNew = em.getReference(secondaryProfessionNew.getClass(), secondaryProfessionNew.getId());
                character.setSecondaryProfession(secondaryProfessionNew);
            }
            if (primaryProfessionNew != null) {
                primaryProfessionNew = em.getReference(primaryProfessionNew.getClass(), primaryProfessionNew.getId());
                character.setPrimaryProfession(primaryProfessionNew);
            }
            if (levelNew != null) {
                levelNew = em.getReference(levelNew.getClass(), levelNew.getLevel());
                character.setLevel(levelNew);
            }
            if (lastOutpostNew != null) {
                lastOutpostNew = em.getReference(lastOutpostNew.getClass(), lastOutpostNew.getId());
                character.setLastOutpost(lastOutpostNew);
            }
            if (equipmentPackNew != null) {
                equipmentPackNew = em.getReference(equipmentPackNew.getClass(), equipmentPackNew.getId());
                character.setEquipmentPack(equipmentPackNew);
            }
            if (equipmentNew != null) {
                equipmentNew = em.getReference(equipmentNew.getClass(), equipmentNew.getId());
                character.setEquipment(equipmentNew);
            }
            if (beltpouchNew != null) {
                beltpouchNew = em.getReference(beltpouchNew.getClass(), beltpouchNew.getId());
                character.setBeltpouch(beltpouchNew);
            }
            if (bag2New != null) {
                bag2New = em.getReference(bag2New.getClass(), bag2New.getId());
                character.setBag2(bag2New);
            }
            if (bag1New != null) {
                bag1New = em.getReference(bag1New.getClass(), bag1New.getId());
                character.setBag1(bag1New);
            }
            if (backpackNew != null) {
                backpackNew = em.getReference(backpackNew.getClass(), backpackNew.getId());
                character.setBackpack(backpackNew);
            }
            if (accountIDNew != null) {
                accountIDNew = em.getReference(accountIDNew.getClass(), accountIDNew.getEMail());
                character.setAccountID(accountIDNew);
            }
            Collection<Skill> attachedSkillCollectionNew = new ArrayList<Skill>();
            for (Skill skillCollectionNewSkillToAttach : skillCollectionNew) {
                skillCollectionNewSkillToAttach = em.getReference(skillCollectionNewSkillToAttach.getClass(), skillCollectionNewSkillToAttach.getId());
                attachedSkillCollectionNew.add(skillCollectionNewSkillToAttach);
            }
            skillCollectionNew = attachedSkillCollectionNew;
            character.setSkillCollection(skillCollectionNew);
            Collection<Profession> attachedProfessionCollectionNew = new ArrayList<Profession>();
            for (Profession professionCollectionNewProfessionToAttach : professionCollectionNew) {
                professionCollectionNewProfessionToAttach = em.getReference(professionCollectionNewProfessionToAttach.getClass(), professionCollectionNewProfessionToAttach.getId());
                attachedProfessionCollectionNew.add(professionCollectionNewProfessionToAttach);
            }
            professionCollectionNew = attachedProfessionCollectionNew;
            character.setProfessionCollection(professionCollectionNew);
            Collection<EquippedSkill> attachedEquippedSkillCollectionNew = new ArrayList<EquippedSkill>();
            for (EquippedSkill equippedSkillCollectionNewEquippedSkillToAttach : equippedSkillCollectionNew) {
                equippedSkillCollectionNewEquippedSkillToAttach = em.getReference(equippedSkillCollectionNewEquippedSkillToAttach.getClass(), equippedSkillCollectionNewEquippedSkillToAttach.getEquippedSkillPK());
                attachedEquippedSkillCollectionNew.add(equippedSkillCollectionNewEquippedSkillToAttach);
            }
            equippedSkillCollectionNew = attachedEquippedSkillCollectionNew;
            character.setEquippedSkillCollection(equippedSkillCollectionNew);
            Collection<Weaponset> attachedWeaponsetCollectionNew = new ArrayList<Weaponset>();
            for (Weaponset weaponsetCollectionNewWeaponsetToAttach : weaponsetCollectionNew) {
                weaponsetCollectionNewWeaponsetToAttach = em.getReference(weaponsetCollectionNewWeaponsetToAttach.getClass(), weaponsetCollectionNewWeaponsetToAttach.getWeaponsetPK());
                attachedWeaponsetCollectionNew.add(weaponsetCollectionNewWeaponsetToAttach);
            }
            weaponsetCollectionNew = attachedWeaponsetCollectionNew;
            character.setWeaponsetCollection(weaponsetCollectionNew);
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getId());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            character.setItemCollection(itemCollectionNew);
            Collection<Attributepoint> attachedAttributepointCollectionNew = new ArrayList<Attributepoint>();
            for (Attributepoint attributepointCollectionNewAttributepointToAttach : attributepointCollectionNew) {
                attributepointCollectionNewAttributepointToAttach = em.getReference(attributepointCollectionNewAttributepointToAttach.getClass(), attributepointCollectionNewAttributepointToAttach.getAttributepointPK());
                attachedAttributepointCollectionNew.add(attributepointCollectionNewAttributepointToAttach);
            }
            attributepointCollectionNew = attachedAttributepointCollectionNew;
            character.setAttributepointCollection(attributepointCollectionNew);
            character = em.merge(character);
            if (secondaryProfessionOld != null && !secondaryProfessionOld.equals(secondaryProfessionNew)) {
                secondaryProfessionOld.getCharacterCollection().remove(character);
                secondaryProfessionOld = em.merge(secondaryProfessionOld);
            }
            if (secondaryProfessionNew != null && !secondaryProfessionNew.equals(secondaryProfessionOld)) {
                secondaryProfessionNew.getCharacterCollection().add(character);
                secondaryProfessionNew = em.merge(secondaryProfessionNew);
            }
            if (primaryProfessionOld != null && !primaryProfessionOld.equals(primaryProfessionNew)) {
                primaryProfessionOld.getCharacterCollection().remove(character);
                primaryProfessionOld = em.merge(primaryProfessionOld);
            }
            if (primaryProfessionNew != null && !primaryProfessionNew.equals(primaryProfessionOld)) {
                primaryProfessionNew.getCharacterCollection().add(character);
                primaryProfessionNew = em.merge(primaryProfessionNew);
            }
            if (levelOld != null && !levelOld.equals(levelNew)) {
                levelOld.getCharacterCollection().remove(character);
                levelOld = em.merge(levelOld);
            }
            if (levelNew != null && !levelNew.equals(levelOld)) {
                levelNew.getCharacterCollection().add(character);
                levelNew = em.merge(levelNew);
            }
            if (lastOutpostOld != null && !lastOutpostOld.equals(lastOutpostNew)) {
                lastOutpostOld.getCharacterCollection().remove(character);
                lastOutpostOld = em.merge(lastOutpostOld);
            }
            if (lastOutpostNew != null && !lastOutpostNew.equals(lastOutpostOld)) {
                lastOutpostNew.getCharacterCollection().add(character);
                lastOutpostNew = em.merge(lastOutpostNew);
            }
            if (equipmentPackOld != null && !equipmentPackOld.equals(equipmentPackNew)) {
                equipmentPackOld.getCharacterCollection().remove(character);
                equipmentPackOld = em.merge(equipmentPackOld);
            }
            if (equipmentPackNew != null && !equipmentPackNew.equals(equipmentPackOld)) {
                equipmentPackNew.getCharacterCollection().add(character);
                equipmentPackNew = em.merge(equipmentPackNew);
            }
            if (equipmentOld != null && !equipmentOld.equals(equipmentNew)) {
                equipmentOld.getCharacterCollection().remove(character);
                equipmentOld = em.merge(equipmentOld);
            }
            if (equipmentNew != null && !equipmentNew.equals(equipmentOld)) {
                equipmentNew.getCharacterCollection().add(character);
                equipmentNew = em.merge(equipmentNew);
            }
            if (beltpouchOld != null && !beltpouchOld.equals(beltpouchNew)) {
                beltpouchOld.getCharacterCollection().remove(character);
                beltpouchOld = em.merge(beltpouchOld);
            }
            if (beltpouchNew != null && !beltpouchNew.equals(beltpouchOld)) {
                beltpouchNew.getCharacterCollection().add(character);
                beltpouchNew = em.merge(beltpouchNew);
            }
            if (bag2Old != null && !bag2Old.equals(bag2New)) {
                bag2Old.getCharacterCollection().remove(character);
                bag2Old = em.merge(bag2Old);
            }
            if (bag2New != null && !bag2New.equals(bag2Old)) {
                bag2New.getCharacterCollection().add(character);
                bag2New = em.merge(bag2New);
            }
            if (bag1Old != null && !bag1Old.equals(bag1New)) {
                bag1Old.getCharacterCollection().remove(character);
                bag1Old = em.merge(bag1Old);
            }
            if (bag1New != null && !bag1New.equals(bag1Old)) {
                bag1New.getCharacterCollection().add(character);
                bag1New = em.merge(bag1New);
            }
            if (backpackOld != null && !backpackOld.equals(backpackNew)) {
                backpackOld.getCharacterCollection().remove(character);
                backpackOld = em.merge(backpackOld);
            }
            if (backpackNew != null && !backpackNew.equals(backpackOld)) {
                backpackNew.getCharacterCollection().add(character);
                backpackNew = em.merge(backpackNew);
            }
            if (accountIDOld != null && !accountIDOld.equals(accountIDNew)) {
                accountIDOld.getCharacterCollection().remove(character);
                accountIDOld = em.merge(accountIDOld);
            }
            if (accountIDNew != null && !accountIDNew.equals(accountIDOld)) {
                accountIDNew.getCharacterCollection().add(character);
                accountIDNew = em.merge(accountIDNew);
            }
            for (Skill skillCollectionOldSkill : skillCollectionOld) {
                if (!skillCollectionNew.contains(skillCollectionOldSkill)) {
                    skillCollectionOldSkill.getCharacterCollection().remove(character);
                    skillCollectionOldSkill = em.merge(skillCollectionOldSkill);
                }
            }
            for (Skill skillCollectionNewSkill : skillCollectionNew) {
                if (!skillCollectionOld.contains(skillCollectionNewSkill)) {
                    skillCollectionNewSkill.getCharacterCollection().add(character);
                    skillCollectionNewSkill = em.merge(skillCollectionNewSkill);
                }
            }
            for (Profession professionCollectionOldProfession : professionCollectionOld) {
                if (!professionCollectionNew.contains(professionCollectionOldProfession)) {
                    professionCollectionOldProfession.getCharacterCollection().remove(character);
                    professionCollectionOldProfession = em.merge(professionCollectionOldProfession);
                }
            }
            for (Profession professionCollectionNewProfession : professionCollectionNew) {
                if (!professionCollectionOld.contains(professionCollectionNewProfession)) {
                    professionCollectionNewProfession.getCharacterCollection().add(character);
                    professionCollectionNewProfession = em.merge(professionCollectionNewProfession);
                }
            }
            for (EquippedSkill equippedSkillCollectionNewEquippedSkill : equippedSkillCollectionNew) {
                if (!equippedSkillCollectionOld.contains(equippedSkillCollectionNewEquippedSkill)) {
                    Character oldCharacterOfEquippedSkillCollectionNewEquippedSkill = equippedSkillCollectionNewEquippedSkill.getCharacter();
                    equippedSkillCollectionNewEquippedSkill.setCharacter(character);
                    equippedSkillCollectionNewEquippedSkill = em.merge(equippedSkillCollectionNewEquippedSkill);
                    if (oldCharacterOfEquippedSkillCollectionNewEquippedSkill != null && !oldCharacterOfEquippedSkillCollectionNewEquippedSkill.equals(character)) {
                        oldCharacterOfEquippedSkillCollectionNewEquippedSkill.getEquippedSkillCollection().remove(equippedSkillCollectionNewEquippedSkill);
                        oldCharacterOfEquippedSkillCollectionNewEquippedSkill = em.merge(oldCharacterOfEquippedSkillCollectionNewEquippedSkill);
                    }
                }
            }
            for (Weaponset weaponsetCollectionNewWeaponset : weaponsetCollectionNew) {
                if (!weaponsetCollectionOld.contains(weaponsetCollectionNewWeaponset)) {
                    Character oldCharacterOfWeaponsetCollectionNewWeaponset = weaponsetCollectionNewWeaponset.getCharacter();
                    weaponsetCollectionNewWeaponset.setCharacter(character);
                    weaponsetCollectionNewWeaponset = em.merge(weaponsetCollectionNewWeaponset);
                    if (oldCharacterOfWeaponsetCollectionNewWeaponset != null && !oldCharacterOfWeaponsetCollectionNewWeaponset.equals(character)) {
                        oldCharacterOfWeaponsetCollectionNewWeaponset.getWeaponsetCollection().remove(weaponsetCollectionNewWeaponset);
                        oldCharacterOfWeaponsetCollectionNewWeaponset = em.merge(oldCharacterOfWeaponsetCollectionNewWeaponset);
                    }
                }
            }
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    itemCollectionOldItem.setCustomizedFor(null);
                    itemCollectionOldItem = em.merge(itemCollectionOldItem);
                }
            }
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    Character oldCustomizedForOfItemCollectionNewItem = itemCollectionNewItem.getCustomizedFor();
                    itemCollectionNewItem.setCustomizedFor(character);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldCustomizedForOfItemCollectionNewItem != null && !oldCustomizedForOfItemCollectionNewItem.equals(character)) {
                        oldCustomizedForOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldCustomizedForOfItemCollectionNewItem = em.merge(oldCustomizedForOfItemCollectionNewItem);
                    }
                }
            }
            for (Attributepoint attributepointCollectionNewAttributepoint : attributepointCollectionNew) {
                if (!attributepointCollectionOld.contains(attributepointCollectionNewAttributepoint)) {
                    Character oldCharacterOfAttributepointCollectionNewAttributepoint = attributepointCollectionNewAttributepoint.getCharacter();
                    attributepointCollectionNewAttributepoint.setCharacter(character);
                    attributepointCollectionNewAttributepoint = em.merge(attributepointCollectionNewAttributepoint);
                    if (oldCharacterOfAttributepointCollectionNewAttributepoint != null && !oldCharacterOfAttributepointCollectionNewAttributepoint.equals(character)) {
                        oldCharacterOfAttributepointCollectionNewAttributepoint.getAttributepointCollection().remove(attributepointCollectionNewAttributepoint);
                        oldCharacterOfAttributepointCollectionNewAttributepoint = em.merge(oldCharacterOfAttributepointCollectionNewAttributepoint);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = character.getId();
                if (findCharacter(id) == null) {
                    throw new NonexistentEntityException("The character with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Character character;
            try {
                character = em.getReference(Character.class, id);
                character.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The character with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EquippedSkill> equippedSkillCollectionOrphanCheck = character.getEquippedSkillCollection();
            for (EquippedSkill equippedSkillCollectionOrphanCheckEquippedSkill : equippedSkillCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Character (" + character + ") cannot be destroyed since the EquippedSkill " + equippedSkillCollectionOrphanCheckEquippedSkill + " in its equippedSkillCollection field has a non-nullable character field.");
            }
            Collection<Weaponset> weaponsetCollectionOrphanCheck = character.getWeaponsetCollection();
            for (Weaponset weaponsetCollectionOrphanCheckWeaponset : weaponsetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Character (" + character + ") cannot be destroyed since the Weaponset " + weaponsetCollectionOrphanCheckWeaponset + " in its weaponsetCollection field has a non-nullable character field.");
            }
            Collection<Attributepoint> attributepointCollectionOrphanCheck = character.getAttributepointCollection();
            for (Attributepoint attributepointCollectionOrphanCheckAttributepoint : attributepointCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Character (" + character + ") cannot be destroyed since the Attributepoint " + attributepointCollectionOrphanCheckAttributepoint + " in its attributepointCollection field has a non-nullable character field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profession secondaryProfession = character.getSecondaryProfession();
            if (secondaryProfession != null) {
                secondaryProfession.getCharacterCollection().remove(character);
                secondaryProfession = em.merge(secondaryProfession);
            }
            Profession primaryProfession = character.getPrimaryProfession();
            if (primaryProfession != null) {
                primaryProfession.getCharacterCollection().remove(character);
                primaryProfession = em.merge(primaryProfession);
            }
            Level level = character.getLevel();
            if (level != null) {
                level.getCharacterCollection().remove(character);
                level = em.merge(level);
            }
            Map lastOutpost = character.getLastOutpost();
            if (lastOutpost != null) {
                lastOutpost.getCharacterCollection().remove(character);
                lastOutpost = em.merge(lastOutpost);
            }
            Inventory equipmentPack = character.getEquipmentPack();
            if (equipmentPack != null) {
                equipmentPack.getCharacterCollection().remove(character);
                equipmentPack = em.merge(equipmentPack);
            }
            Inventory equipment = character.getEquipment();
            if (equipment != null) {
                equipment.getCharacterCollection().remove(character);
                equipment = em.merge(equipment);
            }
            Inventory beltpouch = character.getBeltpouch();
            if (beltpouch != null) {
                beltpouch.getCharacterCollection().remove(character);
                beltpouch = em.merge(beltpouch);
            }
            Inventory bag2 = character.getBag2();
            if (bag2 != null) {
                bag2.getCharacterCollection().remove(character);
                bag2 = em.merge(bag2);
            }
            Inventory bag1 = character.getBag1();
            if (bag1 != null) {
                bag1.getCharacterCollection().remove(character);
                bag1 = em.merge(bag1);
            }
            Inventory backpack = character.getBackpack();
            if (backpack != null) {
                backpack.getCharacterCollection().remove(character);
                backpack = em.merge(backpack);
            }
            Account accountID = character.getAccountID();
            if (accountID != null) {
                accountID.getCharacterCollection().remove(character);
                accountID = em.merge(accountID);
            }
            Collection<Skill> skillCollection = character.getSkillCollection();
            for (Skill skillCollectionSkill : skillCollection) {
                skillCollectionSkill.getCharacterCollection().remove(character);
                skillCollectionSkill = em.merge(skillCollectionSkill);
            }
            Collection<Profession> professionCollection = character.getProfessionCollection();
            for (Profession professionCollectionProfession : professionCollection) {
                professionCollectionProfession.getCharacterCollection().remove(character);
                professionCollectionProfession = em.merge(professionCollectionProfession);
            }
            Collection<Item> itemCollection = character.getItemCollection();
            for (Item itemCollectionItem : itemCollection) {
                itemCollectionItem.setCustomizedFor(null);
                itemCollectionItem = em.merge(itemCollectionItem);
            }
            em.remove(character);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Character> findCharacterEntities() {
        return findCharacterEntities(true, -1, -1);
    }

    public List<Character> findCharacterEntities(int maxResults, int firstResult) {
        return findCharacterEntities(false, maxResults, firstResult);
    }

    private List<Character> findCharacterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Character.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Character findCharacter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Character.class, id);
        } finally {
            em.close();
        }
    }

    public int getCharacterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Character> rt = cq.from(Character.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
