/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.mapshard.views;

import com.gamerevision.gwlpr.actions.gameserver.stoc.P141_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P378_UnknownAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P379_CharacterCreateHeadAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P380_CharacterCreateAcknowledgeAction;
import com.gamerevision.gwlpr.actions.gameserver.stoc.P381_UnknownAction;
import com.gamerevision.gwlpr.database.DBCharacter;
import com.realityshard.shardlet.Session;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * This view fills a CreateCharacterAck action
 * 
 * @author miracle444
 */
public class CharacterCreationView
{
    
    /**
     * Step 1.
     */
    public static void charCreateHead(Session session)
    {
        P379_CharacterCreateHeadAction startCharacterCreation = new P379_CharacterCreateHeadAction();
        startCharacterCreation.init(session);
        
        session.send(startCharacterCreation);
    }

    
    /**
     * Step 2.
     */
    public static void charCreateAck(Session session)
    {
        P380_CharacterCreateAcknowledgeAction createCharacterAck = new P380_CharacterCreateAcknowledgeAction();
        createCharacterAck.init(session);
        
        session.send(createCharacterAck);
    }
    
    
    /**
     * Step 3.
     */
    public static void unkownStep1(Session session)
    {
        P141_UnknownAction dAction = new P141_UnknownAction();
        dAction.init(session);
        dAction.setUnknown1((short) 248);
        
        session.send(dAction);
    }
    
    
    /**
     * Step 3.1. Abort due to false name...
     */
    public static void abort(Session session)
    {
        P381_UnknownAction mAction = new P381_UnknownAction();
        mAction.init(session);
        mAction.setUnknown1(29);
        
        session.send(mAction);
    }
    
    
    /**
     * Step 4. (final step)
     */
    public static void charCreateFinish(Session session, DBCharacter chara)
    {
        P378_UnknownAction sAction = new P378_UnknownAction();
        sAction.init(session);
        sAction.setUnknown1(new byte[16]);
        sAction.setUnknown2(chara.getName().toCharArray());
        sAction.setUnknown3((short) 81);
        
        ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 6);
        buffer.putShort((short) 248);
        buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});
        
        buffer.put((byte) ((chara.getSkin() << 5) | (chara.getHeight() << 1) | chara.getSex()));
        buffer.put((byte) ((chara.getFace() << 7) | (chara.getSkin() >> 3)));
        buffer.put((byte) ((chara.getPrimary() << 4) | (chara.getFace() >> 1)));
        buffer.put((byte) ((chara.getCampaign() << 6) | chara.getHairstyle()));
        
        buffer.put(new byte[16]);

        byte level = 0; // TODO: replace this dummy variable
        buffer.put((byte) ((level << 4) | chara.getCampaign()));                                                   
        
        buffer.put(new byte[] {-1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
        byte[] a = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(a);
        sAction.setUnknown4(a);

        session.send(sAction);
    }
}
