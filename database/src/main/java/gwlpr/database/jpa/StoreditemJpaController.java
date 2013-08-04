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
import gwlpr.database.entities.Inventory;
import gwlpr.database.entities.Storeditem;
import gwlpr.database.entities.StoreditemPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class StoreditemJpaController implements Serializable 
{

    public StoreditemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Storeditem storeditem) throws PreexistingEntityException, Exception {
        if (storeditem.getStoreditemPK() == null) {
            storeditem.setStoreditemPK(new StoreditemPK());
        }
        storeditem.getStoreditemPK().setInventoryID(storeditem.getInventory().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item itemID = storeditem.getItemID();
            if (itemID != null) {
                itemID = em.getReference(itemID.getClass(), itemID.getId());
                storeditem.setItemID(itemID);
            }
            Inventory inventory = storeditem.getInventory();
            if (inventory != null) {
                inventory = em.getReference(inventory.getClass(), inventory.getId());
                storeditem.setInventory(inventory);
            }
            em.persist(storeditem);
            if (itemID != null) {
                itemID.getStoreditemCollection().add(storeditem);
                itemID = em.merge(itemID);
            }
            if (inventory != null) {
                inventory.getStoreditemCollection().add(storeditem);
                inventory = em.merge(inventory);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStoreditem(storeditem.getStoreditemPK()) != null) {
                throw new PreexistingEntityException("Storeditem " + storeditem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Storeditem storeditem) throws NonexistentEntityException, Exception {
        storeditem.getStoreditemPK().setInventoryID(storeditem.getInventory().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Storeditem persistentStoreditem = em.find(Storeditem.class, storeditem.getStoreditemPK());
            Item itemIDOld = persistentStoreditem.getItemID();
            Item itemIDNew = storeditem.getItemID();
            Inventory inventoryOld = persistentStoreditem.getInventory();
            Inventory inventoryNew = storeditem.getInventory();
            if (itemIDNew != null) {
                itemIDNew = em.getReference(itemIDNew.getClass(), itemIDNew.getId());
                storeditem.setItemID(itemIDNew);
            }
            if (inventoryNew != null) {
                inventoryNew = em.getReference(inventoryNew.getClass(), inventoryNew.getId());
                storeditem.setInventory(inventoryNew);
            }
            storeditem = em.merge(storeditem);
            if (itemIDOld != null && !itemIDOld.equals(itemIDNew)) {
                itemIDOld.getStoreditemCollection().remove(storeditem);
                itemIDOld = em.merge(itemIDOld);
            }
            if (itemIDNew != null && !itemIDNew.equals(itemIDOld)) {
                itemIDNew.getStoreditemCollection().add(storeditem);
                itemIDNew = em.merge(itemIDNew);
            }
            if (inventoryOld != null && !inventoryOld.equals(inventoryNew)) {
                inventoryOld.getStoreditemCollection().remove(storeditem);
                inventoryOld = em.merge(inventoryOld);
            }
            if (inventoryNew != null && !inventoryNew.equals(inventoryOld)) {
                inventoryNew.getStoreditemCollection().add(storeditem);
                inventoryNew = em.merge(inventoryNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                StoreditemPK id = storeditem.getStoreditemPK();
                if (findStoreditem(id) == null) {
                    throw new NonexistentEntityException("The storeditem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(StoreditemPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Storeditem storeditem;
            try {
                storeditem = em.getReference(Storeditem.class, id);
                storeditem.getStoreditemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The storeditem with id " + id + " no longer exists.", enfe);
            }
            Item itemID = storeditem.getItemID();
            if (itemID != null) {
                itemID.getStoreditemCollection().remove(storeditem);
                itemID = em.merge(itemID);
            }
            Inventory inventory = storeditem.getInventory();
            if (inventory != null) {
                inventory.getStoreditemCollection().remove(storeditem);
                inventory = em.merge(inventory);
            }
            em.remove(storeditem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Storeditem> findStoreditemEntities() {
        return findStoreditemEntities(true, -1, -1);
    }

    public List<Storeditem> findStoreditemEntities(int maxResults, int firstResult) {
        return findStoreditemEntities(false, maxResults, firstResult);
    }

    private List<Storeditem> findStoreditemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Storeditem.class));
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

    public Storeditem findStoreditem(StoreditemPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Storeditem.class, id);
        } finally {
            em.close();
        }
    }

    public int getStoreditemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Storeditem> rt = cq.from(Storeditem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
