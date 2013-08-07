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
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Attribute;
import gwlpr.database.entities.Profession;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class ProfessionJpaController implements Serializable 
{
    private static final ProfessionJpaController SINGLETON = new ProfessionJpaController(EntityManagerFactoryProvider.get());
    
    public static ProfessionJpaController get() {
        return SINGLETON;
    }
    
    public ProfessionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profession profession) {
        if (profession.getCharacterCollection() == null) {
            profession.setCharacterCollection(new ArrayList<Character>());
        }
        if (profession.getAttributeCollection() == null) {
            profession.setAttributeCollection(new ArrayList<Attribute>());
        }
        if (profession.getCharacterCollection1() == null) {
            profession.setCharacterCollection1(new ArrayList<Character>());
        }
        if (profession.getCharacterCollection2() == null) {
            profession.setCharacterCollection2(new ArrayList<Character>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : profession.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            profession.setCharacterCollection(attachedCharacterCollection);
            Collection<Attribute> attachedAttributeCollection = new ArrayList<Attribute>();
            for (Attribute attributeCollectionAttributeToAttach : profession.getAttributeCollection()) {
                attributeCollectionAttributeToAttach = em.getReference(attributeCollectionAttributeToAttach.getClass(), attributeCollectionAttributeToAttach.getId());
                attachedAttributeCollection.add(attributeCollectionAttributeToAttach);
            }
            profession.setAttributeCollection(attachedAttributeCollection);
            Collection<Character> attachedCharacterCollection1 = new ArrayList<Character>();
            for (Character characterCollection1CharacterToAttach : profession.getCharacterCollection1()) {
                characterCollection1CharacterToAttach = em.getReference(characterCollection1CharacterToAttach.getClass(), characterCollection1CharacterToAttach.getId());
                attachedCharacterCollection1.add(characterCollection1CharacterToAttach);
            }
            profession.setCharacterCollection1(attachedCharacterCollection1);
            Collection<Character> attachedCharacterCollection2 = new ArrayList<Character>();
            for (Character characterCollection2CharacterToAttach : profession.getCharacterCollection2()) {
                characterCollection2CharacterToAttach = em.getReference(characterCollection2CharacterToAttach.getClass(), characterCollection2CharacterToAttach.getId());
                attachedCharacterCollection2.add(characterCollection2CharacterToAttach);
            }
            profession.setCharacterCollection2(attachedCharacterCollection2);
            em.persist(profession);
            for (Character characterCollectionCharacter : profession.getCharacterCollection()) {
                characterCollectionCharacter.getProfessionCollection().add(profession);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            for (Attribute attributeCollectionAttribute : profession.getAttributeCollection()) {
                Profession oldProfessionOfAttributeCollectionAttribute = attributeCollectionAttribute.getProfession();
                attributeCollectionAttribute.setProfession(profession);
                attributeCollectionAttribute = em.merge(attributeCollectionAttribute);
                if (oldProfessionOfAttributeCollectionAttribute != null) {
                    oldProfessionOfAttributeCollectionAttribute.getAttributeCollection().remove(attributeCollectionAttribute);
                    oldProfessionOfAttributeCollectionAttribute = em.merge(oldProfessionOfAttributeCollectionAttribute);
                }
            }
            for (Character characterCollection1Character : profession.getCharacterCollection1()) {
                Profession oldSecondaryProfessionOfCharacterCollection1Character = characterCollection1Character.getSecondaryProfession();
                characterCollection1Character.setSecondaryProfession(profession);
                characterCollection1Character = em.merge(characterCollection1Character);
                if (oldSecondaryProfessionOfCharacterCollection1Character != null) {
                    oldSecondaryProfessionOfCharacterCollection1Character.getCharacterCollection1().remove(characterCollection1Character);
                    oldSecondaryProfessionOfCharacterCollection1Character = em.merge(oldSecondaryProfessionOfCharacterCollection1Character);
                }
            }
            for (Character characterCollection2Character : profession.getCharacterCollection2()) {
                Profession oldPrimaryProfessionOfCharacterCollection2Character = characterCollection2Character.getPrimaryProfession();
                characterCollection2Character.setPrimaryProfession(profession);
                characterCollection2Character = em.merge(characterCollection2Character);
                if (oldPrimaryProfessionOfCharacterCollection2Character != null) {
                    oldPrimaryProfessionOfCharacterCollection2Character.getCharacterCollection2().remove(characterCollection2Character);
                    oldPrimaryProfessionOfCharacterCollection2Character = em.merge(oldPrimaryProfessionOfCharacterCollection2Character);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profession profession) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profession persistentProfession = em.find(Profession.class, profession.getId());
            Collection<Character> characterCollectionOld = persistentProfession.getCharacterCollection();
            Collection<Character> characterCollectionNew = profession.getCharacterCollection();
            Collection<Attribute> attributeCollectionOld = persistentProfession.getAttributeCollection();
            Collection<Attribute> attributeCollectionNew = profession.getAttributeCollection();
            Collection<Character> characterCollection1Old = persistentProfession.getCharacterCollection1();
            Collection<Character> characterCollection1New = profession.getCharacterCollection1();
            Collection<Character> characterCollection2Old = persistentProfession.getCharacterCollection2();
            Collection<Character> characterCollection2New = profession.getCharacterCollection2();
            List<String> illegalOrphanMessages = null;
            for (Attribute attributeCollectionOldAttribute : attributeCollectionOld) {
                if (!attributeCollectionNew.contains(attributeCollectionOldAttribute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attribute " + attributeCollectionOldAttribute + " since its profession field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            profession.setCharacterCollection(characterCollectionNew);
            Collection<Attribute> attachedAttributeCollectionNew = new ArrayList<Attribute>();
            for (Attribute attributeCollectionNewAttributeToAttach : attributeCollectionNew) {
                attributeCollectionNewAttributeToAttach = em.getReference(attributeCollectionNewAttributeToAttach.getClass(), attributeCollectionNewAttributeToAttach.getId());
                attachedAttributeCollectionNew.add(attributeCollectionNewAttributeToAttach);
            }
            attributeCollectionNew = attachedAttributeCollectionNew;
            profession.setAttributeCollection(attributeCollectionNew);
            Collection<Character> attachedCharacterCollection1New = new ArrayList<Character>();
            for (Character characterCollection1NewCharacterToAttach : characterCollection1New) {
                characterCollection1NewCharacterToAttach = em.getReference(characterCollection1NewCharacterToAttach.getClass(), characterCollection1NewCharacterToAttach.getId());
                attachedCharacterCollection1New.add(characterCollection1NewCharacterToAttach);
            }
            characterCollection1New = attachedCharacterCollection1New;
            profession.setCharacterCollection1(characterCollection1New);
            Collection<Character> attachedCharacterCollection2New = new ArrayList<Character>();
            for (Character characterCollection2NewCharacterToAttach : characterCollection2New) {
                characterCollection2NewCharacterToAttach = em.getReference(characterCollection2NewCharacterToAttach.getClass(), characterCollection2NewCharacterToAttach.getId());
                attachedCharacterCollection2New.add(characterCollection2NewCharacterToAttach);
            }
            characterCollection2New = attachedCharacterCollection2New;
            profession.setCharacterCollection2(characterCollection2New);
            profession = em.merge(profession);
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    characterCollectionOldCharacter.getProfessionCollection().remove(profession);
                    characterCollectionOldCharacter = em.merge(characterCollectionOldCharacter);
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    characterCollectionNewCharacter.getProfessionCollection().add(profession);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                }
            }
            for (Attribute attributeCollectionNewAttribute : attributeCollectionNew) {
                if (!attributeCollectionOld.contains(attributeCollectionNewAttribute)) {
                    Profession oldProfessionOfAttributeCollectionNewAttribute = attributeCollectionNewAttribute.getProfession();
                    attributeCollectionNewAttribute.setProfession(profession);
                    attributeCollectionNewAttribute = em.merge(attributeCollectionNewAttribute);
                    if (oldProfessionOfAttributeCollectionNewAttribute != null && !oldProfessionOfAttributeCollectionNewAttribute.equals(profession)) {
                        oldProfessionOfAttributeCollectionNewAttribute.getAttributeCollection().remove(attributeCollectionNewAttribute);
                        oldProfessionOfAttributeCollectionNewAttribute = em.merge(oldProfessionOfAttributeCollectionNewAttribute);
                    }
                }
            }
            for (Character characterCollection1OldCharacter : characterCollection1Old) {
                if (!characterCollection1New.contains(characterCollection1OldCharacter)) {
                    characterCollection1OldCharacter.setSecondaryProfession(null);
                    characterCollection1OldCharacter = em.merge(characterCollection1OldCharacter);
                }
            }
            for (Character characterCollection1NewCharacter : characterCollection1New) {
                if (!characterCollection1Old.contains(characterCollection1NewCharacter)) {
                    Profession oldSecondaryProfessionOfCharacterCollection1NewCharacter = characterCollection1NewCharacter.getSecondaryProfession();
                    characterCollection1NewCharacter.setSecondaryProfession(profession);
                    characterCollection1NewCharacter = em.merge(characterCollection1NewCharacter);
                    if (oldSecondaryProfessionOfCharacterCollection1NewCharacter != null && !oldSecondaryProfessionOfCharacterCollection1NewCharacter.equals(profession)) {
                        oldSecondaryProfessionOfCharacterCollection1NewCharacter.getCharacterCollection1().remove(characterCollection1NewCharacter);
                        oldSecondaryProfessionOfCharacterCollection1NewCharacter = em.merge(oldSecondaryProfessionOfCharacterCollection1NewCharacter);
                    }
                }
            }
            for (Character characterCollection2OldCharacter : characterCollection2Old) {
                if (!characterCollection2New.contains(characterCollection2OldCharacter)) {
                    characterCollection2OldCharacter.setPrimaryProfession(null);
                    characterCollection2OldCharacter = em.merge(characterCollection2OldCharacter);
                }
            }
            for (Character characterCollection2NewCharacter : characterCollection2New) {
                if (!characterCollection2Old.contains(characterCollection2NewCharacter)) {
                    Profession oldPrimaryProfessionOfCharacterCollection2NewCharacter = characterCollection2NewCharacter.getPrimaryProfession();
                    characterCollection2NewCharacter.setPrimaryProfession(profession);
                    characterCollection2NewCharacter = em.merge(characterCollection2NewCharacter);
                    if (oldPrimaryProfessionOfCharacterCollection2NewCharacter != null && !oldPrimaryProfessionOfCharacterCollection2NewCharacter.equals(profession)) {
                        oldPrimaryProfessionOfCharacterCollection2NewCharacter.getCharacterCollection2().remove(characterCollection2NewCharacter);
                        oldPrimaryProfessionOfCharacterCollection2NewCharacter = em.merge(oldPrimaryProfessionOfCharacterCollection2NewCharacter);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profession.getId();
                if (findProfession(id) == null) {
                    throw new NonexistentEntityException("The profession with id " + id + " no longer exists.");
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
            Profession profession;
            try {
                profession = em.getReference(Profession.class, id);
                profession.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profession with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Attribute> attributeCollectionOrphanCheck = profession.getAttributeCollection();
            for (Attribute attributeCollectionOrphanCheckAttribute : attributeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profession (" + profession + ") cannot be destroyed since the Attribute " + attributeCollectionOrphanCheckAttribute + " in its attributeCollection field has a non-nullable profession field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Character> characterCollection = profession.getCharacterCollection();
            for (Character characterCollectionCharacter : characterCollection) {
                characterCollectionCharacter.getProfessionCollection().remove(profession);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            Collection<Character> characterCollection1 = profession.getCharacterCollection1();
            for (Character characterCollection1Character : characterCollection1) {
                characterCollection1Character.setSecondaryProfession(null);
                characterCollection1Character = em.merge(characterCollection1Character);
            }
            Collection<Character> characterCollection2 = profession.getCharacterCollection2();
            for (Character characterCollection2Character : characterCollection2) {
                characterCollection2Character.setPrimaryProfession(null);
                characterCollection2Character = em.merge(characterCollection2Character);
            }
            em.remove(profession);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profession> findProfessionEntities() {
        return findProfessionEntities(true, -1, -1);
    }

    public List<Profession> findProfessionEntities(int maxResults, int firstResult) {
        return findProfessionEntities(false, maxResults, firstResult);
    }

    private List<Profession> findProfessionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profession.class));
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

    public Profession findProfession(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profession.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profession> rt = cq.from(Profession.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
