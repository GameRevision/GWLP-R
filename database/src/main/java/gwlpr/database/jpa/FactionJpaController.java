/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.entities.Faction;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Factionstat;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class FactionJpaController implements Serializable 
{

    public FactionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Faction faction) {
        if (faction.getFactionstatCollection() == null) {
            faction.setFactionstatCollection(new ArrayList<Factionstat>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Factionstat> attachedFactionstatCollection = new ArrayList<Factionstat>();
            for (Factionstat factionstatCollectionFactionstatToAttach : faction.getFactionstatCollection()) {
                factionstatCollectionFactionstatToAttach = em.getReference(factionstatCollectionFactionstatToAttach.getClass(), factionstatCollectionFactionstatToAttach.getFactionstatPK());
                attachedFactionstatCollection.add(factionstatCollectionFactionstatToAttach);
            }
            faction.setFactionstatCollection(attachedFactionstatCollection);
            em.persist(faction);
            for (Factionstat factionstatCollectionFactionstat : faction.getFactionstatCollection()) {
                Faction oldFactionOfFactionstatCollectionFactionstat = factionstatCollectionFactionstat.getFaction();
                factionstatCollectionFactionstat.setFaction(faction);
                factionstatCollectionFactionstat = em.merge(factionstatCollectionFactionstat);
                if (oldFactionOfFactionstatCollectionFactionstat != null) {
                    oldFactionOfFactionstatCollectionFactionstat.getFactionstatCollection().remove(factionstatCollectionFactionstat);
                    oldFactionOfFactionstatCollectionFactionstat = em.merge(oldFactionOfFactionstatCollectionFactionstat);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Faction faction) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faction persistentFaction = em.find(Faction.class, faction.getId());
            Collection<Factionstat> factionstatCollectionOld = persistentFaction.getFactionstatCollection();
            Collection<Factionstat> factionstatCollectionNew = faction.getFactionstatCollection();
            List<String> illegalOrphanMessages = null;
            for (Factionstat factionstatCollectionOldFactionstat : factionstatCollectionOld) {
                if (!factionstatCollectionNew.contains(factionstatCollectionOldFactionstat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factionstat " + factionstatCollectionOldFactionstat + " since its faction field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Factionstat> attachedFactionstatCollectionNew = new ArrayList<Factionstat>();
            for (Factionstat factionstatCollectionNewFactionstatToAttach : factionstatCollectionNew) {
                factionstatCollectionNewFactionstatToAttach = em.getReference(factionstatCollectionNewFactionstatToAttach.getClass(), factionstatCollectionNewFactionstatToAttach.getFactionstatPK());
                attachedFactionstatCollectionNew.add(factionstatCollectionNewFactionstatToAttach);
            }
            factionstatCollectionNew = attachedFactionstatCollectionNew;
            faction.setFactionstatCollection(factionstatCollectionNew);
            faction = em.merge(faction);
            for (Factionstat factionstatCollectionNewFactionstat : factionstatCollectionNew) {
                if (!factionstatCollectionOld.contains(factionstatCollectionNewFactionstat)) {
                    Faction oldFactionOfFactionstatCollectionNewFactionstat = factionstatCollectionNewFactionstat.getFaction();
                    factionstatCollectionNewFactionstat.setFaction(faction);
                    factionstatCollectionNewFactionstat = em.merge(factionstatCollectionNewFactionstat);
                    if (oldFactionOfFactionstatCollectionNewFactionstat != null && !oldFactionOfFactionstatCollectionNewFactionstat.equals(faction)) {
                        oldFactionOfFactionstatCollectionNewFactionstat.getFactionstatCollection().remove(factionstatCollectionNewFactionstat);
                        oldFactionOfFactionstatCollectionNewFactionstat = em.merge(oldFactionOfFactionstatCollectionNewFactionstat);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = faction.getId();
                if (findFaction(id) == null) {
                    throw new NonexistentEntityException("The faction with id " + id + " no longer exists.");
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
            Faction faction;
            try {
                faction = em.getReference(Faction.class, id);
                faction.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The faction with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Factionstat> factionstatCollectionOrphanCheck = faction.getFactionstatCollection();
            for (Factionstat factionstatCollectionOrphanCheckFactionstat : factionstatCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Faction (" + faction + ") cannot be destroyed since the Factionstat " + factionstatCollectionOrphanCheckFactionstat + " in its factionstatCollection field has a non-nullable faction field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(faction);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Faction> findFactionEntities() {
        return findFactionEntities(true, -1, -1);
    }

    public List<Faction> findFactionEntities(int maxResults, int firstResult) {
        return findFactionEntities(false, maxResults, firstResult);
    }

    private List<Faction> findFactionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Faction.class));
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

    public Faction findFaction(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Faction.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Faction> rt = cq.from(Faction.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
