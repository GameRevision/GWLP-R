/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.EntityManagerFactoryProvider;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Spawnpoint;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Character;
import gwlpr.database.entities.Map;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


/**
 *
 * @author _rusty
 */
public class MapJpaController implements Serializable 
{

    private static final MapJpaController SINGLETON = new MapJpaController(EntityManagerFactoryProvider.get());
    
    public static MapJpaController get() {
        return SINGLETON;
    }
    
    public MapJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Map map) {
        if (map.getSpawnpointCollection() == null) {
            map.setSpawnpointCollection(new ArrayList<Spawnpoint>());
        }
        if (map.getCharacterCollection() == null) {
            map.setCharacterCollection(new ArrayList<Character>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Spawnpoint> attachedSpawnpointCollection = new ArrayList<Spawnpoint>();
            for (Spawnpoint spawnpointCollectionSpawnpointToAttach : map.getSpawnpointCollection()) {
                spawnpointCollectionSpawnpointToAttach = em.getReference(spawnpointCollectionSpawnpointToAttach.getClass(), spawnpointCollectionSpawnpointToAttach.getSpawnpointPK());
                attachedSpawnpointCollection.add(spawnpointCollectionSpawnpointToAttach);
            }
            map.setSpawnpointCollection(attachedSpawnpointCollection);
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : map.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            map.setCharacterCollection(attachedCharacterCollection);
            em.persist(map);
            for (Spawnpoint spawnpointCollectionSpawnpoint : map.getSpawnpointCollection()) {
                Map oldMapOfSpawnpointCollectionSpawnpoint = spawnpointCollectionSpawnpoint.getMap();
                spawnpointCollectionSpawnpoint.setMap(map);
                spawnpointCollectionSpawnpoint = em.merge(spawnpointCollectionSpawnpoint);
                if (oldMapOfSpawnpointCollectionSpawnpoint != null) {
                    oldMapOfSpawnpointCollectionSpawnpoint.getSpawnpointCollection().remove(spawnpointCollectionSpawnpoint);
                    oldMapOfSpawnpointCollectionSpawnpoint = em.merge(oldMapOfSpawnpointCollectionSpawnpoint);
                }
            }
            for (Character characterCollectionCharacter : map.getCharacterCollection()) {
                Map oldLastOutpostOfCharacterCollectionCharacter = characterCollectionCharacter.getLastOutpost();
                characterCollectionCharacter.setLastOutpost(map);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
                if (oldLastOutpostOfCharacterCollectionCharacter != null) {
                    oldLastOutpostOfCharacterCollectionCharacter.getCharacterCollection().remove(characterCollectionCharacter);
                    oldLastOutpostOfCharacterCollectionCharacter = em.merge(oldLastOutpostOfCharacterCollectionCharacter);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Map map) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Map persistentMap = em.find(Map.class, map.getId());
            Collection<Spawnpoint> spawnpointCollectionOld = persistentMap.getSpawnpointCollection();
            Collection<Spawnpoint> spawnpointCollectionNew = map.getSpawnpointCollection();
            Collection<Character> characterCollectionOld = persistentMap.getCharacterCollection();
            Collection<Character> characterCollectionNew = map.getCharacterCollection();
            List<String> illegalOrphanMessages = null;
            for (Spawnpoint spawnpointCollectionOldSpawnpoint : spawnpointCollectionOld) {
                if (!spawnpointCollectionNew.contains(spawnpointCollectionOldSpawnpoint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spawnpoint " + spawnpointCollectionOldSpawnpoint + " since its map field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Spawnpoint> attachedSpawnpointCollectionNew = new ArrayList<Spawnpoint>();
            for (Spawnpoint spawnpointCollectionNewSpawnpointToAttach : spawnpointCollectionNew) {
                spawnpointCollectionNewSpawnpointToAttach = em.getReference(spawnpointCollectionNewSpawnpointToAttach.getClass(), spawnpointCollectionNewSpawnpointToAttach.getSpawnpointPK());
                attachedSpawnpointCollectionNew.add(spawnpointCollectionNewSpawnpointToAttach);
            }
            spawnpointCollectionNew = attachedSpawnpointCollectionNew;
            map.setSpawnpointCollection(spawnpointCollectionNew);
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            map.setCharacterCollection(characterCollectionNew);
            map = em.merge(map);
            for (Spawnpoint spawnpointCollectionNewSpawnpoint : spawnpointCollectionNew) {
                if (!spawnpointCollectionOld.contains(spawnpointCollectionNewSpawnpoint)) {
                    Map oldMapOfSpawnpointCollectionNewSpawnpoint = spawnpointCollectionNewSpawnpoint.getMap();
                    spawnpointCollectionNewSpawnpoint.setMap(map);
                    spawnpointCollectionNewSpawnpoint = em.merge(spawnpointCollectionNewSpawnpoint);
                    if (oldMapOfSpawnpointCollectionNewSpawnpoint != null && !oldMapOfSpawnpointCollectionNewSpawnpoint.equals(map)) {
                        oldMapOfSpawnpointCollectionNewSpawnpoint.getSpawnpointCollection().remove(spawnpointCollectionNewSpawnpoint);
                        oldMapOfSpawnpointCollectionNewSpawnpoint = em.merge(oldMapOfSpawnpointCollectionNewSpawnpoint);
                    }
                }
            }
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    characterCollectionOldCharacter.setLastOutpost(null);
                    characterCollectionOldCharacter = em.merge(characterCollectionOldCharacter);
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    Map oldLastOutpostOfCharacterCollectionNewCharacter = characterCollectionNewCharacter.getLastOutpost();
                    characterCollectionNewCharacter.setLastOutpost(map);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                    if (oldLastOutpostOfCharacterCollectionNewCharacter != null && !oldLastOutpostOfCharacterCollectionNewCharacter.equals(map)) {
                        oldLastOutpostOfCharacterCollectionNewCharacter.getCharacterCollection().remove(characterCollectionNewCharacter);
                        oldLastOutpostOfCharacterCollectionNewCharacter = em.merge(oldLastOutpostOfCharacterCollectionNewCharacter);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = map.getId();
                if (findMap(id) == null) {
                    throw new NonexistentEntityException("The map with id " + id + " no longer exists.");
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
            Map map;
            try {
                map = em.getReference(Map.class, id);
                map.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The map with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Spawnpoint> spawnpointCollectionOrphanCheck = map.getSpawnpointCollection();
            for (Spawnpoint spawnpointCollectionOrphanCheckSpawnpoint : spawnpointCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Map (" + map + ") cannot be destroyed since the Spawnpoint " + spawnpointCollectionOrphanCheckSpawnpoint + " in its spawnpointCollection field has a non-nullable map field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Character> characterCollection = map.getCharacterCollection();
            for (Character characterCollectionCharacter : characterCollection) {
                characterCollectionCharacter.setLastOutpost(null);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            em.remove(map);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Map> findMapEntities() {
        return findMapEntities(true, -1, -1);
    }

    public List<Map> findMapEntities(int maxResults, int firstResult) {
        return findMapEntities(false, maxResults, firstResult);
    }

    private List<Map> findMapEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Map.class));
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

    public Map findMap(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Map.class, id);
        } finally {
            em.close();
        }
    }
    
    public Map findByGameId(int gameId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Map> query = getEntityManager().createNamedQuery("Map.findByGameID", Map.class);
            query.setParameter("gameID", gameId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getMapCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Map> rt = cq.from(Map.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
