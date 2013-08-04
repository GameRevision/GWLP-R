/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.entities.Command;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.UserGroup;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class CommandJpaController implements Serializable 
{

    public CommandJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Command command) throws PreexistingEntityException, Exception {
        if (command.getUserGroupCollection() == null) {
            command.setUserGroupCollection(new ArrayList<UserGroup>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<UserGroup> attachedUserGroupCollection = new ArrayList<UserGroup>();
            for (UserGroup userGroupCollectionUserGroupToAttach : command.getUserGroupCollection()) {
                userGroupCollectionUserGroupToAttach = em.getReference(userGroupCollectionUserGroupToAttach.getClass(), userGroupCollectionUserGroupToAttach.getId());
                attachedUserGroupCollection.add(userGroupCollectionUserGroupToAttach);
            }
            command.setUserGroupCollection(attachedUserGroupCollection);
            em.persist(command);
            for (UserGroup userGroupCollectionUserGroup : command.getUserGroupCollection()) {
                userGroupCollectionUserGroup.getCommandCollection().add(command);
                userGroupCollectionUserGroup = em.merge(userGroupCollectionUserGroup);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCommand(command.getName()) != null) {
                throw new PreexistingEntityException("Command " + command + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Command command) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Command persistentCommand = em.find(Command.class, command.getName());
            Collection<UserGroup> userGroupCollectionOld = persistentCommand.getUserGroupCollection();
            Collection<UserGroup> userGroupCollectionNew = command.getUserGroupCollection();
            Collection<UserGroup> attachedUserGroupCollectionNew = new ArrayList<UserGroup>();
            for (UserGroup userGroupCollectionNewUserGroupToAttach : userGroupCollectionNew) {
                userGroupCollectionNewUserGroupToAttach = em.getReference(userGroupCollectionNewUserGroupToAttach.getClass(), userGroupCollectionNewUserGroupToAttach.getId());
                attachedUserGroupCollectionNew.add(userGroupCollectionNewUserGroupToAttach);
            }
            userGroupCollectionNew = attachedUserGroupCollectionNew;
            command.setUserGroupCollection(userGroupCollectionNew);
            command = em.merge(command);
            for (UserGroup userGroupCollectionOldUserGroup : userGroupCollectionOld) {
                if (!userGroupCollectionNew.contains(userGroupCollectionOldUserGroup)) {
                    userGroupCollectionOldUserGroup.getCommandCollection().remove(command);
                    userGroupCollectionOldUserGroup = em.merge(userGroupCollectionOldUserGroup);
                }
            }
            for (UserGroup userGroupCollectionNewUserGroup : userGroupCollectionNew) {
                if (!userGroupCollectionOld.contains(userGroupCollectionNewUserGroup)) {
                    userGroupCollectionNewUserGroup.getCommandCollection().add(command);
                    userGroupCollectionNewUserGroup = em.merge(userGroupCollectionNewUserGroup);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = command.getName();
                if (findCommand(id) == null) {
                    throw new NonexistentEntityException("The command with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Command command;
            try {
                command = em.getReference(Command.class, id);
                command.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The command with id " + id + " no longer exists.", enfe);
            }
            Collection<UserGroup> userGroupCollection = command.getUserGroupCollection();
            for (UserGroup userGroupCollectionUserGroup : userGroupCollection) {
                userGroupCollectionUserGroup.getCommandCollection().remove(command);
                userGroupCollectionUserGroup = em.merge(userGroupCollectionUserGroup);
            }
            em.remove(command);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Command> findCommandEntities() {
        return findCommandEntities(true, -1, -1);
    }

    public List<Command> findCommandEntities(int maxResults, int firstResult) {
        return findCommandEntities(false, maxResults, firstResult);
    }

    private List<Command> findCommandEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Command.class));
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

    public Command findCommand(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Command.class, id);
        } finally {
            em.close();
        }
    }

    public int getCommandCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Command> rt = cq.from(Command.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
