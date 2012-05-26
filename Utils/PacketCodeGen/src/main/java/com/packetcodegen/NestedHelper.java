/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

/**
 * This class creates nested structs.
 * 
 * @author miracle444
 */
public final class NestedHelper 
{
    
    private final String indent;                // the number of spaces of an indentation
    private PacketConverter packet;             // the packet this nested structs belongs to
    private HeaderHelper header;                // manages header data
    private PropertiesHelper properties;        // manages properties
    private DeserializeHelper deserialize;      // manages deserialisation routines
    private SerializeHelper serialize;          // manages serialisation routines
    private DeclarationsHelper declarations;    // manages declarations
    private SizeHelper size;                    // provides getSize functionality     

    
    /**
     * Constructor.
     * 
     * @param   indention   the number of spaces of an indentation.
     * @param   field       the field that is of nested type.
     * @param   packet      the packet this nested struct belongs to.
     */
    public NestedHelper(String indention, FieldConverter field, PacketConverter packet)
    {
        this.indent = indention;
        this.packet = packet;
        this.header = new HeaderHelper(this.indent, field);
        this.declarations = new DeclarationsHelper(this.indent, this.indent);
        this.size = new SizeHelper(this.indent, this.indent, 0);
        this.properties = new PropertiesHelper(this.indent, this.indent, this.packet, false);
        this.deserialize = new DeserializeHelper(this.indent, this.indent, true);
        this.serialize = new SerializeHelper(this.indent, this.indent, true);
    }
    
    
    /**
     * iteration routine to add packet fields to this nested struct
     * delegating it to all helper objects.
     * 
     * @param   field   the field that has to be added tho this nested struct.
     */
    public void addField(FieldConverter field)
    {
        this.declarations.addField(field);
        this.size.addField(field);
        this.properties.addField(field);
        this.deserialize.addField(field);
        this.serialize.addField(field);
    }
    
    
    /**
     * puts the results together and returns the complete nested struct code.
     * 
     * @return      returns the nested struct code of this class.
     */
    @Override
    public String toString()
    {
        return header.toString() +
               declarations.toString() +
               "\n" +
               "\n" +
               properties.toString() +
               "\n" +
               "\n" +
               (packet.getFromClient() ? "" : size.toString() + "\n\n") +
               (packet.getFromClient() ? deserialize.toString() : "") + 
               (packet.getFromClient() ? "" : serialize.toString()) +
               this.indent + "}\n";
    }
}
