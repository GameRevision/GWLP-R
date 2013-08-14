/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Faction;
import gwlpr.database.entities.Account;
import gwlpr.database.entities.Factionstat;
import gwlpr.database.entities.FactionstatPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class FactionstatJpaController implements Serializable 
{

    public FactionstatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factionstat factionstat) throws PreexistingEntityException, Exception {
        if (factionstat.getFactionstatPK() == null) {
            factionstat.setFactionstatPK(new FactionstatPK());
        }
        factionstat.getFactionstatPK().setType(factionstat.getFaction().getId());
        factionstat.getFactionstatPK().setAccountID(factionstat.getAccount().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Faction faction = factionstat.getFaction();
            if (faction != null) {
                faction = em.getReference(faction.getClass(), faction.getId());
                factionstat.setFaction(faction);
            }
            Account account = factionstat.getAccount();
            if (account != null) {
                account = em.getReference(account.getClass(), account.getEMail());
                factionstat.setAccount(account);
            }
            em.persist(factionstat);
            if (faction != null) {
                faction.getFactionstatCollection().add(factionstat);
                faction = em.merge(faction);
            }
            if (account != null) {
                account.getFactionstatCollection().add(factionstat);
                account = em.merge(account);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFactionstat(factionstat.getFactionstatPK()) != null) {
                throw new PreexistingEntityException("Factionstat " + factionstat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factionstat factionstat) throws NonexistentEntityException, Exception {
        factionstat.getFactionstatPK().setType(factionstat.getFaction().getId());
        factionstat.getFactionstatPK().setAccountID(factionstat.getAccount().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factionstat persistentFactionstat = em.find(Factionstat.class, factionstat.getFactionstatPK());
            Faction factionOld = persistentFactionstat.getFaction();
            Faction factionNew = factionstat.getFaction();
            Account accountOld = persistentFactionstat.getAccount();
            Account accountNew = factionstat.getAccount();
            if (factionNew != null) {
                factionNew = em.getReference(factionNew.getClass(), factionNew.getId());
                factionstat.setFaction(factionNew);
            }
            if (accountNew != null) {
                accountNew = em.getReference(accountNew.getClass(), accountNew.getEMail());
                factionstat.setAccount(accountNew);
            }
            factionstat = em.merge(factionstat);
            if (factionOld != null && !factionOld.equals(factionNew)) {
                factionOld.getFactionstatCollection().remove(factionstat);
                factionOld = em.merge(factionOld);
            }
            if (factionNew != null && !factionNew.equals(factionOld)) {
                factionNew.getFactionstatCollection().add(factionstat);
                factionNew = em.merge(factionNew);
            }
            if (accountOld != null && !accountOld.equals(accountNew)) {
                accountOld.getFactionstatCollection().remove(factionstat);
                accountOld = em.merge(accountOld);
            }
            if (accountNew != null && !accountNew.equals(accountOld)) {
                accountNew.getFactionstatCollection().add(factionstat);
                accountNew = em.merge(accountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FactionstatPK id = factionstat.getFactionstatPK();
                if (findFactionstat(id) == null) {
                    throw new NonexistentEntityException("The factionstat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FactionstatPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factionstat factionstat;
            try {
                factionstat = em.getReference(Factionstat.class, id);
                factionstat.getFactionstatPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factionstat with id " + id + " no longer exists.", enfe);
            }
            Faction faction = factionstat.getFaction();
            if (faction != null) {
                faction.getFactionstatCollection().remove(factionstat);
                faction = em.merge(faction);
            }
            Account account = factionstat.getAccount();
            if (account != null) {
                account.getFactionstatCollection().remove(factionstat);
                account = em.merge(account);
            }
            em.remove(factionstat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factionstat> findFactionstatEntities() {
        return findFactionstatEntities(true, -1, -1);
    }

    public List<Factionstat> findFactionstatEntities(int maxResults, int firstResult) {
        return findFactionstatEntities(false, maxResults, firstResult);
    }

    private List<Factionstat> findFactionstatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factionstat.class));
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

    public Factionstat findFactionstat(FactionstatPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factionstat.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactionstatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factionstat> rt = cq.from(Factionstat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
