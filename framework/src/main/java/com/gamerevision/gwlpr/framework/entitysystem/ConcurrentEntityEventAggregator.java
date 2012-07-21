/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.framework.entitysystem;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Decorates the ConcurrentEventAggregator to implement the functionality
 * of the EntityEventAggregator.
 * 
 * Actually, this is a simple code copy/paste, as i couldnt find a way to meaningful
 * extend the functionality of the original ConcurrentEventAggregator without
 * changing its source.
 * 
 * @author _rusty
 */
public class ConcurrentEntityEventAggregator implements EntityEventAggregator
{

    /**
     * Used for keeping the listener-object/listener-method
     * stuff within a pair. (Needed for invokation of the method lateron)
     */
    private static final class ObjectEntityMethodAssociation
    {
        private Object object;
        private Entity entity;
        private Method method;
        
        
        /**
         * Constructor.
         * 
         * @param       object
         * @param       method 
         */
        public ObjectEntityMethodAssociation(Object object, Entity entity, Method method)
        {
            this.object = object;
            this.entity = entity;
            this.method = method;
        }

        
        /**
         * Getter.
         * 
         * @return      The object that holds the method
         */
        public Object getObject() 
        {
            return object;
        }
        
        
        /**
         * Getter.
         * 
         * @return      The entity connected to this event handler
         */
        public Entity getEntity()
        {
            return entity;
        }
        
        
        /**
         * Getter.
         * 
         * @return      The method declared within the class of the object
         */
        public Method getMethod() 
        {
            return method;
        }     
    }
    
    
    /**
     * Used to invoke the listeners dynamically and by the executor
     */
    private static final class Invokable implements Runnable
    {
        
        private final ObjectEntityMethodAssociation invokableAssociation;
        private final Event parameter;
        
        
        /**
         * Constructor.
         * 
         * @param       invokablePair
         * @param       parameter 
         */
        public Invokable(ObjectEntityMethodAssociation invokablePair, Event parameter)
        {
            this.invokableAssociation = invokablePair;
            this.parameter = parameter;
            
        }
        
        
        /**
         * Will be executed by executor
         */
        @Override
        public void run() 
        {
            try 
            {
                invokableAssociation.getMethod()
                        .invoke(invokableAssociation.getObject(), parameter);
            } 
            catch (    IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) 
            {
                LOGGER.warn("Could not execute an event handler", ex);
            }
        }
    }
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentEntityEventAggregator.class);
    private final Map<Class<? extends Event>, List<ObjectEntityMethodAssociation>> eventMapping;
    private final Executor executor;
    
    
    /**
     * Constructor.
     */
    public ConcurrentEntityEventAggregator(Executor executor)
    {
        // get the executor defined by the application
        this.executor = executor;
        
        eventMapping = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Add a new listener to the event->listener association
     * 
     * @param       listener                The listener object. The aggregator will call the
     *                                      handler methods of this object in case of an event.
     */
    @Override
    public void addListener(Object listener)
    {
        addListener(null, listener);
    }
    
    
    /**
     * Add an entity-dependent listener to the listener collection.
     * Note that by using the usual entity independent addListener method,
     * you will always recieve events, even if they dont concern your entity/component.
     * 
     * @param       entity                  The concrete entity that you want to recieve events for.
     * @param       listener                The listener object (may contain several event-handlers, as usual!)
     */
    @Override
    public void addListener(Entity entity, Object listener)
    {
        // get all the declared methods of the listener, so we can look for annotations
        Method[] methods = listener.getClass().getDeclaredMethods();
        
        // try extracting the methods with our listener annotation, specified in
        // Shardlet.EventHandler
        Map<Class<? extends Event>, Method> listenerMethods = new HashMap<>();
        for (Method method : methods) 
        {
            // check if the method has an annotation of type EventHandler
            if(method.getAnnotation(EventHandler.class) != null)
            {
                Class<?>[] params = method.getParameterTypes();
                // check if the method follows the general listener method conventions
                // meaning it takes only one argument which has a class that implements
                // Shardlet.Event
                if (params.length == 1 && Event.class.isAssignableFrom(params[0]))
                {
                    listenerMethods.put((Class<? extends Event>)params[0], method);
                }
                else
                {
                    LOGGER.warn("Listener has a method that is annotated as EventHandler but doesnt follow the signature.", listener);
                }
            }
        }
        
        // add the methods to the aggregator
        for (Map.Entry<Class<? extends Event>, Method> entry : listenerMethods.entrySet()) 
        {
            Class<? extends Event> clazz = entry.getKey();
            ObjectEntityMethodAssociation pair = new ObjectEntityMethodAssociation(listener, entity, entry.getValue());
        
            // we want to add the listener methods to a list of methods that have the same
            // signature, and thus listen for the same event
            // so try to get the list
            List<ObjectEntityMethodAssociation> list = eventMapping.get(clazz);

            if (list == null)
            {
                // if there is no entry yet, create a new listener list
                list = new CopyOnWriteArrayList<>();
                eventMapping.put(clazz, list);
            }

            // finally add the listener
            list.add(pair);
        }
    }
    
    
    /**
     * Remove all event handlers of a single entity from this aggregator.
     * Note that handlers that are not entity-specific can not be removed.
     * 
     * @param       entity                  All handlers from this entity will be removed from the
     *                                      event-aggregator. (No matter which object holds them)
     */
    @Override
    public void removeListener(Entity entity) 
    {
        // its kinda tricky to remove stuff from our mapping, because we cant simply
        // look up an event. the values of our hashmap consist of lists of the associations
        // that we want to remove, so me must iterate those lists and find the values, then we must remove them from the lists
        
        List<ObjectEntityMethodAssociation> found = new ArrayList<>();
        
        // iterate trough the dictionary values, getting a list of associations
        for (List<ObjectEntityMethodAssociation> list: eventMapping.values())
        {
            // now iterate through the associations, and search for the 'entity'
            for (ObjectEntityMethodAssociation assoc: list)
            {
                if (assoc.getEntity().compareTo(entity) == 0)
                {
                    // we've found one of the associations that we want to remove.
                    // temporarily save it...
                    found.add(assoc);
                }
            }
            
             // remove all those associations that we found
            for (ObjectEntityMethodAssociation assoc: found)
            {
                list.remove(assoc);
            }
            
            // clear up the list that we are using to temporarily save associations
            // because we'll simply re-use it for the next hashmap value
            found.clear();
        }
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       event                   The concrete event     
     */
    @Override
    public void triggerEvent(Event event)
    {
        // get the listeners of the event
        List<ObjectEntityMethodAssociation> listeners = eventMapping.get(event.getClass());
        
        // failcheck
        if (listeners == null) return;
        
        for (ObjectEntityMethodAssociation assoc: listeners)
        {
            // for each listener in the listener collection,
            // try to invoke the handler with
            // the object that holds it and the event
            executor.execute(new Invokable(assoc, event));
        }
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       entity                  The entity that this event concerns
     * @param       event                   The concrete event     
     */
    @Override
    public void triggerEntityEvent(Entity entity, Event event) 
    {
        // get the listeners of the event
        List<ObjectEntityMethodAssociation> associations = eventMapping.get(event.getClass());
        
        // failcheck
        if (associations == null) return;
        
        // get the ones that we actually want to invoke
        for (ObjectEntityMethodAssociation assoc : associations) 
        {
            // we want to invoke methods that:
            //  a) are associated to the 'entity' or
            //  b) have no associated entity (null reference)
            if (assoc.getEntity().equals(entity) || assoc.getEntity() == null)
            {
                // for each listener in the listener collection,
                // try to invoke the handler with
                // the object that holds it and the event
                executor.execute(new Invokable(assoc, event));
            }
        }
    }
}
