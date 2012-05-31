/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import com.packetcodegen.jaxb.PacketFieldType;
import com.packetcodegen.jaxb.PacketSimpleTypes;
import java.util.ArrayList;

/**
 * Converts the field xml data to
 * application specific properties.
 * 
 * @author miracle444
 */
public final class FieldConverter 
{
    private PacketConverter parent; // the packet converter of its parent class
    private PacketFieldType field;  // stores the xml based field data of this class
    private int unknownNumber;      // stores the unknown value of this class


    /**
     * Constructor.
     * 
     * @param       field           the field the converter has to convert from
     * @param       parent          the packet this field belongs to
     */
    public FieldConverter(PacketFieldType field, PacketConverter parent)
    {
        this.field = field;
        this.parent = parent;
    }


    /**
     * Getter.
     * 
     * @return  true if the field is a static array.
     */
    public boolean getStatic()
    {
        return field.isStatic() == null ? true : field.isStatic().booleanValue();
    }


    /**
     * Getter.
     * 
     * @return  the type of a the prefix.
     */
    public String getPrefixType()
    {
        return convertType(field.getPrefixType());
    }


    /**
     * Getter.
     * 
     * @return  the type of a the field.
     */
    public String getType() 
    {
        if (isNested())
        {
            // give it a class name
            return "Nested" + getName();
        }
        
        return convertType(field.getType());
    }
    

    /**
     * Helper function.
     * 
     * @param   type    the type that has to be converted.
     * @return  the corresponding string value to a type.
     */
    private String convertType(PacketSimpleTypes type)
    {
        if (type == null)
        {
            return "";
        }
        
        if (type.equals(PacketSimpleTypes.AGENTID))
        {
            return "int";
        }
        else if (type.equals(PacketSimpleTypes.ASCII))
        {
            return "byte";
        }
        else if (type.equals(PacketSimpleTypes.FLOAT))
        {
            return "float";
        }
        else if (type.equals(PacketSimpleTypes.INT_16))
        {
            return "short";
        }
        else if (type.equals(PacketSimpleTypes.INT_32))
        {
            return "int";
        }
        else if (type.equals(PacketSimpleTypes.INT_64))
        {
            return "long";
        }
        else if (type.equals(PacketSimpleTypes.INT_8))
        {
            return "byte";
        }
        else if (type.equals(PacketSimpleTypes.PACKED))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UTF_16))
        {
            return "char";
        }
        else if (type.equals(PacketSimpleTypes.UUID_16))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UUID_28))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.VEC_2))
        {
            return "float[]";
        }
        else if (type.equals(PacketSimpleTypes.VEC_3))
        {
            return "float[]";
        }
        else if (type.equals(PacketSimpleTypes.VEC_4))
        {
            return "float[]";
        }

        // dummy value because not all types are implemented
        return type.toString();
    }


    /**
     * Getter.
     * 
     * @return  the size of the prefix.
     */
    public int getPrefixSize()
    {
        return convertSize(field.getPrefixType());
    }


    /**
     * Getter.
     * 
     * @return  the size of the field's type.
     */
    public int getSize() 
    {
        return convertSize(field.getType());
    }


    /**
     * Helper function.
     * 
     * @param   type    the type to get the size from.
     * @return  the size of a type.
     */
    private int convertSize(PacketSimpleTypes type)
    {
        if (type.equals(PacketSimpleTypes.AGENTID))
        {
            return 4;
        }
        else if (type.equals(PacketSimpleTypes.ASCII))
        {
            return 1;
        }
        else if (type.equals(PacketSimpleTypes.FLOAT))
        {
            return 4;
        }
        else if (type.equals(PacketSimpleTypes.INT_16))
        {
            return 2;
        }
        else if (type.equals(PacketSimpleTypes.INT_32))
        {
            return 4;
        }
        else if (type.equals(PacketSimpleTypes.INT_64))
        {
            return 8;
        }
        else if (type.equals(PacketSimpleTypes.INT_8))
        {
            return 1;
        }
        else if (type.equals(PacketSimpleTypes.PACKED))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UTF_16))
        {
            return 2;
        }
        else if (type.equals(PacketSimpleTypes.UUID_16))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UUID_28))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.VEC_2))
        {
            return 8;
        }
        else if (type.equals(PacketSimpleTypes.VEC_3))
        {
            return 12;
        }
        else if (type.equals(PacketSimpleTypes.VEC_4))
        {
            return 16;
        }
        
        // dummy value because not all types are implemented
        return 0;
    }
    
    
    /**
     * Getter.
     * 
     * @return  the size of the field's type.
     */
    public boolean isNested()
    {
        return field.getType().equals(PacketSimpleTypes.NESTED);
    }


    /**
     * Getter.
     * 
     * @return  the size of the field's type.
     */
    public String getName() 
    {
        if (field.getInfo() == null)
        {
            return "Unknown" + getUnknownNumber();
        }

        if (field.getInfo().getName() == null)
        {
            return "Unknown" + getUnknownNumber();
        }

        return field.getInfo().getName().toString();
    }


    /**
     * Getter.
     * 
     * @return  the number to enumerate an unknown field.
     */
    public int getUnknownNumber()
    {
        if (unknownNumber == 0)
        {
            unknownNumber = parent.requestUnknownNumber();
        }

        return unknownNumber;
    }


    /**
     * Getter.
     * 
     * @return  the name of this field as an attribute.
     */
    public String getAttributeName() 
    {
        String attributeName = getName();

        return attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1, attributeName.length());
    }

    
    /**
     * Getter.
     * 
     * @return  if this field has a description associated to it.
     */
    public boolean hasDescription()
    {
        if (field.getInfo() == null)
        {
            return false;
        }

        if (field.getInfo().getDescription() == null)
        {
            return false;
        }
        
        return !field.getInfo().getDescription().isEmpty();
    }
        
    
    /**
     * Getter.
     * 
     * @return  the description associated to this field.
     */
    public String[] getDescription() 
    {
        final int maximumLength = 50;
        
        if (!hasDescription())
        {
            return null;
        }
        
        String[] words = field.getInfo().getDescription().split(" ");
        ArrayList<String> description = new ArrayList<>();
        String thisLine = "";
        
        for (String word : words)
        {
            thisLine += word + " ";
            
            if (thisLine.length() > maximumLength)
            {
                description.add(" * " + thisLine);
                thisLine = "";
            }
        }
        
        if (!thisLine.isEmpty())
        {
            description.add(" * " + thisLine);
        }
        
        
        return description.toArray(new String[description.size()]);
    }


    /**
     * Getter.
     * 
     * @return  the size of a static array.
     */
    public int getOccurs() 
    {
        return field.getOccurs() == null ? -1 : field.getOccurs().intValue();
    }
    
    
    /**
     * Getter.
     * 
     * @return  whether this field is an array.
     */
    public boolean isArray()
    {
        return field.getOccurs() != null;
    }
    
    
    /**
     * Getter.
     * 
     * @return  the buffer method for a prefix.
     */
    public String getPrefixBufferMethod() 
    {
        return convertBufferMethod(field.getPrefixType());
    }


    /**
     * Getter.
     * 
     * @return  the buffer method of a primitive type.
     */
    public String getBufferMethod() 
    {
        return convertBufferMethod(field.getType());
    }


    /**
     * Helper function.
     * 
     * @param   type    converts a primitive type to its corresponding buffer method
     * @return  the buffer method corresponding to type.
     */
    private String convertBufferMethod(PacketSimpleTypes type) 
    {
        if (type == null)
        {
            return "";
        }
        
        if (type.equals(PacketSimpleTypes.AGENTID))
        {
            return "Int";
        }
        else if (type.equals(PacketSimpleTypes.ASCII))
        {
            return "";
        }
        else if (type.equals(PacketSimpleTypes.FLOAT))
        {
            return "Float";
        }
        else if (type.equals(PacketSimpleTypes.INT_16))
        {
            return "Short";
        }
        else if (type.equals(PacketSimpleTypes.INT_32))
        {
            return "Int";
        }
        else if (type.equals(PacketSimpleTypes.INT_64))
        {
            return "Long";
        }
        else if (type.equals(PacketSimpleTypes.INT_8))
        {
            return "";
        }
        else if (type.equals(PacketSimpleTypes.PACKED))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UTF_16))
        {
            return "Char";
        }
        else if (type.equals(PacketSimpleTypes.UUID_16))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.UUID_28))
        {
            // TODO: implement it
            // i didnt know what to do with this type ~miracle444
        }
        else if (type.equals(PacketSimpleTypes.VEC_2))
        {
            // this path will never be used as its handled seperately
        }
        else if (type.equals(PacketSimpleTypes.VEC_3))
        {
            // this path will never be used as its handled seperately
        }
        else if (type.equals(PacketSimpleTypes.VEC_4))
        {
            // this path will never be used as its handled seperately
        }

        // dummy value because not all types are implemented/usable
        return type.toString();
    }

    
    /**
     * Getter.
     * 
     * @return  whether this field is a vector type.
     */
    public boolean isVector()
    {
        return getVectorSize() > 0;
    }
    
    
    /**
     * Getter.
     * 
     * @return  the vector size.
     */
    public int getVectorSize()
    {
        if (field.getType().equals(PacketSimpleTypes.VEC_2))
        {
            return 2;
        }
        else if (field.getType().equals(PacketSimpleTypes.VEC_3))
        {
            return 3;
        }
        else if (field.getType().equals(PacketSimpleTypes.VEC_4))
        {
            return 4;
        }
        
        return 0;
    }
}
