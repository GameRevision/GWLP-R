/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import gwlpr.database.EntityManagerFactoryProvider;
import gwlpr.database.entities.Account;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Usergroup;
import gwlpr.database.entities.Inventory;
import gwlpr.database.entities.Storagetab;
import java.util.ArrayList;
import java.util.Collection;
import gwlpr.database.entities.Factionstat;
import gwlpr.database.entities.Character;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
import gwlpr.database.jpa.exceptions.NonexistentEntityException;
import gwlpr.database.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;


/**
 *
 * @author _rusty
 */
public class AccountJpaController implements Serializable 
{
    private static final AccountJpaController SINGLETON = new AccountJpaController(EntityManagerFactoryProvider.get());
    
    public static AccountJpaController get() {
        return SINGLETON;
    }
    
    public AccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws PreexistingEntityException, Exception {
        if (account.getStoragetabCollection() == null) {
            account.setStoragetabCollection(new ArrayList<Storagetab>());
        }
        if (account.getFactionstatCollection() == null) {
            account.setFactionstatCollection(new ArrayList<Factionstat>());
        }
        if (account.getCharacterCollection() == null) {
            account.setCharacterCollection(new ArrayList<Character>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usergroup userGroup = account.getUserGroup();
            if (userGroup != null) {
                userGroup = em.getReference(userGroup.getClass(), userGroup.getId());
                account.setUserGroup(userGroup);
            }
            Inventory materialStorage = account.getMaterialStorage();
            if (materialStorage != null) {
                materialStorage = em.getReference(materialStorage.getClass(), materialStorage.getId());
                account.setMaterialStorage(materialStorage);
            }
            Collection<Storagetab> attachedStoragetabCollection = new ArrayList<Storagetab>();
            for (Storagetab storagetabCollectionStoragetabToAttach : account.getStoragetabCollection()) {
                storagetabCollectionStoragetabToAttach = em.getReference(storagetabCollectionStoragetabToAttach.getClass(), storagetabCollectionStoragetabToAttach.getStoragetabPK());
                attachedStoragetabCollection.add(storagetabCollectionStoragetabToAttach);
            }
            account.setStoragetabCollection(attachedStoragetabCollection);
            Collection<Factionstat> attachedFactionstatCollection = new ArrayList<Factionstat>();
            for (Factionstat factionstatCollectionFactionstatToAttach : account.getFactionstatCollection()) {
                factionstatCollectionFactionstatToAttach = em.getReference(factionstatCollectionFactionstatToAttach.getClass(), factionstatCollectionFactionstatToAttach.getFactionstatPK());
                attachedFactionstatCollection.add(factionstatCollectionFactionstatToAttach);
            }
            account.setFactionstatCollection(attachedFactionstatCollection);
            Collection<Character> attachedCharacterCollection = new ArrayList<Character>();
            for (Character characterCollectionCharacterToAttach : account.getCharacterCollection()) {
                characterCollectionCharacterToAttach = em.getReference(characterCollectionCharacterToAttach.getClass(), characterCollectionCharacterToAttach.getId());
                attachedCharacterCollection.add(characterCollectionCharacterToAttach);
            }
            account.setCharacterCollection(attachedCharacterCollection);
            em.persist(account);
            if (userGroup != null) {
                userGroup.getAccountCollection().add(account);
                userGroup = em.merge(userGroup);
            }
            if (materialStorage != null) {
                materialStorage.getAccountCollection().add(account);
                materialStorage = em.merge(materialStorage);
            }
            for (Storagetab storagetabCollectionStoragetab : account.getStoragetabCollection()) {
                Account oldAccountOfStoragetabCollectionStoragetab = storagetabCollectionStoragetab.getAccount();
                storagetabCollectionStoragetab.setAccount(account);
                storagetabCollectionStoragetab = em.merge(storagetabCollectionStoragetab);
                if (oldAccountOfStoragetabCollectionStoragetab != null) {
                    oldAccountOfStoragetabCollectionStoragetab.getStoragetabCollection().remove(storagetabCollectionStoragetab);
                    oldAccountOfStoragetabCollectionStoragetab = em.merge(oldAccountOfStoragetabCollectionStoragetab);
                }
            }
            for (Factionstat factionstatCollectionFactionstat : account.getFactionstatCollection()) {
                Account oldAccountOfFactionstatCollectionFactionstat = factionstatCollectionFactionstat.getAccount();
                factionstatCollectionFactionstat.setAccount(account);
                factionstatCollectionFactionstat = em.merge(factionstatCollectionFactionstat);
                if (oldAccountOfFactionstatCollectionFactionstat != null) {
                    oldAccountOfFactionstatCollectionFactionstat.getFactionstatCollection().remove(factionstatCollectionFactionstat);
                    oldAccountOfFactionstatCollectionFactionstat = em.merge(oldAccountOfFactionstatCollectionFactionstat);
                }
            }
            for (Character characterCollectionCharacter : account.getCharacterCollection()) {
                Account oldAccountIDOfCharacterCollectionCharacter = characterCollectionCharacter.getAccountID();
                characterCollectionCharacter.setAccountID(account);
                characterCollectionCharacter = em.merge(characterCollectionCharacter);
                if (oldAccountIDOfCharacterCollectionCharacter != null) {
                    oldAccountIDOfCharacterCollectionCharacter.getCharacterCollection().remove(characterCollectionCharacter);
                    oldAccountIDOfCharacterCollectionCharacter = em.merge(oldAccountIDOfCharacterCollectionCharacter);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAccount(account.getEMail()) != null) {
                throw new PreexistingEntityException("Account " + account + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account persistentAccount = em.find(Account.class, account.getEMail());
            Usergroup userGroupOld = persistentAccount.getUserGroup();
            Usergroup userGroupNew = account.getUserGroup();
            Inventory materialStorageOld = persistentAccount.getMaterialStorage();
            Inventory materialStorageNew = account.getMaterialStorage();
            Collection<Storagetab> storagetabCollectionOld = persistentAccount.getStoragetabCollection();
            Collection<Storagetab> storagetabCollectionNew = account.getStoragetabCollection();
            Collection<Factionstat> factionstatCollectionOld = persistentAccount.getFactionstatCollection();
            Collection<Factionstat> factionstatCollectionNew = account.getFactionstatCollection();
            Collection<Character> characterCollectionOld = persistentAccount.getCharacterCollection();
            Collection<Character> characterCollectionNew = account.getCharacterCollection();
            List<String> illegalOrphanMessages = null;
            for (Storagetab storagetabCollectionOldStoragetab : storagetabCollectionOld) {
                if (!storagetabCollectionNew.contains(storagetabCollectionOldStoragetab)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Storagetab " + storagetabCollectionOldStoragetab + " since its account field is not nullable.");
                }
            }
            for (Factionstat factionstatCollectionOldFactionstat : factionstatCollectionOld) {
                if (!factionstatCollectionNew.contains(factionstatCollectionOldFactionstat)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factionstat " + factionstatCollectionOldFactionstat + " since its account field is not nullable.");
                }
            }
            for (Character characterCollectionOldCharacter : characterCollectionOld) {
                if (!characterCollectionNew.contains(characterCollectionOldCharacter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Character " + characterCollectionOldCharacter + " since its accountID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userGroupNew != null) {
                userGroupNew = em.getReference(userGroupNew.getClass(), userGroupNew.getId());
                account.setUserGroup(userGroupNew);
            }
            if (materialStorageNew != null) {
                materialStorageNew = em.getReference(materialStorageNew.getClass(), materialStorageNew.getId());
                account.setMaterialStorage(materialStorageNew);
            }
            Collection<Storagetab> attachedStoragetabCollectionNew = new ArrayList<Storagetab>();
            for (Storagetab storagetabCollectionNewStoragetabToAttach : storagetabCollectionNew) {
                storagetabCollectionNewStoragetabToAttach = em.getReference(storagetabCollectionNewStoragetabToAttach.getClass(), storagetabCollectionNewStoragetabToAttach.getStoragetabPK());
                attachedStoragetabCollectionNew.add(storagetabCollectionNewStoragetabToAttach);
            }
            storagetabCollectionNew = attachedStoragetabCollectionNew;
            account.setStoragetabCollection(storagetabCollectionNew);
            Collection<Factionstat> attachedFactionstatCollectionNew = new ArrayList<Factionstat>();
            for (Factionstat factionstatCollectionNewFactionstatToAttach : factionstatCollectionNew) {
                factionstatCollectionNewFactionstatToAttach = em.getReference(factionstatCollectionNewFactionstatToAttach.getClass(), factionstatCollectionNewFactionstatToAttach.getFactionstatPK());
                attachedFactionstatCollectionNew.add(factionstatCollectionNewFactionstatToAttach);
            }
            factionstatCollectionNew = attachedFactionstatCollectionNew;
            account.setFactionstatCollection(factionstatCollectionNew);
            Collection<Character> attachedCharacterCollectionNew = new ArrayList<Character>();
            for (Character characterCollectionNewCharacterToAttach : characterCollectionNew) {
                characterCollectionNewCharacterToAttach = em.getReference(characterCollectionNewCharacterToAttach.getClass(), characterCollectionNewCharacterToAttach.getId());
                attachedCharacterCollectionNew.add(characterCollectionNewCharacterToAttach);
            }
            characterCollectionNew = attachedCharacterCollectionNew;
            account.setCharacterCollection(characterCollectionNew);
            account = em.merge(account);
            if (userGroupOld != null && !userGroupOld.equals(userGroupNew)) {
                userGroupOld.getAccountCollection().remove(account);
                userGroupOld = em.merge(userGroupOld);
            }
            if (userGroupNew != null && !userGroupNew.equals(userGroupOld)) {
                userGroupNew.getAccountCollection().add(account);
                userGroupNew = em.merge(userGroupNew);
            }
            if (materialStorageOld != null && !materialStorageOld.equals(materialStorageNew)) {
                materialStorageOld.getAccountCollection().remove(account);
                materialStorageOld = em.merge(materialStorageOld);
            }
            if (materialStorageNew != null && !materialStorageNew.equals(materialStorageOld)) {
                materialStorageNew.getAccountCollection().add(account);
                materialStorageNew = em.merge(materialStorageNew);
            }
            for (Storagetab storagetabCollectionNewStoragetab : storagetabCollectionNew) {
                if (!storagetabCollectionOld.contains(storagetabCollectionNewStoragetab)) {
                    Account oldAccountOfStoragetabCollectionNewStoragetab = storagetabCollectionNewStoragetab.getAccount();
                    storagetabCollectionNewStoragetab.setAccount(account);
                    storagetabCollectionNewStoragetab = em.merge(storagetabCollectionNewStoragetab);
                    if (oldAccountOfStoragetabCollectionNewStoragetab != null && !oldAccountOfStoragetabCollectionNewStoragetab.equals(account)) {
                        oldAccountOfStoragetabCollectionNewStoragetab.getStoragetabCollection().remove(storagetabCollectionNewStoragetab);
                        oldAccountOfStoragetabCollectionNewStoragetab = em.merge(oldAccountOfStoragetabCollectionNewStoragetab);
                    }
                }
            }
            for (Factionstat factionstatCollectionNewFactionstat : factionstatCollectionNew) {
                if (!factionstatCollectionOld.contains(factionstatCollectionNewFactionstat)) {
                    Account oldAccountOfFactionstatCollectionNewFactionstat = factionstatCollectionNewFactionstat.getAccount();
                    factionstatCollectionNewFactionstat.setAccount(account);
                    factionstatCollectionNewFactionstat = em.merge(factionstatCollectionNewFactionstat);
                    if (oldAccountOfFactionstatCollectionNewFactionstat != null && !oldAccountOfFactionstatCollectionNewFactionstat.equals(account)) {
                        oldAccountOfFactionstatCollectionNewFactionstat.getFactionstatCollection().remove(factionstatCollectionNewFactionstat);
                        oldAccountOfFactionstatCollectionNewFactionstat = em.merge(oldAccountOfFactionstatCollectionNewFactionstat);
                    }
                }
            }
            for (Character characterCollectionNewCharacter : characterCollectionNew) {
                if (!characterCollectionOld.contains(characterCollectionNewCharacter)) {
                    Account oldAccountIDOfCharacterCollectionNewCharacter = characterCollectionNewCharacter.getAccountID();
                    characterCollectionNewCharacter.setAccountID(account);
                    characterCollectionNewCharacter = em.merge(characterCollectionNewCharacter);
                    if (oldAccountIDOfCharacterCollectionNewCharacter != null && !oldAccountIDOfCharacterCollectionNewCharacter.equals(account)) {
                        oldAccountIDOfCharacterCollectionNewCharacter.getCharacterCollection().remove(characterCollectionNewCharacter);
                        oldAccountIDOfCharacterCollectionNewCharacter = em.merge(oldAccountIDOfCharacterCollectionNewCharacter);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = account.getEMail();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getEMail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Storagetab> storagetabCollectionOrphanCheck = account.getStoragetabCollection();
            for (Storagetab storagetabCollectionOrphanCheckStoragetab : storagetabCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Storagetab " + storagetabCollectionOrphanCheckStoragetab + " in its storagetabCollection field has a non-nullable account field.");
            }
            Collection<Factionstat> factionstatCollectionOrphanCheck = account.getFactionstatCollection();
            for (Factionstat factionstatCollectionOrphanCheckFactionstat : factionstatCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Factionstat " + factionstatCollectionOrphanCheckFactionstat + " in its factionstatCollection field has a non-nullable account field.");
            }
            Collection<Character> characterCollectionOrphanCheck = account.getCharacterCollection();
            for (Character characterCollectionOrphanCheckCharacter : characterCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Character " + characterCollectionOrphanCheckCharacter + " in its characterCollection field has a non-nullable accountID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usergroup userGroup = account.getUserGroup();
            if (userGroup != null) {
                userGroup.getAccountCollection().remove(account);
                userGroup = em.merge(userGroup);
            }
            Inventory materialStorage = account.getMaterialStorage();
            if (materialStorage != null) {
                materialStorage.getAccountCollection().remove(account);
                materialStorage = em.merge(materialStorage);
            }
            em.remove(account);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }
    
    public Account findById(int id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Account> query = getEntityManager().createNamedQuery("Account.findById", Account.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
