/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.gwlpr.protocol;

import com.realityshard.shardlet.Action;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.TriggerableAction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This protocol filter can be used to log specific packets
 * 
 * This either operates with a black- or whitelist, see
 * LoggingFilter.OperationMethod. The list can be given by adding
 * the "HeaderList" init param with the following format: (each header as parsable int)
 * 
 * "header1 header2 header3" (and so on)
 * 
 * These will be either logged (whitelist) or ignored (blacklist)
 * 
 * @author _rusty
 */
public class LoggingFilter implements ProtocolFilter
{
 
    /**
     * Define the operation method by adding a 
     * "OperationMethod" init param with either "WhiteList" or "BlackList"
     */
    private static enum OperationMethod
    {
        WhiteList,
        BlackList
    }
    
    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    private OperationMethod opMethod;
    private Collection<Integer> headers;

    
    /**
     * Init this filter.
     * 
     * @param       initParams              This should contain two params:
     *                                      "OperationMethod" - "WhiteList" or "BlackList"
     *                                      "HeaderList" - "header1 header2 header3" (and so on)
     */
    @Override
    public void init(Map<String, String> initParams) 
    {
        headers = new HashSet<>();
        
        String opMethString = initParams.get("OperationMethod");
        String headerString = initParams.get("HeaderList");
        
        // failcheck
        if (opMethString == null || headerString == null) 
        {
            LOGGER.error("Init-param missing. Make sure you set 'OperationMethod' and 'HeaderList'.");
            return;
        }
        
        try 
        {
            opMethod = OperationMethod.valueOf(opMethString);
        } 
        catch (IllegalArgumentException ex) 
        {
            LOGGER.error("Non-existing operation method given. Use 'WhiteList' or 'BlackList'. Defaulting to the latter now.");
            opMethod = OperationMethod.BlackList;
        }
        
        String[] headerList = headerString.split(" ");
        
        // failcheck
        if (headerList == null || headerList.length == 0)
        {
            LOGGER.error("No header list given!");
        }
        
        try
        {
            for (String header : headerList) 
            {
                headers.add(Integer.parseInt(header));
            }
        }
        catch (NumberFormatException ex)
        {
            LOGGER.error("A header could not be parsed. Stopped parsing.", ex);
        }
    }

    
    /**
     * This method logs incoming packets.
     * 
     * @param       action
     * @return
     * @throws      IOException 
     */
    @Override
    public List<TriggerableAction> doInFilter(TriggerableAction action) throws IOException 
    {
        logIt("C->S", action);
        
        // this is one of the things that i hate about java's syntax...
        // this shouldnt be needing 3 lines!
        List<TriggerableAction> result = new ArrayList<>();
        result.add(action);
        return result;
    }

    
    /**
     * This method logs outgoing packets.
     * 
     * Be sure to place this after the serialization filter, but before any
     * encryption filters!
     * 
     * @param       action
     * @return
     * @throws      IOException 
     */
    @Override
    public Action doOutFilter(Action action) throws IOException 
    {
        logIt("S->C", action);
        
        // dont change anything!
        return action;
    }
    
    
    /**
     * Helper method.
     * Log a byte buffer of a network action, depending on our
     * White/BlackList settings
     * 
     * @param       prefix
     * @param       action 
     */
    public void logIt(String prefix, Action action)
    {
        if (action.getBuffer() == null) { return; }

        int header = action.getBuffer().getShort(0);
        
        // 0 and everything below that is not a correct header
        if (header <= 0) { return; } 
        
        if (opMethod == OperationMethod.BlackList)
        {
            // dont log it if the blacklist contains the header of this packet
            if (headers.contains(header)) { return; }
        }
        else if (opMethod == OperationMethod.WhiteList)
        {
            // dont log it if the whitelist doesnt contain the header of this packet
            if (!headers.contains(header)) { return; }
        }
        
        LOGGER.debug(String.format("%s: %s", prefix, bytesToHex(action.getBuffer().array())));
    }
    
    
    /**
     * Helper.
     * (Taken from stackoverflow)
     * 
     * @param       bytes
     * @return 
     */
    public static String bytesToHex(byte[] bytes) 
    {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
