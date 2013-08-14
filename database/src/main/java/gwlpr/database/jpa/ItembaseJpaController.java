/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.database.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import gwlpr.database.entities.Item;
import gwlpr.database.entities.Itembase;
import gwlpr.database.jpa.exceptions.IllegalOrphanException;
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
public class ItembaseJpaController implements Serializable 
{

    public ItembaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itembase itembase) {
        if (itembase.getItemCollection() == null) {
            itembase.setItemCollection(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : itembase.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getId());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            itembase.setItemCollection(attachedItemCollection);
            em.persist(itembase);
            for (Item itemCollectionItem : itembase.getItemCollection()) {
                Itembase oldBaseIDOfItemCollectionItem = itemCollectionItem.getBaseID();
                itemCollectionItem.setBaseID(itembase);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldBaseIDOfItemCollectionItem != null) {
                    oldBaseIDOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldBaseIDOfItemCollectionItem = em.merge(oldBaseIDOfItemCollectionItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itembase itembase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itembase persistentItembase = em.find(Itembase.class, itembase.getId());
            Collection<Item> itemCollectionOld = persistentItembase.getItemCollection();
            Collection<Item> itemCollectionNew = itembase.getItemCollection();
            List<String> illegalOrphanMessages = null;
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its baseID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getId());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            itembase.setItemCollection(itemCollectionNew);
            itembase = em.merge(itembase);
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    Itembase oldBaseIDOfItemCollectionNewItem = itemCollectionNewItem.getBaseID();
                    itemCollectionNewItem.setBaseID(itembase);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldBaseIDOfItemCollectionNewItem != null && !oldBaseIDOfItemCollectionNewItem.equals(itembase)) {
                        oldBaseIDOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldBaseIDOfItemCollectionNewItem = em.merge(oldBaseIDOfItemCollectionNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itembase.getId();
                if (findItembase(id) == null) {
                    throw new NonexistentEntityException("The itembase with id " + id + " no longer exists.");
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
            Itembase itembase;
            try {
                itembase = em.getReference(Itembase.class, id);
                itembase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itembase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Item> itemCollectionOrphanCheck = itembase.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Itembase (" + itembase + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable baseID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(itembase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itembase> findItembaseEntities() {
        return findItembaseEntities(true, -1, -1);
    }

    public List<Itembase> findItembaseEntities(int maxResults, int firstResult) {
        return findItembaseEntities(false, maxResults, firstResult);
    }

    private List<Itembase> findItembaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itembase.class));
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

    public Itembase findItembase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itembase.class, id);
        } finally {
            em.close();
        }
    }

    public int getItembaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itembase> rt = cq.from(Itembase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
