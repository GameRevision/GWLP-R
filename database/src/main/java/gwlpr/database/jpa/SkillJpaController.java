/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Attribute;
import gwlpr.database.entities.Character;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.EquippedSkill;
import gwlpr.database.entities.Skill;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class SkillJpaController implements Serializable 
{

    public SkillJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Skill skill) {
        if (skill.getCharacterCollection() == null) {
            skill.setCharacterCollection(new ArrayList<Character>());
        }
        if (skill.getEquippedSkillCollection() == null) {
            skill.setEquippedSkillCollection(new ArrayList<EquippedSkill>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribute attribute = skill.getAttribute();
            if (attribute != null) {
                attribute = em.getReference(attribute.getClass(), attribute.getId());
                skill.setAttribute(attribute);
            }
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : skill.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            skill.setCharacterCollection(attachedCharacterCollection);
            Collection<EquippedSkill> attachedEquippedSkillCollection = new ArrayList<EquippedSkill>();
            for (EquippedSkill equippedSkillCollectionEquippedSkillToAttach : skill.getEquippedSkillCollection()) {
                equippedSkillCollectionEquippedSkillToAttach = em.getReference(equippedSkillCollectionEquippedSkillToAttach.getClass(), equippedSkillCollectionEquippedSkillToAttach.getEquippedSkillPK());
                attachedEquippedSkillCollection.add(equippedSkillCollectionEquippedSkillToAttach);
            }
            skill.setEquippedSkillCollection(attachedEquippedSkillCollection);
            em.persist(skill);
            if (attribute != null) {
                attribute.getSkillCollection().add(skill);
                attribute = em.merge(attribute);
            }
            for (Character characterCollectionCharacter : skill.getCharacterCollection()) {
                characterCollectionCharacter.getSkillCollection().add(skill);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            for (EquippedSkill equippedSkillCollectionEquippedSkill : skill.getEquippedSkillCollection()) {
                Skill oldSkillOfEquippedSkillCollectionEquippedSkill = equippedSkillCollectionEquippedSkill.getSkill();
                equippedSkillCollectionEquippedSkill.setSkill(skill);
                equippedSkillCollectionEquippedSkill = em.merge(equippedSkillCollectionEquippedSkill);
                if (oldSkillOfEquippedSkillCollectionEquippedSkill != null) {
                    oldSkillOfEquippedSkillCollectionEquippedSkill.getEquippedSkillCollection().remove(equippedSkillCollectionEquippedSkill);
                    oldSkillOfEquippedSkillCollectionEquippedSkill = em.merge(oldSkillOfEquippedSkillCollectionEquippedSkill);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Skill skill) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Skill persistentSkill = em.find(Skill.class, skill.getId());
            Attribute attributeOld = persistentSkill.getAttribute();
            Attribute attributeNew = skill.getAttribute();
            Collection<Character> characterCollectionOld = persistentSkill.getCharacterCollection();
            Collection<Character> characterCollectionNew = skill.getCharacterCollection();
            Collection<EquippedSkill> equippedSkillCollectionOld = persistentSkill.getEquippedSkillCollection();
            Collection<EquippedSkill> equippedSkillCollectionNew = skill.getEquippedSkillCollection();
            List<String> illegalOrphanMessages = null;
            for (EquippedSkill equippedSkillCollectionOldEquippedSkill : equippedSkillCollectionOld) {
                if (!equippedSkillCollectionNew.contains(equippedSkillCollectionOldEquippedSkill)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EquippedSkill " + equippedSkillCollectionOldEquippedSkill + " since its skill field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attributeNew != null) {
                attributeNew = em.getReference(attributeNew.getClass(), attributeNew.getId());
                skill.setAttribute(attributeNew);
            }
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            skill.setCharacterCollection(characterCollectionNew);
            Collection<EquippedSkill> attachedEquippedSkillCollectionNew = new ArrayList<EquippedSkill>();
            for (EquippedSkill equippedSkillCollectionNewEquippedSkillToAttach : equippedSkillCollectionNew) {
                equippedSkillCollectionNewEquippedSkillToAttach = em.getReference(equippedSkillCollectionNewEquippedSkillToAttach.getClass(), equippedSkillCollectionNewEquippedSkillToAttach.getEquippedSkillPK());
                attachedEquippedSkillCollectionNew.add(equippedSkillCollectionNewEquippedSkillToAttach);
            }
            equippedSkillCollectionNew = attachedEquippedSkillCollectionNew;
            skill.setEquippedSkillCollection(equippedSkillCollectionNew);
            skill = em.merge(skill);
            if (attributeOld != null && !attributeOld.equals(attributeNew)) {
                attributeOld.getSkillCollection().remove(skill);
                attributeOld = em.merge(attributeOld);
            }
            if (attributeNew != null && !attributeNew.equals(attributeOld)) {
                attributeNew.getSkillCollection().add(skill);
                attributeNew = em.merge(attributeNew);
            }
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    characterCollectionOldCharacter.getSkillCollection().remove(skill);
                    characterCollectionOldCharacter = em.merge(characterCollectionOldCharacter);
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    characterCollectionNewCharacter.getSkillCollection().add(skill);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                }
            }
            for (EquippedSkill equippedSkillCollectionNewEquippedSkill : equippedSkillCollectionNew) {
                if (!equippedSkillCollectionOld.contains(equippedSkillCollectionNewEquippedSkill)) {
                    Skill oldSkillOfEquippedSkillCollectionNewEquippedSkill = equippedSkillCollectionNewEquippedSkill.getSkill();
                    equippedSkillCollectionNewEquippedSkill.setSkill(skill);
                    equippedSkillCollectionNewEquippedSkill = em.merge(equippedSkillCollectionNewEquippedSkill);
                    if (oldSkillOfEquippedSkillCollectionNewEquippedSkill != null && !oldSkillOfEquippedSkillCollectionNewEquippedSkill.equals(skill)) {
                        oldSkillOfEquippedSkillCollectionNewEquippedSkill.getEquippedSkillCollection().remove(equippedSkillCollectionNewEquippedSkill);
                        oldSkillOfEquippedSkillCollectionNewEquippedSkill = em.merge(oldSkillOfEquippedSkillCollectionNewEquippedSkill);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = skill.getId();
                if (findSkill(id) == null) {
                    throw new NonexistentEntityException("The skill with id " + id + " no longer exists.");
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
            Skill skill;
            try {
                skill = em.getReference(Skill.class, id);
                skill.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The skill with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EquippedSkill> equippedSkillCollectionOrphanCheck = skill.getEquippedSkillCollection();
            for (EquippedSkill equippedSkillCollectionOrphanCheckEquippedSkill : equippedSkillCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Skill (" + skill + ") cannot be destroyed since the EquippedSkill " + equippedSkillCollectionOrphanCheckEquippedSkill + " in its equippedSkillCollection field has a non-nullable skill field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Attribute attribute = skill.getAttribute();
            if (attribute != null) {
                attribute.getSkillCollection().remove(skill);
                attribute = em.merge(attribute);
            }
            Collection<Character> characterCollection = skill.getCharacterCollection();
            for (Character characterCollectionCharacter : characterCollection) {
                characterCollectionCharacter.getSkillCollection().remove(skill);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
            }
            em.remove(skill);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Skill> findSkillEntities() {
        return findSkillEntities(true, -1, -1);
    }

    public List<Skill> findSkillEntities(int maxResults, int firstResult) {
        return findSkillEntities(false, maxResults, firstResult);
    }

    private List<Skill> findSkillEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Skill.class));
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

    public Skill findSkill(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Skill.class, id);
        } finally {
            em.close();
        }
    }

    public int getSkillCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Skill> rt = cq.from(Skill.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
