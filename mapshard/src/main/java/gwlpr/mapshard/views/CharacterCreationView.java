/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.mapshard.views;


import gwlpr.database.entities.Character;
import gwlpr.mapshard.models.enums.ErrorCode;
import gwlpr.protocol.gameserver.outbound.P141_Unknown;
import gwlpr.protocol.gameserver.outbound.P379_CharacterCreateHead;
import gwlpr.protocol.gameserver.outbound.P380_CharacterCreateAcknowledge;
import gwlpr.protocol.gameserver.outbound.P378_Unknown;
import gwlpr.protocol.gameserver.outbound.P381_CharacterCreateError;
import io.netty.channel.Channel;
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
    public static void charCreateHead(Channel channel)
    {
        P379_CharacterCreateHead startCharacterCreation = new P379_CharacterCreateHead();
        startCharacterCreation.init(channel);
        
        channel.writeAndFlush(startCharacterCreation);
    }

    
    /**
     * Step 2.
     */
    public static void charCreateAck(Channel channel)
    {
        P380_CharacterCreateAcknowledge createCharacterAck = new P380_CharacterCreateAcknowledge();
        createCharacterAck.init(channel);
        
        channel.writeAndFlush(createCharacterAck);
    }
    
    
    /**
     * Step 3.
     */
    public static void unkownStep1(Channel channel)
    {
        P141_Unknown dAction = new P141_Unknown();
        dAction.init(channel);
        dAction.setUnknown1((short) 248);
        
        channel.writeAndFlush(dAction);
    }
    
    
    /**
     * Step 3.1. Abort due to false name...
     */
    public static void error(Channel channel, ErrorCode errorCode)
    {
        P381_CharacterCreateError mAction = new P381_CharacterCreateError();
        mAction.init(channel);
        mAction.setErroCode(errorCode.get());
        
        channel.writeAndFlush(mAction);
    }
    
    
    /**
     * Step 4. (final step)
     * TODO fix this and reverse the packet more!
     */
    public static void charCreateFinish(Channel channel, Character chara)
    {
        P378_Unknown sAction = new P378_Unknown();
        sAction.init(channel);
        sAction.setUnknown1(new byte[16]); // TODO probably char UID
        sAction.setUnknown2(chara.getName());
        sAction.setUnknown3((short) 81);
        
        ByteBuffer buffer = ByteBuffer.allocate(100).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 6);
        buffer.putShort((short) 248); // TODO current location (game-map-id)
        buffer.put(new byte[] {0x33, 0x36, 0x31, 0x30});
        
        buffer.put((byte) ((chara.getSkin().byteValue() << 5) | (chara.getHeight().byteValue() << 1) | chara.getSex().byteValue()));
        buffer.put((byte) ((chara.getFace().byteValue() << 7) | (chara.getSkin().byteValue() >> 3)));
        buffer.put((byte) ((chara.getPrimaryProfession().getId().byteValue() << 4) | (chara.getFace().byteValue() >> 1)));
        buffer.put((byte) ((chara.getCampaign().byteValue() << 6) | chara.getHairstyle().byteValue()));
        
        buffer.put(new byte[16]);

        byte level = chara.getLevel().getLevel().byteValue();
        buffer.put((byte) ((level << 4) | chara.getCampaign().byteValue()));                                                   
        
        buffer.put(new byte[] {-1, -0x23, -0x23, 0, -0x23, -0x23, -0x23, -0x23});
        
        byte[] a = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(a);
        
        sAction.setUnknown4(a);

        channel.writeAndFlush(sAction);
    }
}
