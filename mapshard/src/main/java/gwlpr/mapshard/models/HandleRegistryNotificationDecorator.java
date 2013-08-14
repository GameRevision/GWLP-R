/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.models;

import java.util.UUID;
import realityshard.container.events.Event;
import realityshard.container.events.EventAggregator;
import realityshard.container.util.Handle;
import realityshard.container.util.HandleRegistry;


/**
 * This handle registry will trigger events in the provided event aggregator
 * when handles
 * 
 * - get registered
 * - get removed
 * 
 * or when the last handle has been removed
 * 
 * @author _rusty
 */
public class HandleRegistryNotificationDecorator<T> extends HandleRegistry<T>
{

    public static final class Registered<T> implements Event 
    { 
        private Handle<T> handle;
        public Registered(Handle<T> handle) { this.handle = handle; }
        public Handle<T> getHandle() { return handle; }
    }
    
    
    public static final class Unregistered<T> implements Event 
    { 
        private T object;
        public Unregistered(T object) { this.object = object; }
        public T getObject() { return object; }
    }
    
    
    public static final class Empty implements Event {}
    
    
    private final EventAggregator aggregator;
    
    
    /**
     * Constructor.
     * 
     * @param       aggregator              The event aggregator that this registry
     *                                      will trigger its event in.
     */
    public HandleRegistryNotificationDecorator(EventAggregator aggregator)
    {
        this.aggregator = aggregator;
    }
    
    
    /**
     * We only need to override this to get the notifications, because the register
     * function will call this.
     */
    @Override
    public Handle<T> registerExisting(T object, UUID uid)
    {
        Handle<T> result = super.registerExisting(object, uid);
        
        if (result != null)
        {
            aggregator.triggerEvent(new Registered<>(result));
        }
        
        return result;
    }

    
    @Override
    protected void remove(Handle<T> handle) 
    {
        super.remove(handle);
        
        aggregator.triggerEvent(new Unregistered<>(handle.get()));
        
        if (isEmpty())
        {
            aggregator.triggerEvent(new Empty());
        }
    }
}
