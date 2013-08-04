/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * RC4 encryption handler, making use of the built-in cipher
 * Taken from iDemmel, with permission
 * 
 * @author _rusty
 */
public class NettyRC4Codec extends MessageToByteEncoder<ByteBuf>
{
    
    public enum Mode
    {
        ENCRYPT_MODE(1),
        DECRYPT_MODE(2);
        
        private final int id;
        private Mode(int id) { this.id = id; }
        public int val() { return id; }
    }
    
    private final Cipher rc4Encrypt;
    
    
    public NettyRC4Codec(byte[] rc4Key, Mode mode)
    {
        try 
        {
            SecretKeySpec rc4KeySpec = new SecretKeySpec(rc4Key, "RC4");

            this.rc4Encrypt = Cipher.getInstance("RC4");
            this.rc4Encrypt.init(mode.val(), rc4KeySpec);

        } 
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) 
        {
                throw new RuntimeException(e);
        }
    }
    

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception 
    {
        byte[] dataToBeEncrypted = new byte[in.readableBytes()];
        in.readBytes(dataToBeEncrypted);

        byte[] encryptedBytes = this.rc4Encrypt.update(dataToBeEncrypted);

        out.writeBytes(encryptedBytes);
    }
}
