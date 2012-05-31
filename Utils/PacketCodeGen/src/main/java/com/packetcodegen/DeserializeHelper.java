/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * This class manages deserialization routines
 * for Action and nested structs.
 * 
 * @author miracle444
 */
public final class DeserializeHelper
{
    
    private final String indent;    // the number of spaces of an indentation
    private final String offset;    // a general offset indention for nested classes formatting
    private final boolean nested;   // indicates if its used by a nested struct helper or an class helper
    private String code;            // contains the code this object is managing

    
    /**
     * Constructor.
     * 
     * @param       indention       // the number of spaces of an indentation
     * @param       offset          // a general offset indention for nested classes formatting
     * @param       nested          // indicates if its used by a nested struct helper or an class helper
     */
    public DeserializeHelper(final String indention, final String offset, final boolean nested)
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
            addNoArrayDeserializeMethod(field);
        }
        else
        {
            if (!field.getStatic())
            {
                addDynamicArrayDeserializeMethod(field);
            }
            else
            {
                addStaticArrayDeserializeMethod(field);
            }
        }
    }

    
    /**
     * is used to add a deserialization method for
     * a non-array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addNoArrayDeserializeMethod(FieldConverter field)
    {
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode(field.getAttributeName() + " = buffer.get" + field.getBufferMethod() + "();");
            }
            else
            {
                addCode(field.getAttributeName() + " = new float[" + field.getVectorSize() + "];");
                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode(field.getAttributeName() + "[" + i + "] = buffer.getFloat();");
                }
            }
        }
        else 
        {
            addCode(field.getType() + " " + field.getAttributeName() + "Value = new " + field.getType() + "();");
            addCode("if (!" + field.getAttributeName() + "Value.deserialize(buffer))");
            addCode("{");
            addCode(this.indent + "buffer.position(bufferPosition);");
            addCode(this.indent + "return false;");
            addCode("}");
            addCode(field.getAttributeName() + " = " + field.getAttributeName() + "Value;");
        }
    }


    
    /**
     * is used to add a deserialization method for
     * a dynamic size array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addDynamicArrayDeserializeMethod(FieldConverter field) 
    {
        addCode(field.getPrefixType() + " prefix_" + field.getAttributeName() + " = buffer.get" + field.getPrefixBufferMethod() + "();");
        addCode(field.getAttributeName() + " = new " + field.getType() + "[prefix_" + field.getAttributeName() + "];");
        addCode("");
        addCode("for (int i = 0; i < prefix_" + field.getAttributeName() + "; i++)");
        addCode("{");
        
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode(this.indent + field.getAttributeName() + "[i] = buffer.get" + field.getBufferMethod() + "();");
            }
            else
            {
                addCode("float[] newEntry = float[" + field.getVectorSize() + "];");
                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode("newEntry[" + i + "] = buffer.getFloat();");
                }
                
                addCode(field.getAttributeName() + "[i] = newEntry;");
            }
        }
        else
        {
            addCode(this.indent + field.getType() + " newEntry = new " + field.getType() + "();");
            addCode("");
            addCode(this.indent + "if (!newEntry.deserialize(buffer))");
            addCode(this.indent + "{");
            addCode(this.indent + this.indent + "buffer.position(bufferPosition);");
            addCode(this.indent + this.indent + "return false;");
            addCode(this.indent + "}");
            addCode("");
            addCode(this.indent + field.getAttributeName() + "[i] = newEntry;");
        }
        
        addCode("}");
    }

    
    /**
     * is used to add a deserialization method for
     * a static array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addStaticArrayDeserializeMethod(FieldConverter field)
    {
        addCode(field.getAttributeName() + " = new " + field.getType() + "[" + field.getOccurs() + "];");
        addCode("");
        addCode("for (int i = 0; i < " + field.getOccurs() + "; i++)");
        addCode("{");
        
        if (!field.isNested())
        {
            if (!field.isVector())
            {
                addCode(this.indent + field.getAttributeName() + "[i] = buffer.get" + field.getBufferMethod() + "();");
            }
            else
            {
                addCode("float[] newEntry = float[" + field.getVectorSize() + "];");
                
                for (int i = 0; i < field.getVectorSize(); i++)
                {
                    addCode("newEntry[" + i + "] = buffer.getFloat();");
                }
                
                addCode(field.getAttributeName() + "[i] = newEntry;");
            }
        }
        else
        {
            addCode(this.indent + field.getType() + " newEntry = new " + field.getType() + "();");
            addCode("");
            addCode(this.indent + "if (!newEntry.deserialize(buffer))");
            addCode(this.indent + "{");
            addCode(this.indent + this.indent + "buffer.position(bufferPosition);");
            addCode(this.indent + this.indent + "return false;");
            addCode(this.indent + "}");
            addCode("");
            addCode(this.indent + field.getAttributeName() + "[i] = newEntry;");
        }
        
        addCode("}");
    }
    
       
    /**
     * Getter.
     * 
     * @return      the deserialisation code.
     */
    @Override
    public String toString()
    {
        if (this.code.isEmpty())
        {
            if (!this.nested)
            {
                return
                        this.offset + this.indent + "@Override\n" +
                        this.offset + this.indent + "public boolean deserialize()\n" +
                        this.offset + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + "return true;\n" +
                        this.offset + this.indent + "}\n";
            }
            else
            {
                return
                        this.offset + this.indent + "public boolean deserialize(ByteBuffer buffer)\n" +
                        this.offset + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + "return true;\n" +
                        this.offset + this.indent + "}\n";
            }
        }
        else
        {
            if (!this.nested)
            {
                return
                        this.offset + this.indent + "@Override\n" +
                        this.offset + this.indent + "public boolean deserialize()\n" +
                        this.offset + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + "ByteBuffer buffer = getBuffer();\n" +
                        this.offset + this.indent + this.indent + "int bufferPosition = buffer.position();\n" + 
                                                        "\n" +
                        this.offset + this.indent + this.indent + "try\n" +
                        this.offset + this.indent + this.indent + "{\n" +
                                                    this.code +
                        this.offset + this.indent + this.indent + "}\n" +
                        this.offset + this.indent + this.indent + "catch (BufferUnderflowException e)\n" +
                        this.offset + this.indent + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + this.indent + "buffer.position(bufferPosition);\n" +
                        this.offset + this.indent + this.indent + this.indent + "return false;\n" +
                        this.offset + this.indent + this.indent + "}\n\n" +
                        this.offset + this.indent + this.indent + "return true;\n" +
                        this.offset + this.indent + "}\n";
            }
            else
            {
                return
                        this.offset + this.indent + "public boolean deserialize(ByteBuffer buffer)\n" +
                        this.offset + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + "int bufferPosition = buffer.position();\n" + 
                                                        "\n" +
                        this.offset + this.indent + this.indent + "try\n" +
                        this.offset + this.indent + this.indent + "{\n" +
                                                    this.code +
                        this.offset + this.indent + this.indent + "}\n" +
                        this.offset + this.indent + this.indent + "catch (BufferUnderflowException e)\n" +
                        this.offset + this.indent + this.indent + "{\n" +
                        this.offset + this.indent + this.indent + this.indent + "buffer.position(bufferPosition);\n" +
                        this.offset + this.indent + this.indent + this.indent + "return false;\n" +
                        this.offset + this.indent + this.indent + "}\n\n" +
                        this.offset + this.indent + this.indent + "return true;\n" +
                        this.offset + this.indent + "}\n";
            }
        }
    }
}
