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
import gwlpr.database.entities.Level;
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
public class LevelJpaController implements Serializable 
{

    public LevelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Level level) {
        if (level.getCharacterCollection() == null) {
            level.setCharacterCollection(new ArrayList<Character>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : level.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            level.setCharacterCollection(attachedCharacterCollection);
            em.persist(level);
            for (Character characterCollectionCharacter : level.getCharacterCollection()) {
                Level oldLevelOfCharacterCollectionCharacter = characterCollectionCharacter.getLevel();
                characterCollectionCharacter.setLevel(level);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
                if (oldLevelOfCharacterCollectionCharacter != null) {
                    oldLevelOfCharacterCollectionCharacter.getCharacterCollection().remove(characterCollectionCharacter);
                    oldLevelOfCharacterCollectionCharacter = em.merge(oldLevelOfCharacterCollectionCharacter);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Level level) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Level persistentLevel = em.find(Level.class, level.getLevel());
            Collection<Character> characterCollectionOld = persistentLevel.getCharacterCollection();
            Collection<Character> characterCollectionNew = level.getCharacterCollection();
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            level.setCharacterCollection(characterCollectionNew);
            level = em.merge(level);
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    characterCollectionOldCharacter.setLevel(null);
                    characterCollectionOldCharacter = em.merge(characterCollectionOldCharacter);
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    Level oldLevelOfCharacterCollectionNewCharacter = characterCollectionNewCharacter.getLevel();
                    characterCollectionNewCharacter.setLevel(level);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                    if (oldLevelOfCharacterCollectionNewCharacter != null && !oldLevelOfCharacterCollectionNewCharacter.equals(level)) {
                        oldLevelOfCharacterCollectionNewCharacter.getCharacterCollection().remove(characterCollectionNewCharacter);
                        oldLevelOfCharacterCollectionNewCharacter = em.merge(oldLevelOfCharacterCollectionNewCharacter);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = level.getLevel();
                if (findLevel(id) == null) {
                    throw new NonexistentEntityException("The level with id " + id + " no longer exists.");
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
            Level level;
            try {
                level = em.getReference(Level.class, id);
                level.getLevel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The level with id " + id + " no longer exists.", enfe);
            }
            Collection<Character> characterCollection = level.getCharacterCollection();
            for (Character characterCollectionCharacter : characterCollection) {
                characterCollectionCharacter.setLevel(null);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            em.remove(level);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Level> findLevelEntities() {
        return findLevelEntities(true, -1, -1);
    }

    public List<Level> findLevelEntities(int maxResults, int firstResult) {
        return findLevelEntities(false, maxResults, firstResult);
    }

    private List<Level> findLevelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Level.class));
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

    public Level findLevel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Level.class, id);
        } finally {
            em.close();
        }
    }

    public int getLevelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Level> rt = cq.from(Level.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
