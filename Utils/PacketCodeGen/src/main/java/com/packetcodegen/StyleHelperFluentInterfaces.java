/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;


/**
 * Used to control the style helper.
 * 
 * @author _rusty
 */
public interface StyleHelperFluentInterfaces 
{
    public interface Create
    {
        public WriteFields create(int indentSpaces, String className, String packageName);
    }
    
    public interface WriteFields
    {
        public WriteFields newField(String type, String name, String comment, String author);
        public WriteMethods nextStep();
    }
}
