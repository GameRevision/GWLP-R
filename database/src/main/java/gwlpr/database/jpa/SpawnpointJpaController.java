/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Map;
import gwlpr.database.entities.Spawnpoint;
import gwlpr.database.entities.SpawnpointPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class SpawnpointJpaController implements Serializable 
{

    public SpawnpointJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Spawnpoint spawnpoint) throws PreexistingEntityException, Exception {
        if (spawnpoint.getSpawnpointPK() == null) {
            spawnpoint.setSpawnpointPK(new SpawnpointPK());
        }
        spawnpoint.getSpawnpointPK().setMapID(spawnpoint.getMap().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Map map = spawnpoint.getMap();
            if (map != null) {
                map = em.getReference(map.getClass(), map.getId());
                spawnpoint.setMap(map);
            }
            em.persist(spawnpoint);
            if (map != null) {
                map.getSpawnpointCollection().add(spawnpoint);
                map = em.merge(map);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSpawnpoint(spawnpoint.getSpawnpointPK()) != null) {
                throw new PreexistingEntityException("Spawnpoint " + spawnpoint + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Spawnpoint spawnpoint) throws NonexistentEntityException, Exception {
        spawnpoint.getSpawnpointPK().setMapID(spawnpoint.getMap().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spawnpoint persistentSpawnpoint = em.find(Spawnpoint.class, spawnpoint.getSpawnpointPK());
            Map mapOld = persistentSpawnpoint.getMap();
            Map mapNew = spawnpoint.getMap();
            if (mapNew != null) {
                mapNew = em.getReference(mapNew.getClass(), mapNew.getId());
                spawnpoint.setMap(mapNew);
            }
            spawnpoint = em.merge(spawnpoint);
            if (mapOld != null && !mapOld.equals(mapNew)) {
                mapOld.getSpawnpointCollection().remove(spawnpoint);
                mapOld = em.merge(mapOld);
            }
            if (mapNew != null && !mapNew.equals(mapOld)) {
                mapNew.getSpawnpointCollection().add(spawnpoint);
                mapNew = em.merge(mapNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SpawnpointPK id = spawnpoint.getSpawnpointPK();
                if (findSpawnpoint(id) == null) {
                    throw new NonexistentEntityException("The spawnpoint with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SpawnpointPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spawnpoint spawnpoint;
            try {
                spawnpoint = em.getReference(Spawnpoint.class, id);
                spawnpoint.getSpawnpointPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spawnpoint with id " + id + " no longer exists.", enfe);
            }
            Map map = spawnpoint.getMap();
            if (map != null) {
                map.getSpawnpointCollection().remove(spawnpoint);
                map = em.merge(map);
            }
            em.remove(spawnpoint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Spawnpoint> findSpawnpointEntities() {
        return findSpawnpointEntities(true, -1, -1);
    }

    public List<Spawnpoint> findSpawnpointEntities(int maxResults, int firstResult) {
        return findSpawnpointEntities(false, maxResults, firstResult);
    }

    private List<Spawnpoint> findSpawnpointEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spawnpoint.class));
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

    public Spawnpoint findSpawnpoint(SpawnpointPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spawnpoint.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpawnpointCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spawnpoint> rt = cq.from(Spawnpoint.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
