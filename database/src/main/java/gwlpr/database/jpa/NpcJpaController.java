/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.entities.Npc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Weapon;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class NpcJpaController implements Serializable 
{

    public NpcJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Npc npc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weapon weapons = npc.getWeapons();
            if (weapons != null) {
                weapons = em.getReference(weapons.getClass(), weapons.getId());
                npc.setWeapons(weapons);
            }
            em.persist(npc);
            if (weapons != null) {
                weapons.getNpcCollection().add(npc);
                weapons = em.merge(weapons);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Npc npc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Npc persistentNpc = em.find(Npc.class, npc.getId());
            Weapon weaponsOld = persistentNpc.getWeapons();
            Weapon weaponsNew = npc.getWeapons();
            if (weaponsNew != null) {
                weaponsNew = em.getReference(weaponsNew.getClass(), weaponsNew.getId());
                npc.setWeapons(weaponsNew);
            }
            npc = em.merge(npc);
            if (weaponsOld != null && !weaponsOld.equals(weaponsNew)) {
                weaponsOld.getNpcCollection().remove(npc);
                weaponsOld = em.merge(weaponsOld);
            }
            if (weaponsNew != null && !weaponsNew.equals(weaponsOld)) {
                weaponsNew.getNpcCollection().add(npc);
                weaponsNew = em.merge(weaponsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = npc.getId();
                if (findNpc(id) == null) {
                    throw new NonexistentEntityException("The npc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Npc npc;
            try {
                npc = em.getReference(Npc.class, id);
                npc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The npc with id " + id + " no longer exists.", enfe);
            }
            Weapon weapons = npc.getWeapons();
            if (weapons != null) {
                weapons.getNpcCollection().remove(npc);
                weapons = em.merge(weapons);
            }
            em.remove(npc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Npc> findNpcEntities() {
        return findNpcEntities(true, -1, -1);
    }

    public List<Npc> findNpcEntities(int maxResults, int firstResult) {
        return findNpcEntities(false, maxResults, firstResult);
    }

    private List<Npc> findNpcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Npc.class));
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

    public Npc findNpc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Npc.class, id);
        } finally {
            em.close();
        }
    }

    public int getNpcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Npc> rt = cq.from(Npc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
