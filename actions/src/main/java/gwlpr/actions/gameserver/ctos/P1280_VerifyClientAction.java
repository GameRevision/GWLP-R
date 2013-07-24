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
 * Its the first packet to be sent by the client.
 * 
 * @author miracle444
 */
public final class P1280_VerifyClientAction extends GenericTriggerableAction
{

    private short unknown1;
    private int unknown2;
    private int unknown3;
    private int key1;
    private int unknown4;
    private int key2;
    private byte[] accountHash;
    private byte[] characterHash;
    private int unknown5;
    private int unknown6;
    
    
    public short getHeader()
    {
        return 1280;
    }

    
    public short getUnknown1()
    {
        return unknown1;
    }
    
    
    public int getUnknown2()
    {
        return unknown2;
    }

    
    public int getUnknown3()
    {
        return unknown3;
    }
    
    
    public int getKey1()
    {
        return key1;
    }

    
    public int getUnknown4()
    {
        return unknown4;
    }
    
    
    public int getKey2()
    {
        return key2;
    }

    
    public byte[] getAccountHash()
    {
        return accountHash;
    }

    
    public byte[] getCharacterHash()
    {
        return accountHash;
    }

    
    public int getUnknown5()
    {
        return unknown5;
    }

    
    public int getUnknown6()
    {
        return unknown6;
    }
    
    
    @Override
    public boolean deserialize()
    {
        ByteBuffer buffer = getBuffer();
        int bufferPosition = buffer.position();

        try
        {
            unknown1 = buffer.getShort();
            unknown2 = buffer.getInt();
            unknown3 = buffer.getInt();
            key1 = buffer.getInt();
            unknown4 = buffer.getInt();
            key2 = buffer.getInt();
                    
            accountHash = new byte[16];
            for (int i = 0; i < 16; i++)
            {
                accountHash[i] = buffer.get();
            }
            
            characterHash = new byte[16];
            for (int i = 0; i < 16; i++)
            {
                characterHash[i] = buffer.get();
            }
            
            unknown5 = buffer.getInt();
            unknown6 = buffer.getInt();
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