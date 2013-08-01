/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions.utils;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An ASCII string wrapper class.
 * 
 * @author _rusty
 */
public class ASCIIString 
{
    
    static final Logger LOGGER = LoggerFactory.getLogger(ASCIIString.class);
    
    private String internal = "";
    
    
    public ASCIIString(String string)
    {
        internal = string;
    }
    
    
    public ASCIIString(byte[] asciiBytes) 
    {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : asciiBytes) 
        {
            sb.append((char)b);
        }
        
        internal = sb.toString();
    }
    
    
    @Override
    public String toString()
    {
        return internal;
    }
    
    
    public byte[] getBytes()
    {
        try 
        {
            return internal.getBytes("US-ASCII");
        } 
        catch (UnsupportedEncodingException ex) 
        {
            LOGGER.error("Failed to convert from string to ASCII bytecode.", ex);
            return new byte[] {};
        }
    }
    
    
    public int length()
    {
        return internal.length();
    }
}
