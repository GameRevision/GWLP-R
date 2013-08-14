/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;


/**
 * Used for update loops WITHIN this server
 *
 * @author _rusty
 */
public class SystemsUpdateEvent extends AbstractTimedeltaEvent 
{
    
    @Override
    public SystemsUpdateEvent clone()
    {
        SystemsUpdateEvent result = new SystemsUpdateEvent();
        result.setTimeDelta(getTimeDelta());
        
        return result;
    }
}
