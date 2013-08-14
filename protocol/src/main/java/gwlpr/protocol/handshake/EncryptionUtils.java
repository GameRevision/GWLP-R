package gwlpr.protocol.handshake;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Taken from iDemmel, with permission.
 * 
 * @author _rusty
 */
public class EncryptionUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(EncryptionUtils.class);

	public static final BigInteger PRIME, SERVER_PRIVATE_KEY;

	// TODO: configurable
	// static prime from the GW client
	// the first 0x00 is added because Java's byte encoding of BigInteger is
	// compatible with a scheme called DER encoding. If the high-order byte of
	// the positive BigInteger is >= 128 then the correct encoding is to prefix
	// the result with a 00 byte.
	private static final byte[] PRIME_ARRAY = { 0x00, (byte) 0xEB, 0x74, 0x42, 0x1E, (byte) 0x8C, 0x7F, (byte) 0xEE, (byte) 0xB7, (byte) 0xD8, 0x0A,
			(byte) 0xEF, 0x07, 0x7B, (byte) 0x94, 0x1D, 0x6D, (byte) 0xFA, (byte) 0xD3, (byte) 0x96, 0x59, 0x1C, (byte) 0x9C, (byte) 0xC0, 0x7A, 0x45, 0x3E,
			0x7E, (byte) 0xC9, 0x0C, (byte) 0xA5, (byte) 0xA1, (byte) 0xD7, (byte) 0x90, 0x5D, 0x29, (byte) 0x8E, 0x4D, 0x6D, (byte) 0xA4, (byte) 0xF4,
			(byte) 0x91, 0x61, (byte) 0xBD, 0x3B, (byte) 0xDB, (byte) 0x8C, (byte) 0xD6, 0x5C, 0x42, 0x55, (byte) 0x99, (byte) 0x99, 0x20, (byte) 0xD8,
			(byte) 0xDD, (byte) 0xA7, 0x78, 0x1B, 0x07, (byte) 0xD4, (byte) 0x97, (byte) 0xC0, (byte) 0xF6, (byte) 0xBB }; // BIG_ENDIAN
	private static final byte[] SERVER_PRIVATE_KEY_ARRAY = { 0x62, (byte) 0x96, 0x7D, 0x5E, 0x45, 0x6D, (byte) 0x8F, (byte) 0xF3, (byte) 0xA8, 0x66,
			(byte) 0xB8, (byte) 0xDD, (byte) 0xD1, (byte) 0xF7, (byte) 0xD0, (byte) 0xEA, 0x7D, 0x54, 0x12, (byte) 0x8F, 0x4C, 0x4F, 0x0F, 0x33, 0x3F, 0x58,
			0x7E, 0x44, 0x09, 0x70, (byte) 0xFF, (byte) 0xC1, (byte) 0xB7, (byte) 0x9D, 0x3B, (byte) 0xE7, 0x5F, 0x66, (byte) 0xB6, 0x2D, 0x6E, (byte) 0xA9,
			0x0D, (byte) 0xD2, 0x2B, 0x76, 0x53, (byte) 0xC8, 0x6E, 0x39, 0x23, (byte) 0xDD, (byte) 0xB7, (byte) 0xF1, 0x02, (byte) 0xC6, 0x63, 0x42,
			(byte) 0xF1, 0x1E, (byte) 0x83, 0x6E, 0x33, 0x21 }; // LITTLE_ENDIAN

        
	// the public server key and the generator can be found in the launcher
	static 
        {
		ArrayUtils.reverse(SERVER_PRIVATE_KEY_ARRAY); // for the lazy dev that didn't want to reverse the hardcoded key
		PRIME = new BigInteger(PRIME_ARRAY);
		SERVER_PRIVATE_KEY = new BigInteger(SERVER_PRIVATE_KEY_ARRAY);

		LOG.debug("Prime: {}", PRIME);
		LOG.debug("Private key (server): {}", SERVER_PRIVATE_KEY);
	}

        
	public static byte[] generateSharedKey(byte[] clientPublicKeyBytes) {
		// START Diffie-Hellman: generate a shared key (publicClientKey ^ serverPrivateKey % prime)
		ArrayUtils.reverse(clientPublicKeyBytes); // the client's public key is received in LITTLE_ENDIAN
		BigInteger clientPublicKey = new BigInteger(1, clientPublicKeyBytes);
		LOG.debug("Public key (client): {}", clientPublicKey);

		BigInteger sharedKey = clientPublicKey.modPow(EncryptionUtils.SERVER_PRIVATE_KEY, EncryptionUtils.PRIME);
		byte[] sharedKeyBytes = getBytesFromBigInteger(sharedKey);
		LOG.debug("Shared key: {} ({} bytes)", sharedKey, sharedKeyBytes.length);
		// END Diffie-Hellman
		return sharedKeyBytes;
	}
        
        
        public static byte[] generateServerPublicKey()
        {
            BigInteger pubKey = BigInteger.valueOf(4).modPow(SERVER_PRIVATE_KEY, PRIME);
            byte[] pubKeyBytes = getBytesFromBigInteger(pubKey);
            LOG.debug("Public key: {} ({} bytes)", pubKey, pubKeyBytes.length);
            
            return pubKeyBytes;
        }
	
        
	// TODO: write JUnit tests for these sets given by ACB:
	/*
	 * byte[] input1 = { 0x1B, 0x67, (byte)0xFF, (byte)0xB6, 0x12, (byte)0x94, 0x49, (byte)0x9B, 0x5D, 0x12, 0x72, 0x0E, 0x11, (byte)0xAA, 0x43, 0x08,
	 * (byte)0x8E, 0x26, (byte)0xB3, 0x66 }; byte[] needed1 = { 0x4B, (byte)0xB9, 0x73, (byte)0xCD, (byte)0xCA, 0x6E, 0x46, 0x3A, 0x6C, 0x21, 0x3E, (byte)0x99,
	 * 0x2E, 0x4B, 0x4A, 0x0B, 0x42, 0x13, (byte)0xEC, (byte)0xD6 }; byte[] input = { (byte) 0xD3, 0x65, (byte) 0x9F, 0x05, (byte) 0xB8, 0x79, 0x07, 0x5B,
	 * (byte) 0xCB, 0x25, (byte) 0x95, 0x51, 0x1B, 0x53, 0x56, 0x19, (byte) 0xEB, 0x24, (byte) 0xA8, 0x45 }; byte[] needed = { 0x03, (byte) 0xB8, 0x13, 0x1C,
	 * 0x70, 0x54, 0x04, (byte) 0xFA, 0x3A, (byte) 0xDE, 0x5B, 0x79, (byte) 0xEA, 0x14, 0x7F, (byte) 0x92, 0x67, 0x40, 0x64, (byte) 0xE1 };
	 */
	public static byte[] hash(byte[] state) 
        {
		ByteBuffer bb = ByteBuffer.wrap(state).order(ByteOrder.LITTLE_ENDIAN);

		long local6 = bb.getInt() & 0xFFFFFFFFL;
		long local5 = bb.getInt() & 0xFFFFFFFFL;
		long local4 = bb.getInt() & 0xFFFFFFFFL;
		long local3 = bb.getInt() & 0xFFFFFFFFL;
		long local2 = bb.getInt() & 0xFFFFFFFFL;
		long eax, ebx, ecx, edx, edi, esi, arg1;

		ecx = local6;// MOV ECX,DWORD PTR SS:[LOCAL.6]
		eax = 0x67452301L;// MOV EAX,67452301
		eax = ROL(eax, 5);// ROL EAX,5
		edi = local5;// MOV EDI,DWORD PTR SS:[LOCAL.5]
		esi = ecx + eax + 0xB7103887L;// LEA ESI,[ECX+EAX+B7103887]
		eax = 0xEFCDAB89L;// MOV EAX,EFCDAB89
		eax = ROL(eax, 0x1E);// ROL EAX,1E
		edx = esi;// MOV EDX,ESI
		ecx = eax;// MOV ECX,EAX
		edx = ROL(edx, 5);// ROL EDX,5
		ecx &= 0x67452301L;// AND ECX,67452301
		edi += edx;// ADD EDI,EDX
		ecx ^= 0x98BADCFEL;// XOR ECX,98BADCFE
		edx = 0x67452301L;// MOV EDX,67452301
		edx = ROL(edx, 0x1E);// ROL EDX,1E
		edi = edi + ecx + 0x6AB4CE0FL;// LEA EDI,[EDI+ECX+nvd3dum.6AB4CE0F]
		ebx = eax;// MOV EBX,EAX
		ecx = edi;// MOV ECX,EDI
		ebx ^= edx;// XOR EBX,EDX
		ecx = ROL(ecx, 5);// ROL ECX,5
		ecx += local4;// ADD ECX,DWORD PTR SS:[LOCAL.4]
		ebx &= esi;// AND EBX,ESI
		ebx ^= eax;// XOR EBX,EAX
		arg1 = edi;// MOV DWORD PTR SS:[ARG.1],EDI
		esi = ROL(esi, 0x1E);// ROL ESI,1E
		ecx = ecx + ebx + 0xF33D5697L;// LEA ECX,[ECX+EBX+F33D5697]
		ebx = edx;// MOV EBX,EDX
		ebx = ROL(ebx, 5);// ROL EBX,5
		ebx += local3;// ADD EBX,DWORD PTR SS:[LOCAL.3]
		edi ^= ecx;// XOR EDI,ECX
		edi ^= eax;// XOR EDI,EAX
		ebx += esi;// ADD EBX,ESI
		eax = ROL(eax, 0x1E);// ROL EAX,1E
		esi = ebx + edi + 0x6ED9EBA1L;// LEA ESI,[EBX+EDI+6ED9EBA1]
		ebx = local6;// MOV EBX,DWORD PTR SS:[LOCAL.6]
		edi = edx;// MOV EDI,EDX
		edi = ROL(edi, 0x1E);// ROL EDI,1E
		ebx += edi;// ADD EBX,EDI
		edi = local4;// MOV EDI,DWORD PTR SS:[LOCAL.4]
		local6 = ebx;// MOV DWORD PTR SS:[LOCAL.6],EBX
		ebx = local5;// MOV EBX,DWORD PTR SS:[LOCAL.5]
		edi += ecx;// ADD EDI,ECX
		ebx += eax;// ADD EBX,EAX
		local4 = edi;// MOV DWORD PTR SS:[LOCAL.4],EDI
		ecx ^= eax;// XOR ECX,EAX
		eax = local2;// MOV EAX,DWORD PTR SS:[LOCAL.2]
		edi = esi;// MOV EDI,ESI
		ecx ^= edx;// XOR ECX,EDX
		edx = eax;// MOV EDX,EAX
		edi = ROL(edi, 5);// ROL EDI,5
		edx += edi;// ADD EDX,EDI
		eax += esi;// ADD EAX,ESI
		ecx += edx;// ADD ECX,EDX
		edx = arg1;// MOV EDX,DWORD PTR SS:[ARG.1]
		ecx += edx;// ADD ECX,EDX
		edx = local3;// MOV EDX,DWORD PTR SS:[LOCAL.3]
		local2 = eax;// MOV DWORD PTR SS:[LOCAL.2],EAX
		// eax = local1;//MOV EAX,DWORD PTR SS:[LOCAL.1]
		ecx = ecx + edx + 0x6ED9EBA1L;// LEA ECX,[ECX+EDX+6ED9EBA1]
		edx ^= edx;// XOR EDX,EDX
		local3 = ecx;// MOV DWORD PTR SS:[LOCAL.3],ECX
		local5 = ebx;// MOV DWORD PTR SS:[LOCAL.5],EBX

		return ByteBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN).putInt((int) local6).putInt((int) local5).putInt((int) local4).putInt((int) local3)
				.putInt((int) local2).array();
	}

        
	private static long ROL(long value, long distance) 
        {
		return uint(value << distance) | (uint(value) >>> (32 - distance));
	}
        
        
        public static byte[] XOR(byte[] value, byte[] key) 
        {
		byte[] xorValue = new byte[value.length];

		for (int i = 0; i < xorValue.length; i++) 
                {
			xorValue[i] = (byte) (value[i] ^ key[i]);
		}

		return xorValue;
	}

        
	private static long uint(long value) 
        {
		return value & 0xFFFFFFFFL;
	}
	
        
        private static byte[] getBytesFromBigInteger(BigInteger bi) 
        {
		byte[] bytes = bi.toByteArray();

		if (bytes[0] == (byte) 0x00) { // remove the padding zero added by BigInteger in case the number is "negative"
			byte[] tmp = new byte[bytes.length - 1];
			System.arraycopy(bytes, 1, tmp, 0, bytes.length - 1);
			bytes = tmp;
		}
		ArrayUtils.reverse(bytes); // must be reversed as BigInteger works with BIG_ENDIAN data
		return bytes;
	}
}
