/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.entities.Attribute;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Profession;
import gwlpr.database.entities.Skill;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Attributepoint;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class AttributeJpaController implements Serializable 
{

    public AttributeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attribute attribute) throws PreexistingEntityException, Exception {
        if (attribute.getSkillCollection() == null) {
            attribute.setSkillCollection(new ArrayList<Skill>());
        }
        if (attribute.getAttributepointCollection() == null) {
            attribute.setAttributepointCollection(new ArrayList<Attributepoint>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profession profession = attribute.getProfession();
            if (profession != null) {
                profession = em.getReference(profession.getClass(), profession.getId());
                attribute.setProfession(profession);
            }
            Collection<Skill> attachedSkillCollection = new ArrayList<Skill>();
            for (Skill skillCollectionSkillToAttach : attribute.getSkillCollection()) {
                skillCollectionSkillToAttach = em.getReference(skillCollectionSkillToAttach.getClass(), skillCollectionSkillToAttach.getId());
                attachedSkillCollection.add(skillCollectionSkillToAttach);
            }
            attribute.setSkillCollection(attachedSkillCollection);
            Collection<Attributepoint> attachedAttributepointCollection = new ArrayList<Attributepoint>();
            for (Attributepoint attributepointCollectionAttributepointToAttach : attribute.getAttributepointCollection()) {
                attributepointCollectionAttributepointToAttach = em.getReference(attributepointCollectionAttributepointToAttach.getClass(), attributepointCollectionAttributepointToAttach.getAttributepointPK());
                attachedAttributepointCollection.add(attributepointCollectionAttributepointToAttach);
            }
            attribute.setAttributepointCollection(attachedAttributepointCollection);
            em.persist(attribute);
            if (profession != null) {
                profession.getAttributeCollection().add(attribute);
                profession = em.merge(profession);
            }
            for (Skill skillCollectionSkill : attribute.getSkillCollection()) {
                Attribute oldAttributeOfSkillCollectionSkill = skillCollectionSkill.getAttribute();
                skillCollectionSkill.setAttribute(attribute);
                skillCollectionSkill = em.merge(skillCollectionSkill);
                if (oldAttributeOfSkillCollectionSkill != null) {
                    oldAttributeOfSkillCollectionSkill.getSkillCollection().remove(skillCollectionSkill);
                    oldAttributeOfSkillCollectionSkill = em.merge(oldAttributeOfSkillCollectionSkill);
                }
            }
            for (Attributepoint attributepointCollectionAttributepoint : attribute.getAttributepointCollection()) {
                Attribute oldAttributeOfAttributepointCollectionAttributepoint = attributepointCollectionAttributepoint.getAttribute();
                attributepointCollectionAttributepoint.setAttribute(attribute);
                attributepointCollectionAttributepoint = em.merge(attributepointCollectionAttributepoint);
                if (oldAttributeOfAttributepointCollectionAttributepoint != null) {
                    oldAttributeOfAttributepointCollectionAttributepoint.getAttributepointCollection().remove(attributepointCollectionAttributepoint);
                    oldAttributeOfAttributepointCollectionAttributepoint = em.merge(oldAttributeOfAttributepointCollectionAttributepoint);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAttribute(attribute.getId()) != null) {
                throw new PreexistingEntityException("Attribute " + attribute + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attribute attribute) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribute persistentAttribute = em.find(Attribute.class, attribute.getId());
            Profession professionOld = persistentAttribute.getProfession();
            Profession professionNew = attribute.getProfession();
            Collection<Skill> skillCollectionOld = persistentAttribute.getSkillCollection();
            Collection<Skill> skillCollectionNew = attribute.getSkillCollection();
            Collection<Attributepoint> attributepointCollectionOld = persistentAttribute.getAttributepointCollection();
            Collection<Attributepoint> attributepointCollectionNew = attribute.getAttributepointCollection();
            List<String> illegalOrphanMessages = null;
            for (Skill skillCollectionOldSkill : skillCollectionOld) {
                if (!skillCollectionNew.contains(skillCollectionOldSkill)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Skill " + skillCollectionOldSkill + " since its attribute field is not nullable.");
                }
            }
            for (Attributepoint attributepointCollectionOldAttributepoint : attributepointCollectionOld) {
                if (!attributepointCollectionNew.contains(attributepointCollectionOldAttributepoint)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attributepoint " + attributepointCollectionOldAttributepoint + " since its attribute field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (professionNew != null) {
                professionNew = em.getReference(professionNew.getClass(), professionNew.getId());
                attribute.setProfession(professionNew);
            }
            Collection<Skill> attachedSkillCollectionNew = new ArrayList<Skill>();
            for (Skill skillCollectionNewSkillToAttach : skillCollectionNew) {
                skillCollectionNewSkillToAttach = em.getReference(skillCollectionNewSkillToAttach.getClass(), skillCollectionNewSkillToAttach.getId());
                attachedSkillCollectionNew.add(skillCollectionNewSkillToAttach);
            }
            skillCollectionNew = attachedSkillCollectionNew;
            attribute.setSkillCollection(skillCollectionNew);
            Collection<Attributepoint> attachedAttributepointCollectionNew = new ArrayList<Attributepoint>();
            for (Attributepoint attributepointCollectionNewAttributepointToAttach : attributepointCollectionNew) {
                attributepointCollectionNewAttributepointToAttach = em.getReference(attributepointCollectionNewAttributepointToAttach.getClass(), attributepointCollectionNewAttributepointToAttach.getAttributepointPK());
                attachedAttributepointCollectionNew.add(attributepointCollectionNewAttributepointToAttach);
            }
            attributepointCollectionNew = attachedAttributepointCollectionNew;
            attribute.setAttributepointCollection(attributepointCollectionNew);
            attribute = em.merge(attribute);
            if (professionOld != null && !professionOld.equals(professionNew)) {
                professionOld.getAttributeCollection().remove(attribute);
                professionOld = em.merge(professionOld);
            }
            if (professionNew != null && !professionNew.equals(professionOld)) {
                professionNew.getAttributeCollection().add(attribute);
                professionNew = em.merge(professionNew);
            }
            for (Skill skillCollectionNewSkill : skillCollectionNew) {
                if (!skillCollectionOld.contains(skillCollectionNewSkill)) {
                    Attribute oldAttributeOfSkillCollectionNewSkill = skillCollectionNewSkill.getAttribute();
                    skillCollectionNewSkill.setAttribute(attribute);
                    skillCollectionNewSkill = em.merge(skillCollectionNewSkill);
                    if (oldAttributeOfSkillCollectionNewSkill != null && !oldAttributeOfSkillCollectionNewSkill.equals(attribute)) {
                        oldAttributeOfSkillCollectionNewSkill.getSkillCollection().remove(skillCollectionNewSkill);
                        oldAttributeOfSkillCollectionNewSkill = em.merge(oldAttributeOfSkillCollectionNewSkill);
                    }
                }
            }
            for (Attributepoint attributepointCollectionNewAttributepoint : attributepointCollectionNew) {
                if (!attributepointCollectionOld.contains(attributepointCollectionNewAttributepoint)) {
                    Attribute oldAttributeOfAttributepointCollectionNewAttributepoint = attributepointCollectionNewAttributepoint.getAttribute();
                    attributepointCollectionNewAttributepoint.setAttribute(attribute);
                    attributepointCollectionNewAttributepoint = em.merge(attributepointCollectionNewAttributepoint);
                    if (oldAttributeOfAttributepointCollectionNewAttributepoint != null && !oldAttributeOfAttributepointCollectionNewAttributepoint.equals(attribute)) {
                        oldAttributeOfAttributepointCollectionNewAttributepoint.getAttributepointCollection().remove(attributepointCollectionNewAttributepoint);
                        oldAttributeOfAttributepointCollectionNewAttributepoint = em.merge(oldAttributeOfAttributepointCollectionNewAttributepoint);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attribute.getId();
                if (findAttribute(id) == null) {
                    throw new NonexistentEntityException("The attribute with id " + id + " no longer exists.");
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
            Attribute attribute;
            try {
                attribute = em.getReference(Attribute.class, id);
                attribute.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attribute with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Skill> skillCollectionOrphanCheck = attribute.getSkillCollection();
            for (Skill skillCollectionOrphanCheckSkill : skillCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attribute (" + attribute + ") cannot be destroyed since the Skill " + skillCollectionOrphanCheckSkill + " in its skillCollection field has a non-nullable attribute field.");
            }
            Collection<Attributepoint> attributepointCollectionOrphanCheck = attribute.getAttributepointCollection();
            for (Attributepoint attributepointCollectionOrphanCheckAttributepoint : attributepointCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attribute (" + attribute + ") cannot be destroyed since the Attributepoint " + attributepointCollectionOrphanCheckAttributepoint + " in its attributepointCollection field has a non-nullable attribute field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Profession profession = attribute.getProfession();
            if (profession != null) {
                profession.getAttributeCollection().remove(attribute);
                profession = em.merge(profession);
            }
            em.remove(attribute);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attribute> findAttributeEntities() {
        return findAttributeEntities(true, -1, -1);
    }

    public List<Attribute> findAttributeEntities(int maxResults, int firstResult) {
        return findAttributeEntities(false, maxResults, firstResult);
    }

    private List<Attribute> findAttributeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attribute.class));
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

    public Attribute findAttribute(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attribute.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttributeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attribute> rt = cq.from(Attribute.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
