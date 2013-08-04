/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Command;
import gwlpr.database.entities.UserGroup;
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
public class UserGroupJpaController implements Serializable 
{

    public UserGroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserGroup userGroup) {
        if (userGroup.getCommandCollection() == null) {
            userGroup.setCommandCollection(new ArrayList<Command>());
        }
        if (userGroup.getCommandsCollection() == null) {
            userGroup.setCommandsCollection(new ArrayList<Command>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Command> attachedCommandCollection = new ArrayList<Command>();
            for (Command commandCollectionCommandToAttach : userGroup.getCommandCollection()) {
                commandCollectionCommandToAttach = em.getReference(commandCollectionCommandToAttach.getClass(), commandCollectionCommandToAttach.getName());
                attachedCommandCollection.add(commandCollectionCommandToAttach);
            }
            userGroup.setCommandCollection(attachedCommandCollection);
            Collection<Command> attachedCommandsCollection = new ArrayList<Command>();
            for (Command commandsCollectionCommandToAttach : userGroup.getCommandsCollection()) {
                commandsCollectionCommandToAttach = em.getReference(commandsCollectionCommandToAttach.getClass(), commandsCollectionCommandToAttach.getName());
                attachedCommandsCollection.add(commandsCollectionCommandToAttach);
            }
            userGroup.setCommandsCollection(attachedCommandsCollection);
            em.persist(userGroup);
            for (Command commandCollectionCommand : userGroup.getCommandCollection()) {
                commandCollectionCommand.getUserGroupCollection().add(userGroup);
                commandCollectionCommand = em.merge(commandCollectionCommand);
            }
            for (Command commandsCollectionCommand : userGroup.getCommandsCollection()) {
                commandsCollectionCommand.getUserGroupCollection().add(userGroup);
                commandsCollectionCommand = em.merge(commandsCollectionCommand);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserGroup userGroup) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserGroup persistentUserGroup = em.find(UserGroup.class, userGroup.getId());
            Collection<Command> commandCollectionOld = persistentUserGroup.getCommandCollection();
            Collection<Command> commandCollectionNew = userGroup.getCommandCollection();
            Collection<Command> commandsCollectionOld = persistentUserGroup.getCommandsCollection();
            Collection<Command> commandsCollectionNew = userGroup.getCommandsCollection();
            Collection<Command> attachedCommandCollectionNew = new ArrayList<Command>();
            for (Command commandCollectionNewCommandToAttach : commandCollectionNew) {
                commandCollectionNewCommandToAttach = em.getReference(commandCollectionNewCommandToAttach.getClass(), commandCollectionNewCommandToAttach.getName());
                attachedCommandCollectionNew.add(commandCollectionNewCommandToAttach);
            }
            commandCollectionNew = attachedCommandCollectionNew;
            userGroup.setCommandCollection(commandCollectionNew);
            Collection<Command> attachedCommandsCollectionNew = new ArrayList<Command>();
            for (Command commandsCollectionNewCommandToAttach : commandsCollectionNew) {
                commandsCollectionNewCommandToAttach = em.getReference(commandsCollectionNewCommandToAttach.getClass(), commandsCollectionNewCommandToAttach.getName());
                attachedCommandsCollectionNew.add(commandsCollectionNewCommandToAttach);
            }
            commandsCollectionNew = attachedCommandsCollectionNew;
            userGroup.setCommandsCollection(commandsCollectionNew);
            userGroup = em.merge(userGroup);
            for (Command commandCollectionOldCommand : commandCollectionOld) {
                if (!commandCollectionNew.contains(commandCollectionOldCommand)) {
                    commandCollectionOldCommand.getUserGroupCollection().remove(userGroup);
                    commandCollectionOldCommand = em.merge(commandCollectionOldCommand);
                }
            }
            for (Command commandCollectionNewCommand : commandCollectionNew) {
                if (!commandCollectionOld.contains(commandCollectionNewCommand)) {
                    commandCollectionNewCommand.getUserGroupCollection().add(userGroup);
                    commandCollectionNewCommand = em.merge(commandCollectionNewCommand);
                }
            }
            for (Command commandsCollectionOldCommand : commandsCollectionOld) {
                if (!commandsCollectionNew.contains(commandsCollectionOldCommand)) {
                    commandsCollectionOldCommand.getUserGroupCollection().remove(userGroup);
                    commandsCollectionOldCommand = em.merge(commandsCollectionOldCommand);
                }
            }
            for (Command commandsCollectionNewCommand : commandsCollectionNew) {
                if (!commandsCollectionOld.contains(commandsCollectionNewCommand)) {
                    commandsCollectionNewCommand.getUserGroupCollection().add(userGroup);
                    commandsCollectionNewCommand = em.merge(commandsCollectionNewCommand);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userGroup.getId();
                if (findUserGroup(id) == null) {
                    throw new NonexistentEntityException("The userGroup with id " + id + " no longer exists.");
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
            UserGroup userGroup;
            try {
                userGroup = em.getReference(UserGroup.class, id);
                userGroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userGroup with id " + id + " no longer exists.", enfe);
            }
            Collection<Command> commandCollection = userGroup.getCommandCollection();
            for (Command commandCollectionCommand : commandCollection) {
                commandCollectionCommand.getUserGroupCollection().remove(userGroup);
                commandCollectionCommand = em.merge(commandCollectionCommand);
            }
            Collection<Command> commandsCollection = userGroup.getCommandsCollection();
            for (Command commandsCollectionCommand : commandsCollection) {
                commandsCollectionCommand.getUserGroupCollection().remove(userGroup);
                commandsCollectionCommand = em.merge(commandsCollectionCommand);
            }
            em.remove(userGroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserGroup> findUserGroupEntities() {
        return findUserGroupEntities(true, -1, -1);
    }

    public List<UserGroup> findUserGroupEntities(int maxResults, int firstResult) {
        return findUserGroupEntities(false, maxResults, firstResult);
    }

    private List<UserGroup> findUserGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserGroup.class));
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

    public UserGroup findUserGroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserGroup> rt = cq.from(UserGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
