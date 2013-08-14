/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Account;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Storagetab;
import gwlpr.database.entities.Storeditem;
import gwlpr.database.entities.Character;
import gwlpr.database.entities.Inventory;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class InventoryJpaController implements Serializable 
{

    public InventoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventory inventory) {
        if (inventory.getAccountCollection() == null) {
            inventory.setAccountCollection(new ArrayList<Account>());
        }
        if (inventory.getStoragetabCollection() == null) {
            inventory.setStoragetabCollection(new ArrayList<Storagetab>());
        }
        if (inventory.getStoreditemCollection() == null) {
            inventory.setStoreditemCollection(new ArrayList<Storeditem>());
        }
        if (inventory.getCharacterCollection() == null) {
            inventory.setCharacterCollection(new ArrayList<Character>());
        }
        if (inventory.getCharacterCollection1() == null) {
            inventory.setCharacterCollection1(new ArrayList<Character>());
        }
        if (inventory.getCharacterCollection2() == null) {
            inventory.setCharacterCollection2(new ArrayList<Character>());
        }
        if (inventory.getCharacterCollection3() == null) {
            inventory.setCharacterCollection3(new ArrayList<Character>());
        }
        if (inventory.getCharacterCollection4() == null) {
            inventory.setCharacterCollection4(new ArrayList<Character>());
        }
        if (inventory.getCharacterCollection5() == null) {
            inventory.setCharacterCollection5(new ArrayList<Character>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Account> attachedAccountCollection = new ArrayList<Account>();
            for (Account accountCollectionAccountToAttach : inventory.getAccountCollection()) {
                accountCollectionAccountToAttach = em.getReference(accountCollectionAccountToAttach.getClass(), accountCollectionAccountToAttach.getEMail());
                attachedAccountCollection.add(accountCollectionAccountToAttach);
            }
            inventory.setAccountCollection(attachedAccountCollection);
            Collection<Storagetab> attachedStoragetabCollection = new ArrayList<Storagetab>();
            for (Storagetab storagetabCollectionStoragetabToAttach : inventory.getStoragetabCollection()) {
                storagetabCollectionStoragetabToAttach = em.getReference(storagetabCollectionStoragetabToAttach.getClass(), storagetabCollectionStoragetabToAttach.getStoragetabPK());
                attachedStoragetabCollection.add(storagetabCollectionStoragetabToAttach);
            }
            inventory.setStoragetabCollection(attachedStoragetabCollection);
            Collection<Storeditem> attachedStoreditemCollection = new ArrayList<Storeditem>();
            for (Storeditem storeditemCollectionStoreditemToAttach : inventory.getStoreditemCollection()) {
                storeditemCollectionStoreditemToAttach = em.getReference(storeditemCollectionStoreditemToAttach.getClass(), storeditemCollectionStoreditemToAttach.getStoreditemPK());
                attachedStoreditemCollection.add(storeditemCollectionStoreditemToAttach);
            }
            inventory.setStoreditemCollection(attachedStoreditemCollection);
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : inventory.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            inventory.setCharacterCollection(attachedCharacterCollection);
            Collection<Character> attachedCharacterCollection1 = new ArrayList<Character>();
            for (Character characterCollection1CharacterToAttach : inventory.getCharacterCollection1()) {
                characterCollection1CharacterToAttach = em.getReference(characterCollection1CharacterToAttach.getClass(), characterCollection1CharacterToAttach.getId());
                attachedCharacterCollection1.add(characterCollection1CharacterToAttach);
            }
            inventory.setCharacterCollection1(attachedCharacterCollection1);
            Collection<Character> attachedCharacterCollection2 = new ArrayList<Character>();
            for (Character characterCollection2CharacterToAttach : inventory.getCharacterCollection2()) {
                characterCollection2CharacterToAttach = em.getReference(characterCollection2CharacterToAttach.getClass(), characterCollection2CharacterToAttach.getId());
                attachedCharacterCollection2.add(characterCollection2CharacterToAttach);
            }
            inventory.setCharacterCollection2(attachedCharacterCollection2);
            Collection<Character> attachedCharacterCollection3 = new ArrayList<Character>();
            for (Character characterCollection3CharacterToAttach : inventory.getCharacterCollection3()) {
                characterCollection3CharacterToAttach = em.getReference(characterCollection3CharacterToAttach.getClass(), characterCollection3CharacterToAttach.getId());
                attachedCharacterCollection3.add(characterCollection3CharacterToAttach);
            }
            inventory.setCharacterCollection3(attachedCharacterCollection3);
            Collection<Character> attachedCharacterCollection4 = new ArrayList<Character>();
            for (Character characterCollection4CharacterToAttach : inventory.getCharacterCollection4()) {
                characterCollection4CharacterToAttach = em.getReference(characterCollection4CharacterToAttach.getClass(), characterCollection4CharacterToAttach.getId());
                attachedCharacterCollection4.add(characterCollection4CharacterToAttach);
            }
            inventory.setCharacterCollection4(attachedCharacterCollection4);
            Collection<Character> attachedCharacterCollection5 = new ArrayList<Character>();
            for (Character characterCollection5CharacterToAttach : inventory.getCharacterCollection5()) {
                characterCollection5CharacterToAttach = em.getReference(characterCollection5CharacterToAttach.getClass(), characterCollection5CharacterToAttach.getId());
                attachedCharacterCollection5.add(characterCollection5CharacterToAttach);
            }
            inventory.setCharacterCollection5(attachedCharacterCollection5);
            em.persist(inventory);
            for (Account accountCollectionAccount : inventory.getAccountCollection()) {
                Inventory oldMaterialStorageOfAccountCollectionAccount = accountCollectionAccount.getMaterialStorage();
                accountCollectionAccount.setMaterialStorage(inventory);
                accountCollectionAccount = em.merge(accountCollectionAccount);
                if (oldMaterialStorageOfAccountCollectionAccount != null) {
                    oldMaterialStorageOfAccountCollectionAccount.getAccountCollection().remove(accountCollectionAccount);
                    oldMaterialStorageOfAccountCollectionAccount = em.merge(oldMaterialStorageOfAccountCollectionAccount);
                }
            }
            for (Storagetab storagetabCollectionStoragetab : inventory.getStoragetabCollection()) {
                Inventory oldInventoryIDOfStoragetabCollectionStoragetab = storagetabCollectionStoragetab.getInventoryID();
                storagetabCollectionStoragetab.setInventoryID(inventory);
                storagetabCollectionStoragetab = em.merge(storagetabCollectionStoragetab);
                if (oldInventoryIDOfStoragetabCollectionStoragetab != null) {
                    oldInventoryIDOfStoragetabCollectionStoragetab.getStoragetabCollection().remove(storagetabCollectionStoragetab);
                    oldInventoryIDOfStoragetabCollectionStoragetab = em.merge(oldInventoryIDOfStoragetabCollectionStoragetab);
                }
            }
            for (Storeditem storeditemCollectionStoreditem : inventory.getStoreditemCollection()) {
                Inventory oldInventoryOfStoreditemCollectionStoreditem = storeditemCollectionStoreditem.getInventory();
                storeditemCollectionStoreditem.setInventory(inventory);
                storeditemCollectionStoreditem = em.merge(storeditemCollectionStoreditem);
                if (oldInventoryOfStoreditemCollectionStoreditem != null) {
                    oldInventoryOfStoreditemCollectionStoreditem.getStoreditemCollection().remove(storeditemCollectionStoreditem);
                    oldInventoryOfStoreditemCollectionStoreditem = em.merge(oldInventoryOfStoreditemCollectionStoreditem);
                }
            }
            for (Character characterCollectionCharacter : inventory.getCharacterCollection()) {
                Inventory oldEquipmentPackOfCharacterCollectionCharacter = characterCollectionCharacter.getEquipmentPack();
                characterCollectionCharacter.setEquipmentPack(inventory);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
                if (oldEquipmentPackOfCharacterCollectionCharacter != null) {
                    oldEquipmentPackOfCharacterCollectionCharacter.getCharacterCollection().remove(characterCollectionCharacter);
                    oldEquipmentPackOfCharacterCollectionCharacter = em.merge(oldEquipmentPackOfCharacterCollectionCharacter);
                }
            }
            for (Character characterCollection1Character : inventory.getCharacterCollection1()) {
                Inventory oldEquipmentOfCharacterCollection1Character = characterCollection1Character.getEquipment();
                characterCollection1Character.setEquipment(inventory);
                characterCollection1Character = em.merge(characterCollection1Character);
                if (oldEquipmentOfCharacterCollection1Character != null) {
                    oldEquipmentOfCharacterCollection1Character.getCharacterCollection1().remove(characterCollection1Character);
                    oldEquipmentOfCharacterCollection1Character = em.merge(oldEquipmentOfCharacterCollection1Character);
                }
            }
            for (Character characterCollection2Character : inventory.getCharacterCollection2()) {
                Inventory oldBeltpouchOfCharacterCollection2Character = characterCollection2Character.getBeltpouch();
                characterCollection2Character.setBeltpouch(inventory);
                characterCollection2Character = em.merge(characterCollection2Character);
                if (oldBeltpouchOfCharacterCollection2Character != null) {
                    oldBeltpouchOfCharacterCollection2Character.getCharacterCollection2().remove(characterCollection2Character);
                    oldBeltpouchOfCharacterCollection2Character = em.merge(oldBeltpouchOfCharacterCollection2Character);
                }
            }
            for (Character characterCollection3Character : inventory.getCharacterCollection3()) {
                Inventory oldBag2OfCharacterCollection3Character = characterCollection3Character.getBag2();
                characterCollection3Character.setBag2(inventory);
                characterCollection3Character = em.merge(characterCollection3Character);
                if (oldBag2OfCharacterCollection3Character != null) {
                    oldBag2OfCharacterCollection3Character.getCharacterCollection3().remove(characterCollection3Character);
                    oldBag2OfCharacterCollection3Character = em.merge(oldBag2OfCharacterCollection3Character);
                }
            }
            for (Character characterCollection4Character : inventory.getCharacterCollection4()) {
                Inventory oldBag1OfCharacterCollection4Character = characterCollection4Character.getBag1();
                characterCollection4Character.setBag1(inventory);
                characterCollection4Character = em.merge(characterCollection4Character);
                if (oldBag1OfCharacterCollection4Character != null) {
                    oldBag1OfCharacterCollection4Character.getCharacterCollection4().remove(characterCollection4Character);
                    oldBag1OfCharacterCollection4Character = em.merge(oldBag1OfCharacterCollection4Character);
                }
            }
            for (Character characterCollection5Character : inventory.getCharacterCollection5()) {
                Inventory oldBackpackOfCharacterCollection5Character = characterCollection5Character.getBackpack();
                characterCollection5Character.setBackpack(inventory);
                characterCollection5Character = em.merge(characterCollection5Character);
                if (oldBackpackOfCharacterCollection5Character != null) {
                    oldBackpackOfCharacterCollection5Character.getCharacterCollection5().remove(characterCollection5Character);
                    oldBackpackOfCharacterCollection5Character = em.merge(oldBackpackOfCharacterCollection5Character);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventory inventory) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventory persistentInventory = em.find(Inventory.class, inventory.getId());
            Collection<Account> accountCollectionOld = persistentInventory.getAccountCollection();
            Collection<Account> accountCollectionNew = inventory.getAccountCollection();
            Collection<Storagetab> storagetabCollectionOld = persistentInventory.getStoragetabCollection();
            Collection<Storagetab> storagetabCollectionNew = inventory.getStoragetabCollection();
            Collection<Storeditem> storeditemCollectionOld = persistentInventory.getStoreditemCollection();
            Collection<Storeditem> storeditemCollectionNew = inventory.getStoreditemCollection();
            Collection<Character> characterCollectionOld = persistentInventory.getCharacterCollection();
            Collection<Character> characterCollectionNew = inventory.getCharacterCollection();
            Collection<Character> characterCollection1Old = persistentInventory.getCharacterCollection1();
            Collection<Character> characterCollection1New = inventory.getCharacterCollection1();
            Collection<Character> characterCollection2Old = persistentInventory.getCharacterCollection2();
            Collection<Character> characterCollection2New = inventory.getCharacterCollection2();
            Collection<Character> characterCollection3Old = persistentInventory.getCharacterCollection3();
            Collection<Character> characterCollection3New = inventory.getCharacterCollection3();
            Collection<Character> characterCollection4Old = persistentInventory.getCharacterCollection4();
            Collection<Character> characterCollection4New = inventory.getCharacterCollection4();
            Collection<Character> characterCollection5Old = persistentInventory.getCharacterCollection5();
            Collection<Character> characterCollection5New = inventory.getCharacterCollection5();
            List<String> illegalOrphanMessages = null;
            for (Storagetab storagetabCollectionOldStoragetab : storagetabCollectionOld) {
                if (!storagetabCollectionNew.contains(storagetabCollectionOldStoragetab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Storagetab " + storagetabCollectionOldStoragetab + " since its inventoryID field is not nullable.");
                }
            }
            for (Storeditem storeditemCollectionOldStoreditem : storeditemCollectionOld) {
                if (!storeditemCollectionNew.contains(storeditemCollectionOldStoreditem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Storeditem " + storeditemCollectionOldStoreditem + " since its inventory field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Account> attachedAccountCollectionNew = new ArrayList<Account>();
            for (Account accountCollectionNewAccountToAttach : accountCollectionNew) {
                accountCollectionNewAccountToAttach = em.getReference(accountCollectionNewAccountToAttach.getClass(), accountCollectionNewAccountToAttach.getEMail());
                attachedAccountCollectionNew.add(accountCollectionNewAccountToAttach);
            }
            accountCollectionNew = attachedAccountCollectionNew;
            inventory.setAccountCollection(accountCollectionNew);
            Collection<Storagetab> attachedStoragetabCollectionNew = new ArrayList<Storagetab>();
            for (Storagetab storagetabCollectionNewStoragetabToAttach : storagetabCollectionNew) {
                storagetabCollectionNewStoragetabToAttach = em.getReference(storagetabCollectionNewStoragetabToAttach.getClass(), storagetabCollectionNewStoragetabToAttach.getStoragetabPK());
                attachedStoragetabCollectionNew.add(storagetabCollectionNewStoragetabToAttach);
            }
            storagetabCollectionNew = attachedStoragetabCollectionNew;
            inventory.setStoragetabCollection(storagetabCollectionNew);
            Collection<Storeditem> attachedStoreditemCollectionNew = new ArrayList<Storeditem>();
            for (Storeditem storeditemCollectionNewStoreditemToAttach : storeditemCollectionNew) {
                storeditemCollectionNewStoreditemToAttach = em.getReference(storeditemCollectionNewStoreditemToAttach.getClass(), storeditemCollectionNewStoreditemToAttach.getStoreditemPK());
                attachedStoreditemCollectionNew.add(storeditemCollectionNewStoreditemToAttach);
            }
            storeditemCollectionNew = attachedStoreditemCollectionNew;
            inventory.setStoreditemCollection(storeditemCollectionNew);
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            inventory.setCharacterCollection(characterCollectionNew);
            Collection<Character> attachedCharacterCollection1New = new ArrayList<Character>();
            for (Character characterCollection1NewCharacterToAttach : characterCollection1New) {
                characterCollection1NewCharacterToAttach = em.getReference(characterCollection1NewCharacterToAttach.getClass(), characterCollection1NewCharacterToAttach.getId());
                attachedCharacterCollection1New.add(characterCollection1NewCharacterToAttach);
            }
            characterCollection1New = attachedCharacterCollection1New;
            inventory.setCharacterCollection1(characterCollection1New);
            Collection<Character> attachedCharacterCollection2New = new ArrayList<Character>();
            for (Character characterCollection2NewCharacterToAttach : characterCollection2New) {
                characterCollection2NewCharacterToAttach = em.getReference(characterCollection2NewCharacterToAttach.getClass(), characterCollection2NewCharacterToAttach.getId());
                attachedCharacterCollection2New.add(characterCollection2NewCharacterToAttach);
            }
            characterCollection2New = attachedCharacterCollection2New;
            inventory.setCharacterCollection2(characterCollection2New);
            Collection<Character> attachedCharacterCollection3New = new ArrayList<Character>();
            for (Character characterCollection3NewCharacterToAttach : characterCollection3New) {
                characterCollection3NewCharacterToAttach = em.getReference(characterCollection3NewCharacterToAttach.getClass(), characterCollection3NewCharacterToAttach.getId());
                attachedCharacterCollection3New.add(characterCollection3NewCharacterToAttach);
            }
            characterCollection3New = attachedCharacterCollection3New;
            inventory.setCharacterCollection3(characterCollection3New);
            Collection<Character> attachedCharacterCollection4New = new ArrayList<Character>();
            for (Character characterCollection4NewCharacterToAttach : characterCollection4New) {
                characterCollection4NewCharacterToAttach = em.getReference(characterCollection4NewCharacterToAttach.getClass(), characterCollection4NewCharacterToAttach.getId());
                attachedCharacterCollection4New.add(characterCollection4NewCharacterToAttach);
            }
            characterCollection4New = attachedCharacterCollection4New;
            inventory.setCharacterCollection4(characterCollection4New);
            Collection<Character> attachedCharacterCollection5New = new ArrayList<Character>();
            for (Character characterCollection5NewCharacterToAttach : characterCollection5New) {
                characterCollection5NewCharacterToAttach = em.getReference(characterCollection5NewCharacterToAttach.getClass(), characterCollection5NewCharacterToAttach.getId());
                attachedCharacterCollection5New.add(characterCollection5NewCharacterToAttach);
            }
            characterCollection5New = attachedCharacterCollection5New;
            inventory.setCharacterCollection5(characterCollection5New);
            inventory = em.merge(inventory);
            for (Account accountCollectionOldAccount : accountCollectionOld) {
                if (!accountCollectionNew.contains(accountCollectionOldAccount)) {
                    accountCollectionOldAccount.setMaterialStorage(null);
                    accountCollectionOldAccount = em.merge(accountCollectionOldAccount);
                }
            }
            for (Account accountCollectionNewAccount : accountCollectionNew) {
                if (!accountCollectionOld.contains(accountCollectionNewAccount)) {
                    Inventory oldMaterialStorageOfAccountCollectionNewAccount = accountCollectionNewAccount.getMaterialStorage();
                    accountCollectionNewAccount.setMaterialStorage(inventory);
                    accountCollectionNewAccount = em.merge(accountCollectionNewAccount);
                    if (oldMaterialStorageOfAccountCollectionNewAccount != null && !oldMaterialStorageOfAccountCollectionNewAccount.equals(inventory)) {
                        oldMaterialStorageOfAccountCollectionNewAccount.getAccountCollection().remove(accountCollectionNewAccount);
                        oldMaterialStorageOfAccountCollectionNewAccount = em.merge(oldMaterialStorageOfAccountCollectionNewAccount);
                    }
                }
            }
            for (Storagetab storagetabCollectionNewStoragetab : storagetabCollectionNew) {
                if (!storagetabCollectionOld.contains(storagetabCollectionNewStoragetab)) {
                    Inventory oldInventoryIDOfStoragetabCollectionNewStoragetab = storagetabCollectionNewStoragetab.getInventoryID();
                    storagetabCollectionNewStoragetab.setInventoryID(inventory);
                    storagetabCollectionNewStoragetab = em.merge(storagetabCollectionNewStoragetab);
                    if (oldInventoryIDOfStoragetabCollectionNewStoragetab != null && !oldInventoryIDOfStoragetabCollectionNewStoragetab.equals(inventory)) {
                        oldInventoryIDOfStoragetabCollectionNewStoragetab.getStoragetabCollection().remove(storagetabCollectionNewStoragetab);
                        oldInventoryIDOfStoragetabCollectionNewStoragetab = em.merge(oldInventoryIDOfStoragetabCollectionNewStoragetab);
                    }
                }
            }
            for (Storeditem storeditemCollectionNewStoreditem : storeditemCollectionNew) {
                if (!storeditemCollectionOld.contains(storeditemCollectionNewStoreditem)) {
                    Inventory oldInventoryOfStoreditemCollectionNewStoreditem = storeditemCollectionNewStoreditem.getInventory();
                    storeditemCollectionNewStoreditem.setInventory(inventory);
                    storeditemCollectionNewStoreditem = em.merge(storeditemCollectionNewStoreditem);
                    if (oldInventoryOfStoreditemCollectionNewStoreditem != null && !oldInventoryOfStoreditemCollectionNewStoreditem.equals(inventory)) {
                        oldInventoryOfStoreditemCollectionNewStoreditem.getStoreditemCollection().remove(storeditemCollectionNewStoreditem);
                        oldInventoryOfStoreditemCollectionNewStoreditem = em.merge(oldInventoryOfStoreditemCollectionNewStoreditem);
                    }
                }
            }
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    characterCollectionOldCharacter.setEquipmentPack(null);
                    characterCollectionOldCharacter = em.merge(characterCollectionOldCharacter);
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    Inventory oldEquipmentPackOfCharacterCollectionNewCharacter = characterCollectionNewCharacter.getEquipmentPack();
                    characterCollectionNewCharacter.setEquipmentPack(inventory);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                    if (oldEquipmentPackOfCharacterCollectionNewCharacter != null && !oldEquipmentPackOfCharacterCollectionNewCharacter.equals(inventory)) {
                        oldEquipmentPackOfCharacterCollectionNewCharacter.getCharacterCollection().remove(characterCollectionNewCharacter);
                        oldEquipmentPackOfCharacterCollectionNewCharacter = em.merge(oldEquipmentPackOfCharacterCollectionNewCharacter);
                    }
                }
            }
            for (Character characterCollection1OldCharacter : characterCollection1Old) {
                if (!characterCollection1New.contains(characterCollection1OldCharacter)) {
                    characterCollection1OldCharacter.setEquipment(null);
                    characterCollection1OldCharacter = em.merge(characterCollection1OldCharacter);
                }
            }
            for (Character characterCollection1NewCharacter : characterCollection1New) {
                if (!characterCollection1Old.contains(characterCollection1NewCharacter)) {
                    Inventory oldEquipmentOfCharacterCollection1NewCharacter = characterCollection1NewCharacter.getEquipment();
                    characterCollection1NewCharacter.setEquipment(inventory);
                    characterCollection1NewCharacter = em.merge(characterCollection1NewCharacter);
                    if (oldEquipmentOfCharacterCollection1NewCharacter != null && !oldEquipmentOfCharacterCollection1NewCharacter.equals(inventory)) {
                        oldEquipmentOfCharacterCollection1NewCharacter.getCharacterCollection1().remove(characterCollection1NewCharacter);
                        oldEquipmentOfCharacterCollection1NewCharacter = em.merge(oldEquipmentOfCharacterCollection1NewCharacter);
                    }
                }
            }
            for (Character characterCollection2OldCharacter : characterCollection2Old) {
                if (!characterCollection2New.contains(characterCollection2OldCharacter)) {
                    characterCollection2OldCharacter.setBeltpouch(null);
                    characterCollection2OldCharacter = em.merge(characterCollection2OldCharacter);
                }
            }
            for (Character characterCollection2NewCharacter : characterCollection2New) {
                if (!characterCollection2Old.contains(characterCollection2NewCharacter)) {
                    Inventory oldBeltpouchOfCharacterCollection2NewCharacter = characterCollection2NewCharacter.getBeltpouch();
                    characterCollection2NewCharacter.setBeltpouch(inventory);
                    characterCollection2NewCharacter = em.merge(characterCollection2NewCharacter);
                    if (oldBeltpouchOfCharacterCollection2NewCharacter != null && !oldBeltpouchOfCharacterCollection2NewCharacter.equals(inventory)) {
                        oldBeltpouchOfCharacterCollection2NewCharacter.getCharacterCollection2().remove(characterCollection2NewCharacter);
                        oldBeltpouchOfCharacterCollection2NewCharacter = em.merge(oldBeltpouchOfCharacterCollection2NewCharacter);
                    }
                }
            }
            for (Character characterCollection3OldCharacter : characterCollection3Old) {
                if (!characterCollection3New.contains(characterCollection3OldCharacter)) {
                    characterCollection3OldCharacter.setBag2(null);
                    characterCollection3OldCharacter = em.merge(characterCollection3OldCharacter);
                }
            }
            for (Character characterCollection3NewCharacter : characterCollection3New) {
                if (!characterCollection3Old.contains(characterCollection3NewCharacter)) {
                    Inventory oldBag2OfCharacterCollection3NewCharacter = characterCollection3NewCharacter.getBag2();
                    characterCollection3NewCharacter.setBag2(inventory);
                    characterCollection3NewCharacter = em.merge(characterCollection3NewCharacter);
                    if (oldBag2OfCharacterCollection3NewCharacter != null && !oldBag2OfCharacterCollection3NewCharacter.equals(inventory)) {
                        oldBag2OfCharacterCollection3NewCharacter.getCharacterCollection3().remove(characterCollection3NewCharacter);
                        oldBag2OfCharacterCollection3NewCharacter = em.merge(oldBag2OfCharacterCollection3NewCharacter);
                    }
                }
            }
            for (Character characterCollection4OldCharacter : characterCollection4Old) {
                if (!characterCollection4New.contains(characterCollection4OldCharacter)) {
                    characterCollection4OldCharacter.setBag1(null);
                    characterCollection4OldCharacter = em.merge(characterCollection4OldCharacter);
                }
            }
            for (Character characterCollection4NewCharacter : characterCollection4New) {
                if (!characterCollection4Old.contains(characterCollection4NewCharacter)) {
                    Inventory oldBag1OfCharacterCollection4NewCharacter = characterCollection4NewCharacter.getBag1();
                    characterCollection4NewCharacter.setBag1(inventory);
                    characterCollection4NewCharacter = em.merge(characterCollection4NewCharacter);
                    if (oldBag1OfCharacterCollection4NewCharacter != null && !oldBag1OfCharacterCollection4NewCharacter.equals(inventory)) {
                        oldBag1OfCharacterCollection4NewCharacter.getCharacterCollection4().remove(characterCollection4NewCharacter);
                        oldBag1OfCharacterCollection4NewCharacter = em.merge(oldBag1OfCharacterCollection4NewCharacter);
                    }
                }
            }
            for (Character characterCollection5OldCharacter : characterCollection5Old) {
                if (!characterCollection5New.contains(characterCollection5OldCharacter)) {
                    characterCollection5OldCharacter.setBackpack(null);
                    characterCollection5OldCharacter = em.merge(characterCollection5OldCharacter);
                }
            }
            for (Character characterCollection5NewCharacter : characterCollection5New) {
                if (!characterCollection5Old.contains(characterCollection5NewCharacter)) {
                    Inventory oldBackpackOfCharacterCollection5NewCharacter = characterCollection5NewCharacter.getBackpack();
                    characterCollection5NewCharacter.setBackpack(inventory);
                    characterCollection5NewCharacter = em.merge(characterCollection5NewCharacter);
                    if (oldBackpackOfCharacterCollection5NewCharacter != null && !oldBackpackOfCharacterCollection5NewCharacter.equals(inventory)) {
                        oldBackpackOfCharacterCollection5NewCharacter.getCharacterCollection5().remove(characterCollection5NewCharacter);
                        oldBackpackOfCharacterCollection5NewCharacter = em.merge(oldBackpackOfCharacterCollection5NewCharacter);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inventory.getId();
                if (findInventory(id) == null) {
                    throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.");
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
            Inventory inventory;
            try {
                inventory = em.getReference(Inventory.class, id);
                inventory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Storagetab> storagetabCollectionOrphanCheck = inventory.getStoragetabCollection();
            for (Storagetab storagetabCollectionOrphanCheckStoragetab : storagetabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventory (" + inventory + ") cannot be destroyed since the Storagetab " + storagetabCollectionOrphanCheckStoragetab + " in its storagetabCollection field has a non-nullable inventoryID field.");
            }
            Collection<Storeditem> storeditemCollectionOrphanCheck = inventory.getStoreditemCollection();
            for (Storeditem storeditemCollectionOrphanCheckStoreditem : storeditemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inventory (" + inventory + ") cannot be destroyed since the Storeditem " + storeditemCollectionOrphanCheckStoreditem + " in its storeditemCollection field has a non-nullable inventory field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Account> accountCollection = inventory.getAccountCollection();
            for (Account accountCollectionAccount : accountCollection) {
                accountCollectionAccount.setMaterialStorage(null);
                accountCollectionAccount = em.merge(accountCollectionAccount);
            }
            Collection<Character> characterCollection = inventory.getCharacterCollection();
            for (Character characterCollectionCharacter : characterCollection) {
                characterCollectionCharacter.setEquipmentPack(null);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            Collection<Character> characterCollection1 = inventory.getCharacterCollection1();
            for (Character characterCollection1Character : characterCollection1) {
                characterCollection1Character.setEquipment(null);
                characterCollection1Character = em.merge(characterCollection1Character);
            }
            Collection<Character> characterCollection2 = inventory.getCharacterCollection2();
            for (Character characterCollection2Character : characterCollection2) {
                characterCollection2Character.setBeltpouch(null);
                characterCollection2Character = em.merge(characterCollection2Character);
            }
            Collection<Character> characterCollection3 = inventory.getCharacterCollection3();
            for (Character characterCollection3Character : characterCollection3) {
                characterCollection3Character.setBag2(null);
                characterCollection3Character = em.merge(characterCollection3Character);
            }
            Collection<Character> characterCollection4 = inventory.getCharacterCollection4();
            for (Character characterCollection4Character : characterCollection4) {
                characterCollection4Character.setBag1(null);
                characterCollection4Character = em.merge(characterCollection4Character);
            }
            Collection<Character> characterCollection5 = inventory.getCharacterCollection5();
            for (Character characterCollection5Character : characterCollection5) {
                characterCollection5Character.setBackpack(null);
                characterCollection5Character = em.merge(characterCollection5Character);
            }
            em.remove(inventory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventory> findInventoryEntities() {
        return findInventoryEntities(true, -1, -1);
    }

    public List<Inventory> findInventoryEntities(int maxResults, int firstResult) {
        return findInventoryEntities(false, maxResults, firstResult);
    }

    private List<Inventory> findInventoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventory.class));
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

    public Inventory findInventory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventory.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventory> rt = cq.from(Inventory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
