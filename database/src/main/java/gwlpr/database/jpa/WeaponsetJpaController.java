/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Weapon;
import gwlpr.database.entities.Character;
import gwlpr.database.entities.Weaponset;
import gwlpr.database.entities.WeaponsetPK;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class WeaponsetJpaController implements Serializable 
{

    public WeaponsetJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Weaponset weaponset) throws PreexistingEntityException, Exception {
        if (weaponset.getWeaponsetPK() == null) {
            weaponset.setWeaponsetPK(new WeaponsetPK());
        }
        weaponset.getWeaponsetPK().setCharacterID(weaponset.getCharacter().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weapon weapons = weaponset.getWeapons();
            if (weapons != null) {
                weapons = em.getReference(weapons.getClass(), weapons.getId());
                weaponset.setWeapons(weapons);
            }
            Character character = weaponset.getCharacter();
            if (character != null) {
                character = em.getReference(character.getClass(), character.getId());
                weaponset.setCharacter(character);
            }
            em.persist(weaponset);
            if (weapons != null) {
                weapons.getWeaponsetCollection().add(weaponset);
                weapons = em.merge(weapons);
            }
            if (character != null) {
                character.getWeaponsetCollection().add(weaponset);
                character = em.merge(character);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWeaponset(weaponset.getWeaponsetPK()) != null) {
                throw new PreexistingEntityException("Weaponset " + weaponset + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Weaponset weaponset) throws NonexistentEntityException, Exception {
        weaponset.getWeaponsetPK().setCharacterID(weaponset.getCharacter().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weaponset persistentWeaponset = em.find(Weaponset.class, weaponset.getWeaponsetPK());
            Weapon weaponsOld = persistentWeaponset.getWeapons();
            Weapon weaponsNew = weaponset.getWeapons();
            Character characterOld = persistentWeaponset.getCharacter();
            Character characterNew = weaponset.getCharacter();
            if (weaponsNew != null) {
                weaponsNew = em.getReference(weaponsNew.getClass(), weaponsNew.getId());
                weaponset.setWeapons(weaponsNew);
            }
            if (characterNew != null) {
                characterNew = em.getReference(characterNew.getClass(), characterNew.getId());
                weaponset.setCharacter(characterNew);
            }
            weaponset = em.merge(weaponset);
            if (weaponsOld != null && !weaponsOld.equals(weaponsNew)) {
                weaponsOld.getWeaponsetCollection().remove(weaponset);
                weaponsOld = em.merge(weaponsOld);
            }
            if (weaponsNew != null && !weaponsNew.equals(weaponsOld)) {
                weaponsNew.getWeaponsetCollection().add(weaponset);
                weaponsNew = em.merge(weaponsNew);
            }
            if (characterOld != null && !characterOld.equals(characterNew)) {
                characterOld.getWeaponsetCollection().remove(weaponset);
                characterOld = em.merge(characterOld);
            }
            if (characterNew != null && !characterNew.equals(characterOld)) {
                characterNew.getWeaponsetCollection().add(weaponset);
                characterNew = em.merge(characterNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                WeaponsetPK id = weaponset.getWeaponsetPK();
                if (findWeaponset(id) == null) {
                    throw new NonexistentEntityException("The weaponset with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(WeaponsetPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weaponset weaponset;
            try {
                weaponset = em.getReference(Weaponset.class, id);
                weaponset.getWeaponsetPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The weaponset with id " + id + " no longer exists.", enfe);
            }
            Weapon weapons = weaponset.getWeapons();
            if (weapons != null) {
                weapons.getWeaponsetCollection().remove(weaponset);
                weapons = em.merge(weapons);
            }
            Character character = weaponset.getCharacter();
            if (character != null) {
                character.getWeaponsetCollection().remove(weaponset);
                character = em.merge(character);
            }
            em.remove(weaponset);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Weaponset> findWeaponsetEntities() {
        return findWeaponsetEntities(true, -1, -1);
    }

    public List<Weaponset> findWeaponsetEntities(int maxResults, int firstResult) {
        return findWeaponsetEntities(false, maxResults, firstResult);
    }

    private List<Weaponset> findWeaponsetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Weaponset.class));
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

    public Weaponset findWeaponset(WeaponsetPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Weaponset.class, id);
        } finally {
            em.close();
        }
    }

    public int getWeaponsetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Weaponset> rt = cq.from(Weaponset.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
