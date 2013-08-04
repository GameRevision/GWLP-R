/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Skill;
import gwlpr.database.entities.Character;
import gwlpr.database.entities.EquippedSkill;
import gwlpr.database.entities.EquippedSkillPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class EquippedSkillJpaController implements Serializable 
{

    public EquippedSkillJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EquippedSkill equippedSkill) throws PreexistingEntityException, Exception {
        if (equippedSkill.getEquippedSkillPK() == null) {
            equippedSkill.setEquippedSkillPK(new EquippedSkillPK());
        }
        equippedSkill.getEquippedSkillPK().setSkillID(equippedSkill.getSkill().getId());
        equippedSkill.getEquippedSkillPK().setCharacterID(equippedSkill.getCharacter().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Skill skill = equippedSkill.getSkill();
            if (skill != null) {
                skill = em.getReference(skill.getClass(), skill.getId());
                equippedSkill.setSkill(skill);
            }
            Character character = equippedSkill.getCharacter();
            if (character != null) {
                character = em.getReference(character.getClass(), character.getId());
                equippedSkill.setCharacter(character);
            }
            em.persist(equippedSkill);
            if (skill != null) {
                skill.getEquippedSkillCollection().add(equippedSkill);
                skill = em.merge(skill);
            }
            if (character != null) {
                character.getEquippedSkillCollection().add(equippedSkill);
                character = em.merge(character);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEquippedSkill(equippedSkill.getEquippedSkillPK()) != null) {
                throw new PreexistingEntityException("EquippedSkill " + equippedSkill + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EquippedSkill equippedSkill) throws NonexistentEntityException, Exception {
        equippedSkill.getEquippedSkillPK().setSkillID(equippedSkill.getSkill().getId());
        equippedSkill.getEquippedSkillPK().setCharacterID(equippedSkill.getCharacter().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquippedSkill persistentEquippedSkill = em.find(EquippedSkill.class, equippedSkill.getEquippedSkillPK());
            Skill skillOld = persistentEquippedSkill.getSkill();
            Skill skillNew = equippedSkill.getSkill();
            Character characterOld = persistentEquippedSkill.getCharacter();
            Character characterNew = equippedSkill.getCharacter();
            if (skillNew != null) {
                skillNew = em.getReference(skillNew.getClass(), skillNew.getId());
                equippedSkill.setSkill(skillNew);
            }
            if (characterNew != null) {
                characterNew = em.getReference(characterNew.getClass(), characterNew.getId());
                equippedSkill.setCharacter(characterNew);
            }
            equippedSkill = em.merge(equippedSkill);
            if (skillOld != null && !skillOld.equals(skillNew)) {
                skillOld.getEquippedSkillCollection().remove(equippedSkill);
                skillOld = em.merge(skillOld);
            }
            if (skillNew != null && !skillNew.equals(skillOld)) {
                skillNew.getEquippedSkillCollection().add(equippedSkill);
                skillNew = em.merge(skillNew);
            }
            if (characterOld != null && !characterOld.equals(characterNew)) {
                characterOld.getEquippedSkillCollection().remove(equippedSkill);
                characterOld = em.merge(characterOld);
            }
            if (characterNew != null && !characterNew.equals(characterOld)) {
                characterNew.getEquippedSkillCollection().add(equippedSkill);
                characterNew = em.merge(characterNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EquippedSkillPK id = equippedSkill.getEquippedSkillPK();
                if (findEquippedSkill(id) == null) {
                    throw new NonexistentEntityException("The equippedSkill with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EquippedSkillPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquippedSkill equippedSkill;
            try {
                equippedSkill = em.getReference(EquippedSkill.class, id);
                equippedSkill.getEquippedSkillPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equippedSkill with id " + id + " no longer exists.", enfe);
            }
            Skill skill = equippedSkill.getSkill();
            if (skill != null) {
                skill.getEquippedSkillCollection().remove(equippedSkill);
                skill = em.merge(skill);
            }
            Character character = equippedSkill.getCharacter();
            if (character != null) {
                character.getEquippedSkillCollection().remove(equippedSkill);
                character = em.merge(character);
            }
            em.remove(equippedSkill);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EquippedSkill> findEquippedSkillEntities() {
        return findEquippedSkillEntities(true, -1, -1);
    }

    public List<EquippedSkill> findEquippedSkillEntities(int maxResults, int firstResult) {
        return findEquippedSkillEntities(false, maxResults, firstResult);
    }

    private List<EquippedSkill> findEquippedSkillEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EquippedSkill.class));
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

    public EquippedSkill findEquippedSkill(EquippedSkillPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EquippedSkill.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquippedSkillCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EquippedSkill> rt = cq.from(EquippedSkill.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
