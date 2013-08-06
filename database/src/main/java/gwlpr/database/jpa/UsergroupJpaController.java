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
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Account;
import gwlpr.database.entities.Usergroup;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author _rusty
 */
public class UsergroupJpaController implements Serializable 
{

    public UsergroupJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usergroup usergroup) {
        if (usergroup.getCommandCollection() == null) {
            usergroup.setCommandCollection(new ArrayList<Command>());
        }
        if (usergroup.getAccountCollection() == null) {
            usergroup.setAccountCollection(new ArrayList<Account>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Command> attachedCommandCollection = new ArrayList<Command>();
            for (Command commandCollectionCommandToAttach : usergroup.getCommandCollection()) {
                commandCollectionCommandToAttach = em.getReference(commandCollectionCommandToAttach.getClass(), commandCollectionCommandToAttach.getName());
                attachedCommandCollection.add(commandCollectionCommandToAttach);
            }
            usergroup.setCommandCollection(attachedCommandCollection);
            Collection<Account> attachedAccountCollection = new ArrayList<Account>();
            for (Account accountCollectionAccountToAttach : usergroup.getAccountCollection()) {
                accountCollectionAccountToAttach = em.getReference(accountCollectionAccountToAttach.getClass(), accountCollectionAccountToAttach.getEMail());
                attachedAccountCollection.add(accountCollectionAccountToAttach);
            }
            usergroup.setAccountCollection(attachedAccountCollection);
            em.persist(usergroup);
            for (Command commandCollectionCommand : usergroup.getCommandCollection()) {
                commandCollectionCommand.getUsergroupCollection().add(usergroup);
                commandCollectionCommand = em.merge(commandCollectionCommand);
            }
            for (Account accountCollectionAccount : usergroup.getAccountCollection()) {
                Usergroup oldUserGroupOfAccountCollectionAccount = accountCollectionAccount.getUserGroup();
                accountCollectionAccount.setUserGroup(usergroup);
                accountCollectionAccount = em.merge(accountCollectionAccount);
                if (oldUserGroupOfAccountCollectionAccount != null) {
                    oldUserGroupOfAccountCollectionAccount.getAccountCollection().remove(accountCollectionAccount);
                    oldUserGroupOfAccountCollectionAccount = em.merge(oldUserGroupOfAccountCollectionAccount);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usergroup usergroup) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usergroup persistentUsergroup = em.find(Usergroup.class, usergroup.getId());
            Collection<Command> commandCollectionOld = persistentUsergroup.getCommandCollection();
            Collection<Command> commandCollectionNew = usergroup.getCommandCollection();
            Collection<Account> accountCollectionOld = persistentUsergroup.getAccountCollection();
            Collection<Account> accountCollectionNew = usergroup.getAccountCollection();
            Collection<Command> attachedCommandCollectionNew = new ArrayList<Command>();
            for (Command commandCollectionNewCommandToAttach : commandCollectionNew) {
                commandCollectionNewCommandToAttach = em.getReference(commandCollectionNewCommandToAttach.getClass(), commandCollectionNewCommandToAttach.getName());
                attachedCommandCollectionNew.add(commandCollectionNewCommandToAttach);
            }
            commandCollectionNew = attachedCommandCollectionNew;
            usergroup.setCommandCollection(commandCollectionNew);
            Collection<Account> attachedAccountCollectionNew = new ArrayList<Account>();
            for (Account accountCollectionNewAccountToAttach : accountCollectionNew) {
                accountCollectionNewAccountToAttach = em.getReference(accountCollectionNewAccountToAttach.getClass(), accountCollectionNewAccountToAttach.getEMail());
                attachedAccountCollectionNew.add(accountCollectionNewAccountToAttach);
            }
            accountCollectionNew = attachedAccountCollectionNew;
            usergroup.setAccountCollection(accountCollectionNew);
            usergroup = em.merge(usergroup);
            for (Command commandCollectionOldCommand : commandCollectionOld) {
                if (!commandCollectionNew.contains(commandCollectionOldCommand)) {
                    commandCollectionOldCommand.getUsergroupCollection().remove(usergroup);
                    commandCollectionOldCommand = em.merge(commandCollectionOldCommand);
                }
            }
            for (Command commandCollectionNewCommand : commandCollectionNew) {
                if (!commandCollectionOld.contains(commandCollectionNewCommand)) {
                    commandCollectionNewCommand.getUsergroupCollection().add(usergroup);
                    commandCollectionNewCommand = em.merge(commandCollectionNewCommand);
                }
            }
            for (Account accountCollectionOldAccount : accountCollectionOld) {
                if (!accountCollectionNew.contains(accountCollectionOldAccount)) {
                    accountCollectionOldAccount.setUserGroup(null);
                    accountCollectionOldAccount = em.merge(accountCollectionOldAccount);
                }
            }
            for (Account accountCollectionNewAccount : accountCollectionNew) {
                if (!accountCollectionOld.contains(accountCollectionNewAccount)) {
                    Usergroup oldUserGroupOfAccountCollectionNewAccount = accountCollectionNewAccount.getUserGroup();
                    accountCollectionNewAccount.setUserGroup(usergroup);
                    accountCollectionNewAccount = em.merge(accountCollectionNewAccount);
                    if (oldUserGroupOfAccountCollectionNewAccount != null && !oldUserGroupOfAccountCollectionNewAccount.equals(usergroup)) {
                        oldUserGroupOfAccountCollectionNewAccount.getAccountCollection().remove(accountCollectionNewAccount);
                        oldUserGroupOfAccountCollectionNewAccount = em.merge(oldUserGroupOfAccountCollectionNewAccount);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usergroup.getId();
                if (findUsergroup(id) == null) {
                    throw new NonexistentEntityException("The usergroup with id " + id + " no longer exists.");
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
            Usergroup usergroup;
            try {
                usergroup = em.getReference(Usergroup.class, id);
                usergroup.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usergroup with id " + id + " no longer exists.", enfe);
            }
            Collection<Command> commandCollection = usergroup.getCommandCollection();
            for (Command commandCollectionCommand : commandCollection) {
                commandCollectionCommand.getUsergroupCollection().remove(usergroup);
                commandCollectionCommand = em.merge(commandCollectionCommand);
            }
            Collection<Account> accountCollection = usergroup.getAccountCollection();
            for (Account accountCollectionAccount : accountCollection) {
                accountCollectionAccount.setUserGroup(null);
                accountCollectionAccount = em.merge(accountCollectionAccount);
            }
            em.remove(usergroup);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usergroup> findUsergroupEntities() {
        return findUsergroupEntities(true, -1, -1);
    }

    public List<Usergroup> findUsergroupEntities(int maxResults, int firstResult) {
        return findUsergroupEntities(false, maxResults, firstResult);
    }

    private List<Usergroup> findUsergroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usergroup.class));
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

    public Usergroup findUsergroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usergroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsergroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usergroup> rt = cq.from(Usergroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
