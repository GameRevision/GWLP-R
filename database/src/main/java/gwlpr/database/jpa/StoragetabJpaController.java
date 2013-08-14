/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Inventory;
import gwlpr.database.entities.Account;
import gwlpr.database.entities.Storagetab;
import gwlpr.database.entities.StoragetabPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class StoragetabJpaController implements Serializable 
{

    public StoragetabJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Storagetab storagetab) throws PreexistingEntityException, Exception {
        if (storagetab.getStoragetabPK() == null) {
            storagetab.setStoragetabPK(new StoragetabPK());
        }
        storagetab.getStoragetabPK().setAccountID(storagetab.getAccount().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inventory inventoryID = storagetab.getInventoryID();
            if (inventoryID != null) {
                inventoryID = em.getReference(inventoryID.getClass(), inventoryID.getId());
                storagetab.setInventoryID(inventoryID);
            }
            Account account = storagetab.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getEMail());
                storagetab.setAccount(account);
            }
            em.persist(storagetab);
            if (inventoryID != null) {
                inventoryID.getStoragetabCollection().add(storagetab);
                inventoryID = em.merge(inventoryID);
            }
            if (account != null) {
                account.getStoragetabCollection().add(storagetab);
                account = em.merge(account);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStoragetab(storagetab.getStoragetabPK()) != null) {
                throw new PreexistingEntityException("Storagetab " + storagetab + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Storagetab storagetab) throws NonexistentEntityException, Exception {
        storagetab.getStoragetabPK().setAccountID(storagetab.getAccount().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Storagetab persistentStoragetab = em.find(Storagetab.class, storagetab.getStoragetabPK());
            Inventory inventoryIDOld = persistentStoragetab.getInventoryID();
            Inventory inventoryIDNew = storagetab.getInventoryID();
            Account accountOld = persistentStoragetab.getAccount();
            Account accountNew = storagetab.getAccount();
            if (inventoryIDNew != null) {
                inventoryIDNew = em.getReference(inventoryIDNew.getClass(), inventoryIDNew.getId());
                storagetab.setInventoryID(inventoryIDNew);
            }
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getEMail());
                storagetab.setAccount(accountNew);
            }
            storagetab = em.merge(storagetab);
            if (inventoryIDOld != null && !inventoryIDOld.equals(inventoryIDNew)) {
                inventoryIDOld.getStoragetabCollection().remove(storagetab);
                inventoryIDOld = em.merge(inventoryIDOld);
            }
            if (inventoryIDNew != null && !inventoryIDNew.equals(inventoryIDOld)) {
                inventoryIDNew.getStoragetabCollection().add(storagetab);
                inventoryIDNew = em.merge(inventoryIDNew);
            }
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getStoragetabCollection().remove(storagetab);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getStoragetabCollection().add(storagetab);
                accountNew = em.merge(accountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                StoragetabPK id = storagetab.getStoragetabPK();
                if (findStoragetab(id) == null) {
                    throw new NonexistentEntityException("The storagetab with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(StoragetabPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Storagetab storagetab;
            try {
                storagetab = em.getReference(Storagetab.class, id);
                storagetab.getStoragetabPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The storagetab with id " + id + " no longer exists.", enfe);
            }
            Inventory inventoryID = storagetab.getInventoryID();
            if (inventoryID != null) {
                inventoryID.getStoragetabCollection().remove(storagetab);
                inventoryID = em.merge(inventoryID);
            }
            Account account = storagetab.getAccount();
            if (account != null) {
                account.getStoragetabCollection().remove(storagetab);
                account = em.merge(account);
            }
            em.remove(storagetab);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Storagetab> findStoragetabEntities() {
        return findStoragetabEntities(true, -1, -1);
    }

    public List<Storagetab> findStoragetabEntities(int maxResults, int firstResult) {
        return findStoragetabEntities(false, maxResults, firstResult);
    }

    private List<Storagetab> findStoragetabEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Storagetab.class));
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

    public Storagetab findStoragetab(StoragetabPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Storagetab.class, id);
        } finally {
            em.close();
        }
    }

    public int getStoragetabCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Storagetab> rt = cq.from(Storagetab.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
