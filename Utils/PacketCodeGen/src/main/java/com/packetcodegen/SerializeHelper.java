/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * This class manages serialization routines
 * for Action and nested structs.
 * 
 * @author miracle444
 */
public final class SerializeHelper 
{
    
    private final String indent;    // the number of spaces of an indentation
    private final String offset;    // a general offset indention for nested classes formatting
    private final boolean nested;   // indicates if its used by a nested struct helper or an class helper
    private String code;            // contains the code this object is managing
    
    
    /**
     * Constructor.
     * 
     * @param      indention       // the number of spaces of an indentation
     * @param      offset          // a general offset indention for nested classes formatting
     * @param      nested          // indicates if its used by a nested struct helper or an class helper
     */
    public SerializeHelper(final String indention, final String offset, final boolean nested)
    {
        this.indent = indention;
        this.offset = offset;
        this.nested = nested;
        this.code = "";
    }
    
    
    /**
     * adds a line of code to the attribute "code".
     * helper routine to make the code more readable.
     * 
     * @param   code    the code to be added (without trailing newline)
     */
    private void addCode(String code)
    {
        this.code += this.offset + this.indent + this.indent + this.indent + code + "\n";                
    }
            
    
    /**
     * iteration routine to add fields to an action.
     * 
     * @param   field       the field that has to be added
     */
    public void addField(FieldConverter field)
    {        
        if (!field.isArray())
        {
            addNoArraySerializeMethod(field);
        }
        else
        {
            if (!field.getStatic())
            {
                addDynamicArraySerializeMethod(field);
            }
            else
            {
                addStaticArraySerializeMethod(field);
            }
        }
    }

    
    /**
     * is used to add a serialization method for
     * a non-array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addNoArraySerializeMethod(FieldConverter field)
    {
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode("buffer.put" + field.getBufferMethod() + "(" + field.getAttributeName() + ");");
            }
            else
            {                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode("buffer.putFloat(" + field.getAttributeName() + "[" + i + "]);");
                }
            }
        }
        else
        {
            addCode("if (!" + field.getAttributeName() + "Value.serialize(buffer))");
            addCode("{");
            addCode(this.indent + "return false;");
            addCode("}");
        }
    }

    /**
     * is used to add a serialization method for
     * a dynamic size array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addDynamicArraySerializeMethod(FieldConverter field) 
    {      
        addCode(field.getPrefixType() + " prefix_" + field.getAttributeName() + ";");
        addCode("if (" + field.getAttributeName() + " == null)");
        addCode("{");
        addCode(indent + "prefix_" + field.getAttributeName() + " = 0;");
        addCode("}");
        addCode("else");
        addCode("{");
        addCode(indent + "prefix_" + field.getAttributeName() + " = " + 
                            (field.getPrefixType().equals("int") ? "" : "(" + field.getPrefixType() + ") ") + 
                            field.getAttributeName() + ".length;");
        addCode("}");
        addCode("buffer.put" + field.getPrefixBufferMethod() + "(prefix_" + field.getAttributeName() + ");");
        addCode("");
        addCode("for (int i = 0; i < prefix_" + field.getAttributeName() + "; i++)");
        addCode("{");
            
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode(indent + "buffer.put" + field.getBufferMethod() + "(" + field.getAttributeName() + "[i]);");          
            }
            else
            {                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode(indent + "buffer.putFloat(" + field.getAttributeName() + "[i][" + i + "]);");
                }
            }
        }
        else
        {
            addCode(indent + "if (!" + field.getAttributeName() + "[i].serialize(buffer))");
            addCode(indent + "{");
            addCode(indent + indent + "return false;");
            addCode(indent + "}");
        }
        
        addCode("}");
    }

    
    /**
     * is used to add a serialization method for
     * a static array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addStaticArraySerializeMethod(FieldConverter field)
    {
        addCode("for (int i = 0; i < " + field.getOccurs() + "; i++)");
        addCode("{");
        
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode(indent + "buffer.put" + field.getBufferMethod() + "(" + field.getAttributeName() + "[i]);");          
            }
            else
            {                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode(indent + "buffer.putFloat(" + field.getAttributeName() + "[i][" + i + "]);");
                }
            }
        }
        else
        {
            addCode(indent + "if (!" + field.getAttributeName() + "[i].serialize(buffer))");
            addCode(indent + "{");
            addCode(indent + indent + "return false;");
            addCode(indent + "}");
        }
        
        addCode("}");
    }
    
    
    /**
     * Getter.
     * 
     * @return      the serialisation code.
     */
    @Override
    public String toString()
    {
        if (!this.nested)
        {
            return
                    this.offset + indent + "@Override\n" +
                    this.offset + indent + "public boolean serialize()\n" +
                    this.offset + indent + "{\n" +
                    this.offset + indent + indent + "int size = getSize();\n\n" +
                    this.offset + indent + indent + "if (size == 0)\n" +
                    this.offset + indent + indent + "{\n" +
                    this.offset + indent + indent + indent + "return false;\n" +
                    this.offset + indent + indent + "}\n" +
                                                    "\n" +
                    this.offset + indent + indent + "ByteBuffer buffer = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN);\n" +
                                                    "\n" +
                    this.offset + indent + indent + "try\n" +
                    this.offset + indent + indent + "{\n" +
                    this.offset + indent + indent + indent + "buffer.putShort(getHeader());" + "\n\n" +
                                                    code +
                    this.offset + indent + indent + "}\n" +
                    this.offset + indent + indent + "catch (BufferOverflowException e)\n" +
                    this.offset + indent + indent + "{\n" +
                    this.offset + indent + indent + indent + "return false;\n" +
                    this.offset + indent + indent + "}\n" +
                                                    "\n" +
                    this.offset + indent + indent + "setBuffer(buffer);\n\n" +
                    this.offset + indent + indent + "return true;\n" +
                    this.offset + indent + "}\n";
        }
        else
        {
            return
                    this.offset + indent + "public boolean serialize(ByteBuffer buffer)\n" +
                    this.offset + indent + "{\n" +
                    this.offset + indent + indent + "try\n" +
                    this.offset + indent + indent + "{\n" +
                                           code +
                    this.offset + indent + indent + "}\n" +
                    this.offset + indent + indent + "catch (BufferOverflowException e)\n" +
                    this.offset + indent + indent + "{\n" +
                    this.offset + indent + indent + indent + "return false;\n" +
                    this.offset + indent + indent + "}\n" +
                                                    "\n" +
                    this.offset + indent + indent + "return true;\n" +
                    this.offset + indent + "}\n";
        }
    }
}
