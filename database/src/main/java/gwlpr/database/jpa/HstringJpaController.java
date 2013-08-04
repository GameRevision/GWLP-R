/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.entities.Hstring;
import gwlpr.database.entities.HstringPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * @author _rusty
 */
public class HstringJpaController implements Serializable 
{

    public HstringJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hstring hstring) throws PreexistingEntityException, Exception {
        if (hstring.getHstringPK() == null) {
            hstring.setHstringPK(new HstringPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(hstring);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHstring(hstring.getHstringPK()) != null) {
                throw new PreexistingEntityException("Hstring " + hstring + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hstring hstring) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            hstring = em.merge(hstring);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HstringPK id = hstring.getHstringPK();
                if (findHstring(id) == null) {
                    throw new NonexistentEntityException("The hstring with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HstringPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hstring hstring;
            try {
                hstring = em.getReference(Hstring.class, id);
                hstring.getHstringPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hstring with id " + id + " no longer exists.", enfe);
            }
            em.remove(hstring);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hstring> findHstringEntities() {
        return findHstringEntities(true, -1, -1);
    }

    public List<Hstring> findHstringEntities(int maxResults, int firstResult) {
        return findHstringEntities(false, maxResults, firstResult);
    }

    private List<Hstring> findHstringEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hstring.class));
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

    public Hstring findHstring(HstringPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hstring.class, id);
        } finally {
            em.close();
        }
    }

    public int getHstringCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hstring> rt = cq.from(Hstring.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
