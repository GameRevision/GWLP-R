/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Character;
import gwlpr.database.entities.Item;
import gwlpr.database.entities.Itembase;
import gwlpr.database.entities.Itemstat;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Storeditem;
import gwlpr.database.entities.Weapon;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class ItemJpaController implements Serializable 
{

    public ItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) {
        if (item.getItemstatCollection() == null) {
            item.setItemstatCollection(new ArrayList<Itemstat>());
        }
        if (item.getStoreditemCollection() == null) {
            item.setStoreditemCollection(new ArrayList<Storeditem>());
        }
        if (item.getWeaponCollection() == null) {
            item.setWeaponCollection(new ArrayList<Weapon>());
        }
        if (item.getWeaponCollection1() == null) {
            item.setWeaponCollection1(new ArrayList<Weapon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Character customizedFor = item.getCustomizedFor();
            if (customizedFor != null) {
                customizedFor = em.getReference(customizedFor.getClass(), customizedFor.getId());
                item.setCustomizedFor(customizedFor);
            }
            Itembase baseID = item.getBaseID();
            if (baseID != null) {
                baseID = em.getReference(baseID.getClass(), baseID.getId());
                item.setBaseID(baseID);
            }
            Collection<Itemstat> attachedItemstatCollection = new ArrayList<Itemstat>();
            for (Itemstat itemstatCollectionItemstatToAttach : item.getItemstatCollection()) {
                itemstatCollectionItemstatToAttach = em.getReference(itemstatCollectionItemstatToAttach.getClass(), itemstatCollectionItemstatToAttach.getItemstatPK());
                attachedItemstatCollection.add(itemstatCollectionItemstatToAttach);
            }
            item.setItemstatCollection(attachedItemstatCollection);
            Collection<Storeditem> attachedStoreditemCollection = new ArrayList<Storeditem>();
            for (Storeditem storeditemCollectionStoreditemToAttach : item.getStoreditemCollection()) {
                storeditemCollectionStoreditemToAttach = em.getReference(storeditemCollectionStoreditemToAttach.getClass(), storeditemCollectionStoreditemToAttach.getStoreditemPK());
                attachedStoreditemCollection.add(storeditemCollectionStoreditemToAttach);
            }
            item.setStoreditemCollection(attachedStoreditemCollection);
            Collection<Weapon> attachedWeaponCollection = new ArrayList<Weapon>();
            for (Weapon weaponCollectionWeaponToAttach : item.getWeaponCollection()) {
                weaponCollectionWeaponToAttach = em.getReference(weaponCollectionWeaponToAttach.getClass(), weaponCollectionWeaponToAttach.getId());
                attachedWeaponCollection.add(weaponCollectionWeaponToAttach);
            }
            item.setWeaponCollection(attachedWeaponCollection);
            Collection<Weapon> attachedWeaponCollection1 = new ArrayList<Weapon>();
            for (Weapon weaponCollection1WeaponToAttach : item.getWeaponCollection1()) {
                weaponCollection1WeaponToAttach = em.getReference(weaponCollection1WeaponToAttach.getClass(), weaponCollection1WeaponToAttach.getId());
                attachedWeaponCollection1.add(weaponCollection1WeaponToAttach);
            }
            item.setWeaponCollection1(attachedWeaponCollection1);
            em.persist(item);
            if (customizedFor != null) {
                customizedFor.getItemCollection().add(item);
                customizedFor = em.merge(customizedFor);
            }
            if (baseID != null) {
                baseID.getItemCollection().add(item);
                baseID = em.merge(baseID);
            }
            for (Itemstat itemstatCollectionItemstat : item.getItemstatCollection()) {
                Item oldItemOfItemstatCollectionItemstat = itemstatCollectionItemstat.getItem();
                itemstatCollectionItemstat.setItem(item);
                itemstatCollectionItemstat = em.merge(itemstatCollectionItemstat);
                if (oldItemOfItemstatCollectionItemstat != null) {
                    oldItemOfItemstatCollectionItemstat.getItemstatCollection().remove(itemstatCollectionItemstat);
                    oldItemOfItemstatCollectionItemstat = em.merge(oldItemOfItemstatCollectionItemstat);
                }
            }
            for (Storeditem storeditemCollectionStoreditem : item.getStoreditemCollection()) {
                Item oldItemIDOfStoreditemCollectionStoreditem = storeditemCollectionStoreditem.getItemID();
                storeditemCollectionStoreditem.setItemID(item);
                storeditemCollectionStoreditem = em.merge(storeditemCollectionStoreditem);
                if (oldItemIDOfStoreditemCollectionStoreditem != null) {
                    oldItemIDOfStoreditemCollectionStoreditem.getStoreditemCollection().remove(storeditemCollectionStoreditem);
                    oldItemIDOfStoreditemCollectionStoreditem = em.merge(oldItemIDOfStoreditemCollectionStoreditem);
                }
            }
            for (Weapon weaponCollectionWeapon : item.getWeaponCollection()) {
                Item oldOffhandOfWeaponCollectionWeapon = weaponCollectionWeapon.getOffhand();
                weaponCollectionWeapon.setOffhand(item);
                weaponCollectionWeapon = em.merge(weaponCollectionWeapon);
                if (oldOffhandOfWeaponCollectionWeapon != null) {
                    oldOffhandOfWeaponCollectionWeapon.getWeaponCollection().remove(weaponCollectionWeapon);
                    oldOffhandOfWeaponCollectionWeapon = em.merge(oldOffhandOfWeaponCollectionWeapon);
                }
            }
            for (Weapon weaponCollection1Weapon : item.getWeaponCollection1()) {
                Item oldLeadhandOfWeaponCollection1Weapon = weaponCollection1Weapon.getLeadhand();
                weaponCollection1Weapon.setLeadhand(item);
                weaponCollection1Weapon = em.merge(weaponCollection1Weapon);
                if (oldLeadhandOfWeaponCollection1Weapon != null) {
                    oldLeadhandOfWeaponCollection1Weapon.getWeaponCollection1().remove(weaponCollection1Weapon);
                    oldLeadhandOfWeaponCollection1Weapon = em.merge(oldLeadhandOfWeaponCollection1Weapon);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Item item) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item persistentItem = em.find(Item.class, item.getId());
            Character customizedForOld = persistentItem.getCustomizedFor();
            Character customizedForNew = item.getCustomizedFor();
            Itembase baseIDOld = persistentItem.getBaseID();
            Itembase baseIDNew = item.getBaseID();
            Collection<Itemstat> itemstatCollectionOld = persistentItem.getItemstatCollection();
            Collection<Itemstat> itemstatCollectionNew = item.getItemstatCollection();
            Collection<Storeditem> storeditemCollectionOld = persistentItem.getStoreditemCollection();
            Collection<Storeditem> storeditemCollectionNew = item.getStoreditemCollection();
            Collection<Weapon> weaponCollectionOld = persistentItem.getWeaponCollection();
            Collection<Weapon> weaponCollectionNew = item.getWeaponCollection();
            Collection<Weapon> weaponCollection1Old = persistentItem.getWeaponCollection1();
            Collection<Weapon> weaponCollection1New = item.getWeaponCollection1();
            List<String> illegalOrphanMessages = null;
            for (Itemstat itemstatCollectionOldItemstat : itemstatCollectionOld) {
                if (!itemstatCollectionNew.contains(itemstatCollectionOldItemstat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Itemstat " + itemstatCollectionOldItemstat + " since its item field is not nullable.");
                }
            }
            for (Storeditem storeditemCollectionOldStoreditem : storeditemCollectionOld) {
                if (!storeditemCollectionNew.contains(storeditemCollectionOldStoreditem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Storeditem " + storeditemCollectionOldStoreditem + " since its itemID field is not nullable.");
                }
            }
            for (Weapon weaponCollectionOldWeapon : weaponCollectionOld) {
                if (!weaponCollectionNew.contains(weaponCollectionOldWeapon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Weapon " + weaponCollectionOldWeapon + " since its offhand field is not nullable.");
                }
            }
            for (Weapon weaponCollection1OldWeapon : weaponCollection1Old) {
                if (!weaponCollection1New.contains(weaponCollection1OldWeapon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Weapon " + weaponCollection1OldWeapon + " since its leadhand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customizedForNew != null) {
                customizedForNew = em.getReference(customizedForNew.getClass(), customizedForNew.getId());
                item.setCustomizedFor(customizedForNew);
            }
            if (baseIDNew != null) {
                baseIDNew = em.getReference(baseIDNew.getClass(), baseIDNew.getId());
                item.setBaseID(baseIDNew);
            }
            Collection<Itemstat> attachedItemstatCollectionNew = new ArrayList<Itemstat>();
            for (Itemstat itemstatCollectionNewItemstatToAttach : itemstatCollectionNew) {
                itemstatCollectionNewItemstatToAttach = em.getReference(itemstatCollectionNewItemstatToAttach.getClass(), itemstatCollectionNewItemstatToAttach.getItemstatPK());
                attachedItemstatCollectionNew.add(itemstatCollectionNewItemstatToAttach);
            }
            itemstatCollectionNew = attachedItemstatCollectionNew;
            item.setItemstatCollection(itemstatCollectionNew);
            Collection<Storeditem> attachedStoreditemCollectionNew = new ArrayList<Storeditem>();
            for (Storeditem storeditemCollectionNewStoreditemToAttach : storeditemCollectionNew) {
                storeditemCollectionNewStoreditemToAttach = em.getReference(storeditemCollectionNewStoreditemToAttach.getClass(), storeditemCollectionNewStoreditemToAttach.getStoreditemPK());
                attachedStoreditemCollectionNew.add(storeditemCollectionNewStoreditemToAttach);
            }
            storeditemCollectionNew = attachedStoreditemCollectionNew;
            item.setStoreditemCollection(storeditemCollectionNew);
            Collection<Weapon> attachedWeaponCollectionNew = new ArrayList<Weapon>();
            for (Weapon weaponCollectionNewWeaponToAttach : weaponCollectionNew) {
                weaponCollectionNewWeaponToAttach = em.getReference(weaponCollectionNewWeaponToAttach.getClass(), weaponCollectionNewWeaponToAttach.getId());
                attachedWeaponCollectionNew.add(weaponCollectionNewWeaponToAttach);
            }
            weaponCollectionNew = attachedWeaponCollectionNew;
            item.setWeaponCollection(weaponCollectionNew);
            Collection<Weapon> attachedWeaponCollection1New = new ArrayList<Weapon>();
            for (Weapon weaponCollection1NewWeaponToAttach : weaponCollection1New) {
                weaponCollection1NewWeaponToAttach = em.getReference(weaponCollection1NewWeaponToAttach.getClass(), weaponCollection1NewWeaponToAttach.getId());
                attachedWeaponCollection1New.add(weaponCollection1NewWeaponToAttach);
            }
            weaponCollection1New = attachedWeaponCollection1New;
            item.setWeaponCollection1(weaponCollection1New);
            item = em.merge(item);
            if (customizedForOld != null && !customizedForOld.equals(customizedForNew)) {
                customizedForOld.getItemCollection().remove(item);
                customizedForOld = em.merge(customizedForOld);
            }
            if (customizedForNew != null && !customizedForNew.equals(customizedForOld)) {
                customizedForNew.getItemCollection().add(item);
                customizedForNew = em.merge(customizedForNew);
            }
            if (baseIDOld != null && !baseIDOld.equals(baseIDNew)) {
                baseIDOld.getItemCollection().remove(item);
                baseIDOld = em.merge(baseIDOld);
            }
            if (baseIDNew != null && !baseIDNew.equals(baseIDOld)) {
                baseIDNew.getItemCollection().add(item);
                baseIDNew = em.merge(baseIDNew);
            }
            for (Itemstat itemstatCollectionNewItemstat : itemstatCollectionNew) {
                if (!itemstatCollectionOld.contains(itemstatCollectionNewItemstat)) {
                    Item oldItemOfItemstatCollectionNewItemstat = itemstatCollectionNewItemstat.getItem();
                    itemstatCollectionNewItemstat.setItem(item);
                    itemstatCollectionNewItemstat = em.merge(itemstatCollectionNewItemstat);
                    if (oldItemOfItemstatCollectionNewItemstat != null && !oldItemOfItemstatCollectionNewItemstat.equals(item)) {
                        oldItemOfItemstatCollectionNewItemstat.getItemstatCollection().remove(itemstatCollectionNewItemstat);
                        oldItemOfItemstatCollectionNewItemstat = em.merge(oldItemOfItemstatCollectionNewItemstat);
                    }
                }
            }
            for (Storeditem storeditemCollectionNewStoreditem : storeditemCollectionNew) {
                if (!storeditemCollectionOld.contains(storeditemCollectionNewStoreditem)) {
                    Item oldItemIDOfStoreditemCollectionNewStoreditem = storeditemCollectionNewStoreditem.getItemID();
                    storeditemCollectionNewStoreditem.setItemID(item);
                    storeditemCollectionNewStoreditem = em.merge(storeditemCollectionNewStoreditem);
                    if (oldItemIDOfStoreditemCollectionNewStoreditem != null && !oldItemIDOfStoreditemCollectionNewStoreditem.equals(item)) {
                        oldItemIDOfStoreditemCollectionNewStoreditem.getStoreditemCollection().remove(storeditemCollectionNewStoreditem);
                        oldItemIDOfStoreditemCollectionNewStoreditem = em.merge(oldItemIDOfStoreditemCollectionNewStoreditem);
                    }
                }
            }
            for (Weapon weaponCollectionNewWeapon : weaponCollectionNew) {
                if (!weaponCollectionOld.contains(weaponCollectionNewWeapon)) {
                    Item oldOffhandOfWeaponCollectionNewWeapon = weaponCollectionNewWeapon.getOffhand();
                    weaponCollectionNewWeapon.setOffhand(item);
                    weaponCollectionNewWeapon = em.merge(weaponCollectionNewWeapon);
                    if (oldOffhandOfWeaponCollectionNewWeapon != null && !oldOffhandOfWeaponCollectionNewWeapon.equals(item)) {
                        oldOffhandOfWeaponCollectionNewWeapon.getWeaponCollection().remove(weaponCollectionNewWeapon);
                        oldOffhandOfWeaponCollectionNewWeapon = em.merge(oldOffhandOfWeaponCollectionNewWeapon);
                    }
                }
            }
            for (Weapon weaponCollection1NewWeapon : weaponCollection1New) {
                if (!weaponCollection1Old.contains(weaponCollection1NewWeapon)) {
                    Item oldLeadhandOfWeaponCollection1NewWeapon = weaponCollection1NewWeapon.getLeadhand();
                    weaponCollection1NewWeapon.setLeadhand(item);
                    weaponCollection1NewWeapon = em.merge(weaponCollection1NewWeapon);
                    if (oldLeadhandOfWeaponCollection1NewWeapon != null && !oldLeadhandOfWeaponCollection1NewWeapon.equals(item)) {
                        oldLeadhandOfWeaponCollection1NewWeapon.getWeaponCollection1().remove(weaponCollection1NewWeapon);
                        oldLeadhandOfWeaponCollection1NewWeapon = em.merge(oldLeadhandOfWeaponCollection1NewWeapon);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = item.getId();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
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
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Itemstat> itemstatCollectionOrphanCheck = item.getItemstatCollection();
            for (Itemstat itemstatCollectionOrphanCheckItemstat : itemstatCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Itemstat " + itemstatCollectionOrphanCheckItemstat + " in its itemstatCollection field has a non-nullable item field.");
            }
            Collection<Storeditem> storeditemCollectionOrphanCheck = item.getStoreditemCollection();
            for (Storeditem storeditemCollectionOrphanCheckStoreditem : storeditemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Storeditem " + storeditemCollectionOrphanCheckStoreditem + " in its storeditemCollection field has a non-nullable itemID field.");
            }
            Collection<Weapon> weaponCollectionOrphanCheck = item.getWeaponCollection();
            for (Weapon weaponCollectionOrphanCheckWeapon : weaponCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Weapon " + weaponCollectionOrphanCheckWeapon + " in its weaponCollection field has a non-nullable offhand field.");
            }
            Collection<Weapon> weaponCollection1OrphanCheck = item.getWeaponCollection1();
            for (Weapon weaponCollection1OrphanCheckWeapon : weaponCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Weapon " + weaponCollection1OrphanCheckWeapon + " in its weaponCollection1 field has a non-nullable leadhand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Character customizedFor = item.getCustomizedFor();
            if (customizedFor != null) {
                customizedFor.getItemCollection().remove(item);
                customizedFor = em.merge(customizedFor);
            }
            Itembase baseID = item.getBaseID();
            if (baseID != null) {
                baseID.getItemCollection().remove(item);
                baseID = em.merge(baseID);
            }
            em.remove(item);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
