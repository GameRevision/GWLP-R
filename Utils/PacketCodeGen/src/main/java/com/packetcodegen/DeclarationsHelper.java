/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * This class manages the declarations of an Action or
 * a nested struct.
 * 
 * @author miracle444
 */
public final class DeclarationsHelper 
{
    
    private final String indent;    // the number of spaces of an indentation
    private final String offset;    // a general offset indention for nested classes formatting
    private String code;            // contains the code this object is managing
    
    /**
     * Constructor.
     * 
     * @param   indention   the number of spaces of an indentation
     * @param   offset      a general offset indention for nested classes formatting
     */
    public DeclarationsHelper(final String indention, final String offset)
    {
        this.indent = indention;
        this.offset = offset;
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
        this.code += this.offset + this.indent + code + "\n";                
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
            addCode("private " + field.getType() + " " + field.getAttributeName() + ";");
        }
        else
        {
            addCode("private " + field.getType() + "[] " + field.getAttributeName() + ";");
        }
    }
    
    
    /**
     * outputs the declaration code.
     * 
     * @return      the declaration code.
     */
    @Override
    public String toString()
    {
        return code;
    }
}
