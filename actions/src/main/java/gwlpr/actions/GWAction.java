/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.actions;

import realityshard.shardlet.utils.GenericAction;


/**
 * Specifies a header additionally to the functionality of GenericAction.
 * This class must be used for all GW protocol related actions.
 * 
 * @author _rusty
 */
public abstract class GWAction extends GenericAction
{
    
    /**
     * Getter.
     * The header is the first 2 bytes of a GW packet.
     * 
     * @return      The header of this action.
     */
    public abstract short getHeader();
}
