/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import com.packetcodegen.jaxb.CommunicationDirection;
import com.packetcodegen.jaxb.PacketFieldType;
import com.packetcodegen.jaxb.PacketType;
import com.packetcodegen.jaxb.Packets;
import com.realityshard.container.utils.JaxbUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;

/**
 * This class is used to create packet actions (ShardletActions) out of a
 * packet-xml file.
 * 
 * @author miracle444
 */
public final class CodeGenExec 
{
    /**
     * Application entry point.
     * 
     * @param       args                    Command line args
     *                                      (See code for more information)
     */
    
    public static void main(String[] args)
    {       
        // the schema file to be used in the codegenerator
        File schema = new File("input" + File.separator + "gw-packet-schema.xsd");
        
        // check if the schema file exists
        if (!schema.exists())
        {
            final String schemaError = "schema file is missing\n" +
                                       "file not found:\n" +
                                       "input" + File.separator + "gw-packet-schema.xsd";
            Logger.getLogger(CodeGenExec.class.getName()).log(Level.SEVERE, null, schemaError);
        }
        
        
        // the templates file to be used in the codegenerator
        File packXML = new File("input" + File.separator + "PacketTemplates.xml");
        
        // check if the templates file exists
        if (!schema.exists())
        {
            final String schemaError = "templates file is missing\n" +
                                       "file not found:\n" +
                                       "input" + File.separator + "PacketTemplates.xml";
            Logger.getLogger(CodeGenExec.class.getName()).log(Level.SEVERE, null, schemaError);
        }
   
        
        try
        {
            // execute the packetcodegen
            DoStuff(schema, packXML);
        } catch (Exception ex)
        {
            Logger.getLogger(CodeGenExec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void DoStuff(File schema, File packXML) throws Exception
    {
        // this constant describes what indention spacing is used in the codegenerator
        final String indention = "    ";
                    
        try 
        {
            // parse templates file
            Packets packConfig = JaxbUtils.validateAndUnmarshal(Packets.class, packXML, schema);

            // get all packet directions
            List<CommunicationDirection> directions = packConfig.getDirection();
            
            // for each direction in the templates
            for (CommunicationDirection direction : directions)
            {
                // get all packets for this direction
                List<PacketType> packets = direction.getPacket();
                
                // create a converter for direction properties to get application specific properties
                DirectionConverter directionConverter = new DirectionConverter(direction);
                
                // writer to output the header->action mapping
                FileWriter mappingWriter = new FileWriter("output" + File.separator +
                                                directionConverter.getServerName() + "-" + 
                                                directionConverter.getDirectionString() + "-" +
                                                "mapping.txt");            
                    
                // for each packet in this direction
                for (PacketType packet : packets)
                {
                    // create a converter to get application specific properties of this packet
                    PacketConverter packetConverter = new PacketConverter(packet, directionConverter);
                    
                    // create the output file object
                    File actionFile = new File("output" + File.separator + 
                                               "actions" + File.separator + 
                                               directionConverter.getServerName() + File.separator +
                                               directionConverter.getDirectionString() + File.separator +
                                               packetConverter.getActionName() + ".java");
                    
                    // create directories if the dont exists
                    actionFile.getParentFile().mkdirs();
                    
                    // instantiate a writer to output the generated code to the actionFile
                    FileWriter actionWriter = new FileWriter(actionFile);
                    
                    // create the helper object that generates the action's code
                    ClassHelper classHelper = new ClassHelper(indention, packetConverter);
                    
                    // add mapping to output file
                    mappingWriter.append(classHelper.getMapping());
                    
                    // get all fields in this packet
                    List<PacketFieldType> fields = packet.getField();
                    
                    // for each field in this packet                    
                    for (PacketFieldType field : fields)
                    {
                        // create a converter to get application specific information of this field
                        FieldConverter fieldConverter = new FieldConverter(field, packetConverter);
                        
                        // add the field to this action
                        classHelper.addField(fieldConverter);
                        
                        // check if this field's type is nested
                        if (fieldConverter.isNested())
                        {
                            // if it is nested add this nested type
                            classHelper.addNestedType(fieldConverter, field.getField());
                        }
                    }
                    
                    // output the generated code
                    actionWriter.write(classHelper.toString());
                    
                    // close the writer object
                    actionWriter.close();
                }
                
                // close the writer object
                mappingWriter.close();
            }
        } 
        catch (JAXBException | SAXException ex) 
        {
            throw new Exception("Unable to parse xml file.", ex);
        }
        catch (IOException ex)
        {
            throw new Exception("Unable to write action file.", ex);
        }
    }
}
