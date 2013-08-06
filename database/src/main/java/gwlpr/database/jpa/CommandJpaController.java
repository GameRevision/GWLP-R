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
import gwlpr.database.entities.Usergroup;
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
        if (command.getUsergroupCollection() == null) {
            command.setUsergroupCollection(new ArrayList<Usergroup>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usergroup> attachedUsergroupCollection = new ArrayList<Usergroup>();
            for (Usergroup usergroupCollectionUsergroupToAttach : command.getUsergroupCollection()) {
                usergroupCollectionUsergroupToAttach = em.getReference(usergroupCollectionUsergroupToAttach.getClass(), usergroupCollectionUsergroupToAttach.getId());
                attachedUsergroupCollection.add(usergroupCollectionUsergroupToAttach);
            }
            command.setUsergroupCollection(attachedUsergroupCollection);
            em.persist(command);
            for (Usergroup usergroupCollectionUsergroup : command.getUsergroupCollection()) {
                usergroupCollectionUsergroup.getCommandCollection().add(command);
                usergroupCollectionUsergroup = em.merge(usergroupCollectionUsergroup);
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
            Collection<Usergroup> usergroupCollectionOld = persistentCommand.getUsergroupCollection();
            Collection<Usergroup> usergroupCollectionNew = command.getUsergroupCollection();
            Collection<Usergroup> attachedUsergroupCollectionNew = new ArrayList<Usergroup>();
            for (Usergroup usergroupCollectionNewUsergroupToAttach : usergroupCollectionNew) {
                usergroupCollectionNewUsergroupToAttach = em.getReference(usergroupCollectionNewUsergroupToAttach.getClass(), usergroupCollectionNewUsergroupToAttach.getId());
                attachedUsergroupCollectionNew.add(usergroupCollectionNewUsergroupToAttach);
            }
            usergroupCollectionNew = attachedUsergroupCollectionNew;
            command.setUsergroupCollection(usergroupCollectionNew);
            command = em.merge(command);
            for (Usergroup usergroupCollectionOldUsergroup : usergroupCollectionOld) {
                if (!usergroupCollectionNew.contains(usergroupCollectionOldUsergroup)) {
                    usergroupCollectionOldUsergroup.getCommandCollection().remove(command);
                    usergroupCollectionOldUsergroup = em.merge(usergroupCollectionOldUsergroup);
                }
            }
            for (Usergroup usergroupCollectionNewUsergroup : usergroupCollectionNew) {
                if (!usergroupCollectionOld.contains(usergroupCollectionNewUsergroup)) {
                    usergroupCollectionNewUsergroup.getCommandCollection().add(command);
                    usergroupCollectionNewUsergroup = em.merge(usergroupCollectionNewUsergroup);
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
            Collection<Usergroup> usergroupCollection = command.getUsergroupCollection();
            for (Usergroup usergroupCollectionUsergroup : usergroupCollection) {
                usergroupCollectionUsergroup.getCommandCollection().remove(command);
                usergroupCollectionUsergroup = em.merge(usergroupCollectionUsergroup);
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
