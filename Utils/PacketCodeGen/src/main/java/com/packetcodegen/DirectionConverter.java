/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import com.packetcodegen.jaxb.CommunicationDirection;

/**
 * Converts the packet direction xml data to
 * application specific properties.
 * 
 * @author miracle444
 */
public final class DirectionConverter
{
    
    private CommunicationDirection direction;   // stores the xml data
    private boolean fromClient;                 // stores if directions origin is the client side
    
    
    /**
     * Constructor.
     * 
     * @param       direction       the direction object this converter is going to convert from
     */
    public DirectionConverter(CommunicationDirection direction)
    {
        this.direction = direction;
        this.fromClient = direction.getAbbr().startsWith("C");
    }
    
    
    /**
     * Getter.
     * 
     * @return      the name of the server this direction represents.
     */
    public String getServerName()
    {
        if (fromClient)
        {
            if (direction.getAbbr().charAt(3) == 'L')
            {
                return "loginserver";
            }
        }
        else
        {
            if (direction.getAbbr().charAt(0) == 'L')
            {
                return "loginserver";
            }
        }
        
        return "gameserver";
    }

    
    /**
     * Getter.
     * 
     * @return  the abbreviation for the communication direction.
     */
    public String getDirectionString() 
    {
        return fromClient ? "ctos" : "stoc";
    }
    
    
    /**
     * Getter.
     * 
     * @return  true for client-> server, false for server->client.
     */
    public boolean getFromClient() 
    {
        return fromClient;
    }
    
    
    /**
     * Getter.
     * 
     * @return  the package name to be used in the header of an action.
     */
    public String getPackageName()
    {
        final String packageBase = "com.gamerevision.gwlpr.actions.";
        
        return packageBase+getServerName()+"."+getDirectionString();
    }
}
