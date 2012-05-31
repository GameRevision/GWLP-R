/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * Manages properties (getter/setter) of
 * an Action or a nested struct.
 * 
 * @author miracle444
 */
public final class PropertiesHelper 
{
    
    private final String indent;    // the number of spaces of an indentation
    private final String offset;    // a general offset indention for nested classes formatting
    private String code;            // contains the code this object is managing
    private PacketConverter packet; // the packet this converter belongs to
    
    
    /**
     * Constructor.
     * 
     * @param   indention   the number of spaces of an indentation.
     * @param   offset      a general offset indention for nested classes formatting.
     * @param   packet      the packet this converter belongs to.
     * @param   addHeader   whether to add the header property or not.
     */
    public PropertiesHelper(final String indention, final String offset, PacketConverter packet, boolean addHeader)
    {
        this.indent = indention;
        this.offset = offset;
        
        this.code = "";
        this.packet = packet;
        
        if (addHeader)
        {
            addCode("public short getHeader()");
            addCode("{");
            addCode(this.indent + "return " + packet.getHeader() + ";");
            addCode("}");
        }
    }
    
    
    /**
     * adds a line of code to the attribute "code".
     * helper routine to make the code more readable.
     * 
     * @param   code    the code to be added (without trailing newline)
     */
    private void addCode(String code)
    {
        this.code += this.offset + this.indent + code + "\n";                
    }
    
    
    /**
     * iteration routine to add fields to an action.
     * 
     * @param   field       the field that has to be added
     */
    public void addField(FieldConverter field)
    {
        if (!this.code.isEmpty())
        {
            this.code += "\n\n";
        }
        
        if (field.hasDescription())
        {
            addCode("/**");
            
            String[] description = field.getDescription();
            
            for (String line : description)
            {
                addCode(line);
            }
            
            addCode(" */");
        }
        
        if (!field.isArray())
        {
            addNoArrayPropertyMethod(field);
        }
        else
        {
            addArrayPropertyMethod(field);
        }
    }

    /**
     * is used to add a property method for
     * a non-array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addNoArrayPropertyMethod(FieldConverter field)
    {
        if (packet.getFromClient())
        {
            addCode("public " + field.getType() + " get" + field.getName() + "()");
            addCode("{");
            addCode(this.indent + "return " + field.getAttributeName() + ";");
            addCode("}");
        }
        else
        {
            addCode("public void set" + field.getName() + "(" + field.getType() + " newValue)");
            addCode("{");
            addCode(this.indent + field.getAttributeName() + " = newValue;");
            addCode("}");
        }
    }

    
    /**
     * is used to add a deserialization method for
     * an array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addArrayPropertyMethod(FieldConverter field) 
    {
        if (packet.getFromClient())
        {
            addCode("public " + field.getType() + "[] get" + field.getName() + "()");
            addCode("{");
            addCode(this.indent + "return " + field.getAttributeName() + ";");
            addCode("}");
        }
        else
        {
            addCode("public void set" + field.getName() + "(" + field.getType() + "[] newValue)");
            addCode("{");
            addCode(this.indent + field.getAttributeName() + " = newValue;");
            addCode("}");
        }
    }
    
    
    /**
     * Getter.
     * 
     * @return      the properties code.
     */
    @Override
    public String toString()
    {
        return this.code;
    }
}
