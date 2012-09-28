/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.protocol;

import com.realityshard.shardlet.Session;
import com.realityshard.shardlet.TriggerableAction;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Part of the deserialisation procedure.
 * Creates concrete actions and manages remaining data.
 * This class is extended by concrete deserializers.
 * 
 * @author miracle444
 */
public abstract class Deserializer
{
    
    private byte[] remainingData;       // contains remaining data of incomplete packets.
    private final Session session;      // the session this deserializer belongs to.
    private final int headerSize = 2;   // constant for the size of the header in bytes.
    
    
    /**
     * Constructor.
     * 
     * @param   session     the session this deserializer belongs to.
     */
    public Deserializer(Session session)
    {
        this.session = session;
    }

    
    /**
     * used by the deserialization filter component to manage incoming data 
     * 
     * @param   action  the action containing the buffer to be deserialized
     * @return  a list of successfully deserialized ShardletEventActions
     */
    public List<TriggerableAction> deserialize(TriggerableAction action)
    {
        // the result array containing all successfully deserialized actions
        List<TriggerableAction> result = new ArrayList<>();
        
        // get the buffer to deserialize from
        ByteBuffer buffer = action.getBuffer();
        
        // check if there is remaining data
        if (this.remainingData != null)
        {
            // set the buffer to its end
            buffer.position(buffer.limit());
            
            // add the data to the end
            buffer.put(this.remainingData);
        }
        
        // reset the buffer position
        buffer.position(0);
        
        // check if it has enough data to proceed
        while (buffer.remaining() >= this.headerSize)
        {
            // save restoration point
            int bufferRestorePosition = buffer.position();
            
            // get the header
            short header = buffer.getShort();
            
            // get the next action from the buffer with this specific header
            TriggerableAction nextAction = getNextAction(header, buffer);
            
            // check if deserialization failed
            if (nextAction == null)
            {
                // deserialization failed
                
                // restore buffer to last valid position
                buffer.position(bufferRestorePosition);
                
                // abort iteration
                break;
            }
            
            result.add(nextAction);
        }
        
        // check if the buffer still has remaining data
        if (buffer.hasRemaining())
        {
            // store remaining data
            remainingData = new byte[buffer.remaining()];
            buffer.get(remainingData);
        }
        else
        {
            remainingData = null;
        }
        
        return result;
    }
    
    
    /**
     * Tries to deserialize the next action from the buffer.
     * 
     * @param   header      the header of the next packets action to deserialize.
     * @param   buffer      the buffer to pull data from.
     * @return              null if not successful.
     *                      the deserialized next action if successful.
     */
    private TriggerableAction getNextAction(short header, ByteBuffer buffer)
    {
        TriggerableAction nextAction = createAction(header);

        if (nextAction == null)
        {
            // error: unknown header
            
            // clear the rest of the buffer and reset state
            buffer.position(buffer.limit());
            
            return null;
        }
        
        nextAction.init(this.session);
        nextAction.setBuffer(buffer);
        
        if (!nextAction.deserialize())
        {
            return null;
        }
        
        // set the buffer to null after deserialization
        // so this action can not mess up other actions afterwards
        nextAction.setBuffer(null);

        return nextAction;
    }
    
    
    /**
     * Should realize a header->action mapping.
     * This method needs to be overwritten by the extending class.
     * 
     * @param   header      the header to retrieve an action type from
     * @return              a new instance of an action type corresponding to the header
     */
    protected abstract TriggerableAction createAction(short header);
}
