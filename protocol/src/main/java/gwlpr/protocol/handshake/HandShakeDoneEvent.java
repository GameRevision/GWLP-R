/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.handshake;

import realityshard.container.events.Event;


/**
 * Triggered by the handshake handler once the handshake is done.
 * 
 * Note that this is triggered regardless of verify client was actually
 * received or not. (Meaning it might be null - e.g. on login shards)
 * 
 * @author _rusty
 */
public class HandShakeDoneEvent implements Event
{
    
    private final IN1_VerifyClient verifyClient;
    
    
    public HandShakeDoneEvent(IN1_VerifyClient verifyClient)
    {
        this.verifyClient = verifyClient;
    }

    
    public IN1_VerifyClient getVerifyClient() 
    {
        return verifyClient;
    }    
}
