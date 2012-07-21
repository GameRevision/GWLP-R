/**
 * For copyright information see the LICENSE document.
 */

/**
 * Auto-generated by PacketCodeGen, on 2012-05-31
 */

package com.gamerevision.gwlpr.actions.gameserver.stoc;

import com.realityshard.shardlet.GenericAction;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * This is an automatically generated ShardletAction.
 * It resembles the packet template that has been 
 * parsed from our packet templates xml.
 *
 * Auto generated 
 *
 * @author GWLPR Template Updater
 */
public final class P203_UnknownAction extends GenericAction
{

    private int[] unknown1;


    public short getHeader()
    {
        return 203;
    }


    public void setUnknown1(int[] newValue)
    {
        unknown1 = newValue;
    }


    private int getSize()
    {
        int size = 4;

        if (unknown1 != null)
        {
            size += 4 * unknown1.length;
        }

        return size;
    }


    @Override
    public boolean serialize()
    {
        int size = getSize();

        if (size == 0)
        {
            return false;
        }

        ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);

        try
        {
            buffer.putShort(getHeader());

            short prefix_unknown1;
            if (unknown1 == null)
            {
                prefix_unknown1 = 0;
            }
            else
            {
                prefix_unknown1 = (short) unknown1.length;
            }
            buffer.putShort(prefix_unknown1);
            
            for (int i = 0; i < prefix_unknown1; i++)
            {
                buffer.putInt(unknown1[i]);
            }
        }
        catch (BufferOverflowException e)
        {
            return false;
        }

        setBuffer(buffer);

        return true;
    }
}