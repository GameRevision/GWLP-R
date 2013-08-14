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
import gwlpr.database.entities.Character;
import gwlpr.database.entities.Attribute;
import gwlpr.database.entities.Attributepoint;
import gwlpr.database.entities.AttributepointPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class AttributepointJpaController implements Serializable 
{
    private static final AttributepointJpaController SINGLETON = new AttributepointJpaController(EntityManagerFactoryProvider.get());
    
    public static AttributepointJpaController get() {
        return SINGLETON;
    }
    
    private AttributepointJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attributepoint attributepoint) throws PreexistingEntityException, Exception {
        if (attributepoint.getAttributepointPK() == null) {
            attributepoint.setAttributepointPK(new AttributepointPK());
        }
        attributepoint.getAttributepointPK().setCharacterID(attributepoint.getCharacter().getId());
        attributepoint.getAttributepointPK().setAttributeID(attributepoint.getAttribute().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Character character = attributepoint.getCharacter();
            if (character != null) {
                character = em.getReference(character.getClass(), character.getId());
                attributepoint.setCharacter(character);
            }
            Attribute attribute = attributepoint.getAttribute();
            if (attribute != null) {
                attribute = em.getReference(attribute.getClass(), attribute.getId());
                attributepoint.setAttribute(attribute);
            }
            em.persist(attributepoint);
            if (character != null) {
                character.getAttributepointCollection().add(attributepoint);
                character = em.merge(character);
            }
            if (attribute != null) {
                attribute.getAttributepointCollection().add(attributepoint);
                attribute = em.merge(attribute);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAttributepoint(attributepoint.getAttributepointPK()) != null) {
                throw new PreexistingEntityException("Attributepoint " + attributepoint + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attributepoint attributepoint) throws NonexistentEntityException, Exception {
        attributepoint.getAttributepointPK().setCharacterID(attributepoint.getCharacter().getId());
        attributepoint.getAttributepointPK().setAttributeID(attributepoint.getAttribute().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attributepoint persistentAttributepoint = em.find(Attributepoint.class, attributepoint.getAttributepointPK());
            Character characterOld = persistentAttributepoint.getCharacter();
            Character characterNew = attributepoint.getCharacter();
            Attribute attributeOld = persistentAttributepoint.getAttribute();
            Attribute attributeNew = attributepoint.getAttribute();
            if (characterNew != null) {
                characterNew = em.getReference(characterNew.getClass(), characterNew.getId());
                attributepoint.setCharacter(characterNew);
            }
            if (attributeNew != null) {
                attributeNew = em.getReference(attributeNew.getClass(), attributeNew.getId());
                attributepoint.setAttribute(attributeNew);
            }
            attributepoint = em.merge(attributepoint);
            if (characterOld != null && !characterOld.equals(characterNew)) {
                characterOld.getAttributepointCollection().remove(attributepoint);
                characterOld = em.merge(characterOld);
            }
            if (characterNew != null && !characterNew.equals(characterOld)) {
                characterNew.getAttributepointCollection().add(attributepoint);
                characterNew = em.merge(characterNew);
            }
            if (attributeOld != null && !attributeOld.equals(attributeNew)) {
                attributeOld.getAttributepointCollection().remove(attributepoint);
                attributeOld = em.merge(attributeOld);
            }
            if (attributeNew != null && !attributeNew.equals(attributeOld)) {
                attributeNew.getAttributepointCollection().add(attributepoint);
                attributeNew = em.merge(attributeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AttributepointPK id = attributepoint.getAttributepointPK();
                if (findAttributepoint(id) == null) {
                    throw new NonexistentEntityException("The attributepoint with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AttributepointPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attributepoint attributepoint;
            try {
                attributepoint = em.getReference(Attributepoint.class, id);
                attributepoint.getAttributepointPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attributepoint with id " + id + " no longer exists.", enfe);
            }
            Character character = attributepoint.getCharacter();
            if (character != null) {
                character.getAttributepointCollection().remove(attributepoint);
                character = em.merge(character);
            }
            Attribute attribute = attributepoint.getAttribute();
            if (attribute != null) {
                attribute.getAttributepointCollection().remove(attributepoint);
                attribute = em.merge(attribute);
            }
            em.remove(attributepoint);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attributepoint> findAttributepointEntities() {
        return findAttributepointEntities(true, -1, -1);
    }

    public List<Attributepoint> findAttributepointEntities(int maxResults, int firstResult) {
        return findAttributepointEntities(false, maxResults, firstResult);
    }

    private List<Attributepoint> findAttributepointEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attributepoint.class));
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

    public Attributepoint findAttributepoint(AttributepointPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attributepoint.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttributepointCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attributepoint> rt = cq.from(Attributepoint.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
