/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * This class provides a getSize method
 * to realize serialisation.
 *
 * @author miracle444
 */
public final class SizeHelper 
{
    
    private final String indent;    // the number of spaces of an indentation
    private final String offset;    // a general offset indention for nested classes formatting
    private String code;            // contains the code this object is managing
    private int size;               // contains the total size of all static data
    
    
    /**
     * Constructor.
     * 
     * @param       indention       the number of spaces of an indentation.
     * @param       offset          a general offset indention for nested classes formatting.
     * @param       headerSize      the size of a header in bytes.
     */
    public SizeHelper(String indention, final String offset, final int headerSize)
    {
        this.indent = indention;
        this.offset = offset;
        this.size = headerSize;
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
        this.code += this.offset + this.indent + this.indent + code + "\n";                
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
            addNoArraySizeMethod(field);
        }
        else
        {
            if (!field.getStatic())
            {
                addDynamicArraySizeMethod(field);
            }
            else
            {
                addStaticArraySizeMethod(field);
            }
        }
    }

    
    /**
     * is used to add a size method for
     * a non-array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addNoArraySizeMethod(FieldConverter field)
    {
        if (!field.isNested())
        {
            size += field.getSize();
            
            if (field.isVector())
            {
                // do some extra check on the format
                addCode("if (" + field.getAttributeName() + " == null)");
                addCode("{");
                addCode(this.indent + "return 0;");
                addCode("}");
                addCode("");
                addCode("if (" + field.getAttributeName() + ".length != " + field.getVectorSize() + ")");
                addCode("{");
                addCode(this.indent + "return 0;");
                addCode("}");
            }
        }
        else
        {
            addCode("size += " + field.getAttributeName() + ".getSize();");
        }
    }

    
    /**
     * is used to add a size method for
     * a dynamic size array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addDynamicArraySizeMethod(FieldConverter field) 
    {
        size += field.getPrefixSize();

        addCode("if (" + field.getAttributeName() + " != null)");
        addCode("{");
                        
        if (!field.isNested())
        {
            if (field.isVector())
            {
                // check format
                addCode(indent + "for (float[] entry : " + field.getAttributeName() + ")");
                addCode(indent + "{");
                addCode(indent + indent + "if (entry.length != " + field.getVectorSize() + ")");
                addCode(indent + indent + "{");
                addCode(indent + indent + indent + "return 0;");
                addCode(indent + indent + "}");
                addCode(indent + "}");
            }
            
            addCode(indent + "size += " + field.getSize() + " * " + field.getAttributeName() + ".length;");
        }
        else
        {
            addCode(indent + "for (" + field.getType() + " entry : " + field.getAttributeName() + ")");
            addCode(indent + "{");
            addCode(indent + indent + "int nextSize = entry.getSize();");
            addCode("");
            addCode(indent + indent + "if (nextSize == 0)");
            addCode(indent + indent + "{");
            addCode(indent + indent + indent + "return 0;");
            addCode(indent + indent + "}");
            addCode("");
            addCode(indent + indent + "size += entry.getSize();");
            addCode(indent + "}");
        }
        
        addCode("}");
    }

    
    /**
     * is used to add a size method for
     * a static array field.
     * 
     * @param   field   the field that has to be added
     */
    private void addStaticArraySizeMethod(FieldConverter field)
    {
        if (!field.isNested())
        {
            size += field.getSize() * field.getOccurs();

            addCode("if (" + field.getAttributeName() + " == null)");
            addCode("{");
            addCode(indent + "return 0;");
            addCode("}");
            addCode("");
            addCode("if (" + field.getAttributeName() + ".length != " + field.getOccurs() + ")");
            addCode("{");
            addCode(indent + "return 0;");
            addCode("}");
            
            if (field.isVector())
            {
                // check format
                addCode("for (float[] entry : " + field.getAttributeName() + ")");
                addCode("{");
                addCode(indent + "if (entry.length != " + field.getVectorSize() + ")");
                addCode(indent + "{");
                addCode(indent + indent + "return 0;");
                addCode(indent + "}");
                addCode("}");
            }
        }
        else
        {
            addCode("if (" + field.getAttributeName() + " == null)");
            addCode("{");
            addCode(indent + "return 0;");
            addCode("}");
            addCode("");
            addCode("if (" + field.getAttributeName() + ".length != " + field.getOccurs() + ")");
            addCode("{");
            addCode(indent + "return 0;");
            addCode("}");
            addCode("");
            addCode("for (" + field.getType() + " entry : " + field.getAttributeName() + ")");
            addCode("{");
            addCode(indent + "int nextSize = entry.getSize();");
            addCode("");
            addCode(indent + "if (nextSize == 0)");
            addCode(indent + "{");
            addCode(indent + indent + "return 0;");
            addCode(indent + "}");
            addCode("");
            addCode(indent + "size += entry.getSize();");
            addCode("}");
        }
    }
    
    
    /**
     * Getter.
     * 
     * @return      the getSize code.
     */
    @Override
    public String toString()
    {
        if (code.isEmpty())
        {
            return
                    this.offset + indent + "private int getSize()\n" +
                    this.offset + indent + "{\n" +
                    this.offset + indent + indent + "return " + this.size + ";\n" +
                    this.offset + indent + "}\n";
        }
        else
        {
            return
                    this.offset + indent + "private int getSize()\n" +
                    this.offset + indent + "{\n" +
                    this.offset + indent + indent + "int size = " + size + ";\n" +
                                                    "\n" +
                                                    code +
                                                    "\n" +
                    this.offset + indent + indent + "return size;\n" +
                    this.offset + indent + "}\n";
        }
    }
}
