/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Item;
import gwlpr.database.entities.Npc;
import gwlpr.database.entities.Weapon;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Weaponset;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class WeaponJpaController implements Serializable 
{

    public WeaponJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Weapon weapon) {
        if (weapon.getNpcCollection() == null) {
            weapon.setNpcCollection(new ArrayList<Npc>());
        }
        if (weapon.getWeaponsetCollection() == null) {
            weapon.setWeaponsetCollection(new ArrayList<Weaponset>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item offhand = weapon.getOffhand();
            if (offhand != null) {
                offhand = em.getReference(offhand.getClass(), offhand.getId());
                weapon.setOffhand(offhand);
            }
            Item leadhand = weapon.getLeadhand();
            if (leadhand != null) {
                leadhand = em.getReference(leadhand.getClass(), leadhand.getId());
                weapon.setLeadhand(leadhand);
            }
            Collection<Npc> attachedNpcCollection = new ArrayList<Npc>();
            for (Npc npcCollectionNpcToAttach : weapon.getNpcCollection()) {
                npcCollectionNpcToAttach = em.getReference(npcCollectionNpcToAttach.getClass(), npcCollectionNpcToAttach.getId());
                attachedNpcCollection.add(npcCollectionNpcToAttach);
            }
            weapon.setNpcCollection(attachedNpcCollection);
            Collection<Weaponset> attachedWeaponsetCollection = new ArrayList<Weaponset>();
            for (Weaponset weaponsetCollectionWeaponsetToAttach : weapon.getWeaponsetCollection()) {
                weaponsetCollectionWeaponsetToAttach = em.getReference(weaponsetCollectionWeaponsetToAttach.getClass(), weaponsetCollectionWeaponsetToAttach.getWeaponsetPK());
                attachedWeaponsetCollection.add(weaponsetCollectionWeaponsetToAttach);
            }
            weapon.setWeaponsetCollection(attachedWeaponsetCollection);
            em.persist(weapon);
            if (offhand != null) {
                offhand.getWeaponCollection().add(weapon);
                offhand = em.merge(offhand);
            }
            if (leadhand != null) {
                leadhand.getWeaponCollection().add(weapon);
                leadhand = em.merge(leadhand);
            }
            for (Npc npcCollectionNpc : weapon.getNpcCollection()) {
                Weapon oldWeaponsOfNpcCollectionNpc = npcCollectionNpc.getWeapons();
                npcCollectionNpc.setWeapons(weapon);
                npcCollectionNpc = em.merge(npcCollectionNpc);
                if (oldWeaponsOfNpcCollectionNpc != null) {
                    oldWeaponsOfNpcCollectionNpc.getNpcCollection().remove(npcCollectionNpc);
                    oldWeaponsOfNpcCollectionNpc = em.merge(oldWeaponsOfNpcCollectionNpc);
                }
            }
            for (Weaponset weaponsetCollectionWeaponset : weapon.getWeaponsetCollection()) {
                Weapon oldWeaponsOfWeaponsetCollectionWeaponset = weaponsetCollectionWeaponset.getWeapons();
                weaponsetCollectionWeaponset.setWeapons(weapon);
                weaponsetCollectionWeaponset = em.merge(weaponsetCollectionWeaponset);
                if (oldWeaponsOfWeaponsetCollectionWeaponset != null) {
                    oldWeaponsOfWeaponsetCollectionWeaponset.getWeaponsetCollection().remove(weaponsetCollectionWeaponset);
                    oldWeaponsOfWeaponsetCollectionWeaponset = em.merge(oldWeaponsOfWeaponsetCollectionWeaponset);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Weapon weapon) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weapon persistentWeapon = em.find(Weapon.class, weapon.getId());
            Item offhandOld = persistentWeapon.getOffhand();
            Item offhandNew = weapon.getOffhand();
            Item leadhandOld = persistentWeapon.getLeadhand();
            Item leadhandNew = weapon.getLeadhand();
            Collection<Npc> npcCollectionOld = persistentWeapon.getNpcCollection();
            Collection<Npc> npcCollectionNew = weapon.getNpcCollection();
            Collection<Weaponset> weaponsetCollectionOld = persistentWeapon.getWeaponsetCollection();
            Collection<Weaponset> weaponsetCollectionNew = weapon.getWeaponsetCollection();
            List<String> illegalOrphanMessages = null;
            for (Npc npcCollectionOldNpc : npcCollectionOld) {
                if (!npcCollectionNew.contains(npcCollectionOldNpc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Npc " + npcCollectionOldNpc + " since its weapons field is not nullable.");
                }
            }
            for (Weaponset weaponsetCollectionOldWeaponset : weaponsetCollectionOld) {
                if (!weaponsetCollectionNew.contains(weaponsetCollectionOldWeaponset)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Weaponset " + weaponsetCollectionOldWeaponset + " since its weapons field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (offhandNew != null) {
                offhandNew = em.getReference(offhandNew.getClass(), offhandNew.getId());
                weapon.setOffhand(offhandNew);
            }
            if (leadhandNew != null) {
                leadhandNew = em.getReference(leadhandNew.getClass(), leadhandNew.getId());
                weapon.setLeadhand(leadhandNew);
            }
            Collection<Npc> attachedNpcCollectionNew = new ArrayList<Npc>();
            for (Npc npcCollectionNewNpcToAttach : npcCollectionNew) {
                npcCollectionNewNpcToAttach = em.getReference(npcCollectionNewNpcToAttach.getClass(), npcCollectionNewNpcToAttach.getId());
                attachedNpcCollectionNew.add(npcCollectionNewNpcToAttach);
            }
            npcCollectionNew = attachedNpcCollectionNew;
            weapon.setNpcCollection(npcCollectionNew);
            Collection<Weaponset> attachedWeaponsetCollectionNew = new ArrayList<Weaponset>();
            for (Weaponset weaponsetCollectionNewWeaponsetToAttach : weaponsetCollectionNew) {
                weaponsetCollectionNewWeaponsetToAttach = em.getReference(weaponsetCollectionNewWeaponsetToAttach.getClass(), weaponsetCollectionNewWeaponsetToAttach.getWeaponsetPK());
                attachedWeaponsetCollectionNew.add(weaponsetCollectionNewWeaponsetToAttach);
            }
            weaponsetCollectionNew = attachedWeaponsetCollectionNew;
            weapon.setWeaponsetCollection(weaponsetCollectionNew);
            weapon = em.merge(weapon);
            if (offhandOld != null && !offhandOld.equals(offhandNew)) {
                offhandOld.getWeaponCollection().remove(weapon);
                offhandOld = em.merge(offhandOld);
            }
            if (offhandNew != null && !offhandNew.equals(offhandOld)) {
                offhandNew.getWeaponCollection().add(weapon);
                offhandNew = em.merge(offhandNew);
            }
            if (leadhandOld != null && !leadhandOld.equals(leadhandNew)) {
                leadhandOld.getWeaponCollection().remove(weapon);
                leadhandOld = em.merge(leadhandOld);
            }
            if (leadhandNew != null && !leadhandNew.equals(leadhandOld)) {
                leadhandNew.getWeaponCollection().add(weapon);
                leadhandNew = em.merge(leadhandNew);
            }
            for (Npc npcCollectionNewNpc : npcCollectionNew) {
                if (!npcCollectionOld.contains(npcCollectionNewNpc)) {
                    Weapon oldWeaponsOfNpcCollectionNewNpc = npcCollectionNewNpc.getWeapons();
                    npcCollectionNewNpc.setWeapons(weapon);
                    npcCollectionNewNpc = em.merge(npcCollectionNewNpc);
                    if (oldWeaponsOfNpcCollectionNewNpc != null && !oldWeaponsOfNpcCollectionNewNpc.equals(weapon)) {
                        oldWeaponsOfNpcCollectionNewNpc.getNpcCollection().remove(npcCollectionNewNpc);
                        oldWeaponsOfNpcCollectionNewNpc = em.merge(oldWeaponsOfNpcCollectionNewNpc);
                    }
                }
            }
            for (Weaponset weaponsetCollectionNewWeaponset : weaponsetCollectionNew) {
                if (!weaponsetCollectionOld.contains(weaponsetCollectionNewWeaponset)) {
                    Weapon oldWeaponsOfWeaponsetCollectionNewWeaponset = weaponsetCollectionNewWeaponset.getWeapons();
                    weaponsetCollectionNewWeaponset.setWeapons(weapon);
                    weaponsetCollectionNewWeaponset = em.merge(weaponsetCollectionNewWeaponset);
                    if (oldWeaponsOfWeaponsetCollectionNewWeaponset != null && !oldWeaponsOfWeaponsetCollectionNewWeaponset.equals(weapon)) {
                        oldWeaponsOfWeaponsetCollectionNewWeaponset.getWeaponsetCollection().remove(weaponsetCollectionNewWeaponset);
                        oldWeaponsOfWeaponsetCollectionNewWeaponset = em.merge(oldWeaponsOfWeaponsetCollectionNewWeaponset);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = weapon.getId();
                if (findWeapon(id) == null) {
                    throw new NonexistentEntityException("The weapon with id " + id + " no longer exists.");
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
            Weapon weapon;
            try {
                weapon = em.getReference(Weapon.class, id);
                weapon.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The weapon with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Npc> npcCollectionOrphanCheck = weapon.getNpcCollection();
            for (Npc npcCollectionOrphanCheckNpc : npcCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Weapon (" + weapon + ") cannot be destroyed since the Npc " + npcCollectionOrphanCheckNpc + " in its npcCollection field has a non-nullable weapons field.");
            }
            Collection<Weaponset> weaponsetCollectionOrphanCheck = weapon.getWeaponsetCollection();
            for (Weaponset weaponsetCollectionOrphanCheckWeaponset : weaponsetCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Weapon (" + weapon + ") cannot be destroyed since the Weaponset " + weaponsetCollectionOrphanCheckWeaponset + " in its weaponsetCollection field has a non-nullable weapons field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Item offhand = weapon.getOffhand();
            if (offhand != null) {
                offhand.getWeaponCollection().remove(weapon);
                offhand = em.merge(offhand);
            }
            Item leadhand = weapon.getLeadhand();
            if (leadhand != null) {
                leadhand.getWeaponCollection().remove(weapon);
                leadhand = em.merge(leadhand);
            }
            em.remove(weapon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Weapon> findWeaponEntities() {
        return findWeaponEntities(true, -1, -1);
    }

    public List<Weapon> findWeaponEntities(int maxResults, int firstResult) {
        return findWeaponEntities(false, maxResults, firstResult);
    }

    private List<Weapon> findWeaponEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Weapon.class));
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

    public Weapon findWeapon(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Weapon.class, id);
        } finally {
            em.close();
        }
    }

    public int getWeaponCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Weapon> rt = cq.from(Weapon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
