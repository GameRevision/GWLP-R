/**
 * For copyright information see the LICENSE document.
 */

package com.packetcodegen;

import java.io.File;


/**
 * This class is used to create packet actions (ShardletActions) out of a
 * packet-xml file.
 * 
 * @author _rusty
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
        // parse args, use them
        // TODO: support command line
        
        // check out if all files exist.
        // this should be given by command line parameter.
        
        File schema = new File("gw-packet-schema.xsd");
        File packXML = new File("packets.xml");
        
        
    }
}
