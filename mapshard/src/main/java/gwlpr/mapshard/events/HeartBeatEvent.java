/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.events;


/**
 * Used for the discrete time interval that we have to compute packets
 * and finally send them.
 * There should be a special controller listening for this event and
 * pumping out all necessary packets between the heartbeats.
 *
 * @author _rusty
 */
public class HeartBeatEvent extends AbstractTimedeltaEvent
{
    
    @Override
    public HeartBeatEvent clone()
    {
        HeartBeatEvent result = new HeartBeatEvent();
        result.setTimeDelta(getTimeDelta());
        
        return result;
    }
}
