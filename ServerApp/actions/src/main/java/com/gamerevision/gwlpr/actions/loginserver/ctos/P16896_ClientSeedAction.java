/**
 * For copyright information see the LICENSE document.
 */

/**
 * Auto-generated by PacketCodeGen, on 2012-05-28
 */

package com.gamerevision.gwlpr.actions.loginserver.ctos;

import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.GenericEventAction;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an unencrypted packet.
 * Its the first packet to be sent by the client.
 */
public final class P16896_ClientSeedAction extends GenericEventAction
{

    private List<Byte> clientSeed;

    
    public short getHeader()
    {
        return 16896;
    }


    public List<Byte> getClientSeed()
    {
        return clientSeed;
    }


    @Override
    public boolean deserialize()
    {
        ByteBuffer buffer = getBuffer();
        int bufferPosition = buffer.position();

        try
        {
            clientSeed = new ArrayList<>();
            
            for (int i = 0; i < 64; i++)
            {
                clientSeed.add(buffer.get());
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