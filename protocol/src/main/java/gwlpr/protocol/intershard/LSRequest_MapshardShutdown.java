/**
 * For copyright information see the LICENSE document.
 */

package gwlpr.loginshard;

import realityshard.shardlet.utils.GenericAction;


/**
 * This is send to a mapshard, to request its shut down.
 * 
 * @author _rusty
 */
public class ISC_ShutdownMapshardRequestAction extends GenericAction
{
    
    /**
     * Constructor.
     */
    public ISC_ShutdownMapshardRequestAction()
    {
        init(null); // no session needed here!
    }
}
