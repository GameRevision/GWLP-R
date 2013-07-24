/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.gameserver.ctos;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.utils.GenericTriggerableAction;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * This is an unencrypted packet.
 * Its the second packet to be sent by the client.
 * 
 * @author miracle444
 */
public final class P16896_ClientSeedAction extends GenericTriggerableAction
{

    private byte[] seed;
    
    
    public short getHeader()
    {
        return 16896;
    }

    
    public byte[] getSeed()
    {
        return seed;
    }
    
    
    @Override
    public boolean deserialize()
    {
        ByteBuffer buffer = getBuffer();
        int bufferPosition = buffer.position();

        try
        {
            seed = new byte[64];
            for (int i = 0; i < 64; i++)
            {
                seed[i] = buffer.get();
            }
        }
        catch (BufferUnderflowException e)
        {
            buffer.position(bufferPosition);
            return false;
        }

        return true;
    }


    @Override
    public void triggerEvent(EventAggregator aggregator)
    {
        aggregator.triggerEvent(this);
    }
}