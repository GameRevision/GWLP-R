/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol.serialization;

import realityshard.container.network.Message;


/**
 * Specifies a header additionally to the functionality of a Message.
 * This class must be used for all GW protocol related actions.
 * 
 * @author _rusty
 */
public abstract class GWMessage extends Message
{
    
    /**
     * Getter.
     * The header is the first 2 bytes of a GW packet.
     * 
     * @return      The header of this message.
     */
    public abstract short getHeader();
}
