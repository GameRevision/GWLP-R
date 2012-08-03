/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.loginshard.models;


/**
 * This model class has a simple implementation of a the methods used within the
 * client handshake process.
 * 
 * @author _rusty
 */
public class HandshakeModel
{
    /**
     * Check if a client's version is the right one
     * Imo, we should always accept the client regardless of its version...
     * 
     * @param   clientVersion           The version of the client.
     * @return  True if the version is ok
     */
    public static boolean verifyClientVersion(int clientVersion)
    {
        return true;
    }


    /**
     * Create the client's security stuff out of the client seed
     * 
     * @param clientSeed 
     */
    public static EncryptionDataHolder getEncrpytionData(byte[] clientSeed) 
    {
        return new EncryptionDataHolder();
    }
}
