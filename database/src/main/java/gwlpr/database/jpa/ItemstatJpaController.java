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
import gwlpr.database.entities.Itemstat;
import gwlpr.database.entities.ItemstatPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class ItemstatJpaController implements Serializable 
{

    public ItemstatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itemstat itemstat) throws PreexistingEntityException, Exception {
        if (itemstat.getItemstatPK() == null) {
            itemstat.setItemstatPK(new ItemstatPK());
        }
        itemstat.getItemstatPK().setItemID(itemstat.getItem().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item item = itemstat.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getId());
                itemstat.setItem(item);
            }
            em.persist(itemstat);
            if (item != null) {
                item.getItemstatCollection().add(itemstat);
                item = em.merge(item);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItemstat(itemstat.getItemstatPK()) != null) {
                throw new PreexistingEntityException("Itemstat " + itemstat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itemstat itemstat) throws NonexistentEntityException, Exception {
        itemstat.getItemstatPK().setItemID(itemstat.getItem().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemstat persistentItemstat = em.find(Itemstat.class, itemstat.getItemstatPK());
            Item itemOld = persistentItemstat.getItem();
            Item itemNew = itemstat.getItem();
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getId());
                itemstat.setItem(itemNew);
            }
            itemstat = em.merge(itemstat);
            if (itemOld != null && !itemOld.equals(itemNew)) {
                itemOld.getItemstatCollection().remove(itemstat);
                itemOld = em.merge(itemOld);
            }
            if (itemNew != null && !itemNew.equals(itemOld)) {
                itemNew.getItemstatCollection().add(itemstat);
                itemNew = em.merge(itemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ItemstatPK id = itemstat.getItemstatPK();
                if (findItemstat(id) == null) {
                    throw new NonexistentEntityException("The itemstat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItemstatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itemstat itemstat;
            try {
                itemstat = em.getReference(Itemstat.class, id);
                itemstat.getItemstatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemstat with id " + id + " no longer exists.", enfe);
            }
            Item item = itemstat.getItem();
            if (item != null) {
                item.getItemstatCollection().remove(itemstat);
                item = em.merge(item);
            }
            em.remove(itemstat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itemstat> findItemstatEntities() {
        return findItemstatEntities(true, -1, -1);
    }

    public List<Itemstat> findItemstatEntities(int maxResults, int firstResult) {
        return findItemstatEntities(false, maxResults, firstResult);
    }

    private List<Itemstat> findItemstatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itemstat.class));
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

    public Itemstat findItemstat(ItemstatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itemstat.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemstatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itemstat> rt = cq.from(Itemstat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
