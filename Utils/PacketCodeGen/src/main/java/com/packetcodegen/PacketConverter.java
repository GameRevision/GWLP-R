/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import com.packetcodegen.jaxb.PacketType;

/**
 * Converts the packet xml data to
 * application specific properties.
 * 
 * @author miracle444
 */
public final class PacketConverter
{
    
    private DirectionConverter direction;   // stores the direction converter over this class
    private PacketType packet;              // stores the packet accessed by this class and its subclasses
    private int numberOfUnknowns;           // stores the number of unknown values to distribute numbers

    /**
     * Constructor.
     * 
     * @param       packet           the packet this converter should convert from.
     * @param       direction        the direction of this packet.
     */
    public PacketConverter(PacketType packet, DirectionConverter direction)
    {
        this.packet = packet;
        this.direction = direction;
        this.numberOfUnknowns = 0;
    }

    
    /**
     * Getter.
     * 
     * @return  the header of the packet.
     */
    public int getHeader()
    {
        return packet.getHeader().intValue();
    }
    
    
    /**
     * Getter.
     * 
     * @return  whether this packet comes from the client or not.
     */
    public boolean getFromClient()
    {
        return direction.getFromClient();
    }
    
    
    /**
     * Getter.
     * 
     * @return  the package name to be used for this packet. 
     */
    public String getPackageName()
    {
        return direction.getPackageName();
    }
    
    
    /**
     * Getter.
     * 
     * @return  the full action name of this packet.
     */
    public String getActionName()
    {
        return "P" + String.format("%03d", packet.getHeader()) + "_" + getPacketName() + "Action";
    }
    
    
    /**
     * Getter.
     * 
     * @return  returns an unused unknown number.
     */
    public int requestUnknownNumber() 
    {
        return ++numberOfUnknowns;
    }

    
    /**
     * Getter.
     * 
     * @return  the name of the packet. 
     */
    private String getPacketName()
    {
        if (packet.getInfo() == null)
        {
            return "Unknown";
        }

        if (packet.getInfo().getName() == null)
        {
            return "Unknown";
        }

        return packet.getInfo().getName();
    }

    
    /**
     * Getter.
     * 
     * @return  whether this packet has a description associated to it.
     */
    private boolean hasDescription()
    {
        if (packet.getInfo() == null)
        {
            return false;
        }

        if (packet.getInfo().getDescription() == null)
        {
            return false;
        }
        
        return !packet.getInfo().getDescription().isEmpty();
    }
    
    
    /**
     * Getter.
     * 
     * @return  the description associated to this packet.
     */
    public String getDescription() 
    {
        final int maximumLength = 50;
        
        if (!hasDescription())
        {
            return "";
        }
        
        String[] words = packet.getInfo().getDescription().split(" ");
        String description = "";
        String thisLine = "";
        
        for (String word : words)
        {
            thisLine += word + " ";
            
            if (thisLine.length() > maximumLength)
            {
                description += " * " + thisLine + "\n";
                thisLine = "";
            }
        }
        
        if (!thisLine.isEmpty())
        {
            description += " * " + thisLine + "\n";
        }
        
        return description;
    }

    
    /**
     * Getter.
     * 
     * @return  whether this packet has an author associated to it.
     */
    private boolean hasAuthor()
    {
        if (packet.getInfo() == null)
        {
            return false;
        }

        if (packet.getInfo().getAuthor() == null)
        {
            return false;
        }        
        
        return !packet.getInfo().getAuthor().isEmpty();
    }
    
    
    /**
     * Getter.
     * 
     * @return the author that is associated to this packet.
     */
    public String getAuthor() 
    {
        if (!hasAuthor())
        {
            return "PacketCodeGen";
        }
        
        return packet.getInfo().getAuthor();
    }
}
