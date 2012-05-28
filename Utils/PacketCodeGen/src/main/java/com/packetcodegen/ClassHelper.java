/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import com.packetcodegen.jaxb.PacketFieldType;
import java.util.List;

/**
 * Helps by creating and assembling the code fragments
 * for one specific ShardletAction corresponding to a packet.
 * 
 * @author miracle444
 */
public final class ClassHelper 
{
    
    private PacketConverter packet;             // the packet this class is describing
    private final String indent;                // the number of spaces of an indentation
    private final String eventTriggerCode;      // the event trigger code for this action
    private String nestedTypesCode;             // all nested structs concatenated
    private HeaderHelper header;                // manages header data
    private PropertiesHelper properties;        // manages properties
    private DeserializeHelper deserialize;      // manages deserialisation routines
    private SerializeHelper serialize;          // manages serialisation routines
    private DeclarationsHelper declarations;    // manages declarations
    private SizeHelper size;                    // provides getSize functionality
    
    
    /**
     * Constructor.
     * 
     * @param       indention            The number of spaces of an indentation
     * @param       packet               The packet this class is describing
     */
    public ClassHelper(final String indention, PacketConverter packet)
    {
        this.indent = indention;
        this.packet = packet;
        
        
        // initialize empty because we concat later
        this.nestedTypesCode = "";
        
        
        // the event trigger code for this action
        eventTriggerCode =
                this.indent + "@Override\n" +
                this.indent + "public void triggerEvent(EventAggregator aggregator)\n" +
                this.indent + "{\n" +
                this.indent + this.indent + "aggregator.triggerEvent(this);\n" +
                this.indent + "}\n";
        
        
        // instantiating helper objects        
        header = new HeaderHelper(packet);
        properties = new PropertiesHelper(this.indent, "", packet, true);
        deserialize = new DeserializeHelper(this.indent, "", false);
        serialize = new SerializeHelper(this.indent, "", false);
        size = new SizeHelper(this.indent, "", 2);
        declarations = new DeclarationsHelper(this.indent, "");
    }
    
    /**
     * iteration routine to add packet fields to this action
     * delegating it to all helper objects.
     * 
     * @param   field   the field that has to be added tho this action.
     */
    public void addField(FieldConverter field)
    {
        header.addField(field);
        size.addField(field);
        properties.addField(field);
        deserialize.addField(field);
        serialize.addField(field);
        declarations.addField(field);
    }

    
    /**
     * puts the results together and returns the complete action code.
     * 
     * @return      returns the action code of this class.
     */
    @Override
    public String toString()
    {               
        return header.toString() +
               (!nestedTypesCode.isEmpty() ? nestedTypesCode + "\n\n" : "") +
               (!declarations.toString().isEmpty() ? declarations.toString() + "\n\n" : "") +
               properties.toString() +
               "\n" +
               "\n" +
               (packet.getFromClient() ? "" : size.toString() + "\n\n") +
               (packet.getFromClient() ? deserialize.toString() + "\n\n" : "") + 
               (packet.getFromClient() ? "" : serialize.toString()) +
               (packet.getFromClient() ? eventTriggerCode : "") +
               "}";
    }
    
    
    /**
     * iteration routine to add nested types (not its corresponding field)
     * to an action.
     * 
     * @param   fieldConverter    field converter object of the nested field to be handled.
     * @param   nestedFields      the nested fields in the corresponding field.
     */
    public void addNestedType(FieldConverter fieldConverter, List<PacketFieldType> nestedFields)
    {
        // instantiate the helper object for this nested field
        NestedHelper nested = new NestedHelper(this.indent, fieldConverter, this.packet);
        
        
        // loop through all fields in this nested struct
        for (PacketFieldType nestedField : nestedFields)
        {
            FieldConverter nestedConverter = new FieldConverter(nestedField, packet);
            
            // add the field to the nested struct
            nested.addField(nestedConverter);
            
            
            // check if this field is itself nested
            if (nestedConverter.isNested())
            {
                // if its nested create a nested struct recursively
                addNestedType(nestedConverter, nestedField.getField());                
            }
        }
        
        
        // formatting purposes only
        if (!this.nestedTypesCode.isEmpty())
        {
            this.nestedTypesCode += "\n\n";
        }
        
        
        // concat existing nested struct code with this new one
        this.nestedTypesCode += nested.toString();
    }
    
    
    /**
     * Getter.
     * 
     * @return  the header->action mapping for this action.
     */
    public String getMapping()
    {
        return
                "case " + packet.getHeader() + ":\n" +
                this.indent + "return new " + packet.getActionName() + "();\n";
    }
}
